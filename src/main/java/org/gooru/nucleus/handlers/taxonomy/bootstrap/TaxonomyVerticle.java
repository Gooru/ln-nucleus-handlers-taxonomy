package org.gooru.nucleus.handlers.taxonomy.bootstrap;

import org.gooru.nucleus.handlers.taxonomy.bootstrap.shutdown.Finalizer;
import org.gooru.nucleus.handlers.taxonomy.bootstrap.shutdown.Finalizers;
import org.gooru.nucleus.handlers.taxonomy.bootstrap.startup.Initializer;
import org.gooru.nucleus.handlers.taxonomy.bootstrap.startup.Initializers;
import org.gooru.nucleus.handlers.taxonomy.constants.MessageConstants;
import org.gooru.nucleus.handlers.taxonomy.constants.MessagebusEndpoints;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class TaxonomyVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaxonomyVerticle.class);

    @Override
    public void start(Future<Void> voidFuture) throws Exception {

        EventBus eb = vertx.eventBus();

        vertx.executeBlocking(blockingFuture -> {
            startApplication();
            blockingFuture.complete();
        }, startApplicationFuture -> {
            if (startApplicationFuture.succeeded()) {
                eb.consumer(MessagebusEndpoints.MBEP_TAXONOMY, message -> {
                    LOGGER.debug("Received message: " + message.body());
                    vertx.executeBlocking(future -> {
                        MessageResponse result = ProcessorBuilder.build(message).process();
                        future.complete(result);
                    }, res -> {
                        MessageResponse result = (MessageResponse) res.result();
                        message.reply(result.reply(), result.deliveryOptions());

                        LOGGER.debug("Event Data : " + result.event());
                        JsonObject eventData = result.event();
                        if (eventData != null) {
                            String sessionToken =
                                ((JsonObject) message.body()).getString(MessageConstants.MSG_HEADER_TOKEN);
                            if (sessionToken != null && !sessionToken.isEmpty()) {
                                eventData.put(MessageConstants.MSG_HEADER_TOKEN, sessionToken);
                            } else {
                                LOGGER.warn("Invalid session token received");
                            }
                            eb.send(MessagebusEndpoints.MBEP_EVENT, eventData);
                        }
                    });
                }).completionHandler(result -> {
                    if (result.succeeded()) {
                        voidFuture.complete();
                        LOGGER.info("Taxonomy end point ready to listen");
                    } else {
                        LOGGER.error("Error registering the taxonomy handler. Halting the Taxonomy machinery");
                        voidFuture.fail(result.cause());
                        Runtime.getRuntime().halt(1);
                    }
                });
            } else {
                voidFuture.fail("Not able to initialize the Taxonomy machinery properly");
            }
        });

    }

    @Override
    public void stop() throws Exception {
        shutDownApplication();
        super.stop();
    }

    private void startApplication() {
        Initializers initializers = new Initializers();
        try {
            for (Initializer initializer : initializers) {
                initializer.initializeComponent(vertx, config());
            }
        } catch (IllegalStateException ie) {
            LOGGER.error("Error initializing application", ie);
            Runtime.getRuntime().halt(1);
        }
    }

    private void shutDownApplication() {
        Finalizers finalizers = new Finalizers();
        for (Finalizer finalizer : finalizers) {
            finalizer.finalizeComponent();
        }

    }
}
