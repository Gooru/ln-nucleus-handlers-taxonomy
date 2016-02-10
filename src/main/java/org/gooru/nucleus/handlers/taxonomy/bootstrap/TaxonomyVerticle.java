package org.gooru.nucleus.handlers.taxonomy.bootstrap;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import org.gooru.nucleus.handlers.taxonomy.bootstrap.shutdown.Finalizer;
import org.gooru.nucleus.handlers.taxonomy.bootstrap.shutdown.Finalizers;
import org.gooru.nucleus.handlers.taxonomy.bootstrap.startup.Initializer;
import org.gooru.nucleus.handlers.taxonomy.bootstrap.startup.Initializers;
import org.gooru.nucleus.handlers.taxonomy.constants.MessagebusEndpoints;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaxonomyVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(TaxonomyVerticle.class);

  @Override
  public void start(Future<Void> voidFuture) throws Exception {

    vertx.executeBlocking(blockingFuture -> {
      startApplication();
      blockingFuture.complete();
    }, future -> {
      if (future.succeeded()) {
        voidFuture.complete();
      } else {
        voidFuture.fail("Not able to initialize the Taxonomy machinery properly");
      }
    });

    EventBus eb = vertx.eventBus();

    eb.consumer(MessagebusEndpoints.MBEP_TAXONOMY, message -> {

      LOGGER.debug("Received message: " + message.body());

      vertx.executeBlocking(future -> {
        MessageResponse result = ProcessorBuilder.build(message).process();
        future.complete(result);
      }, res -> {
        MessageResponse result = (MessageResponse) res.result();
        message.reply(result.reply(), result.deliveryOptions());

        JsonObject eventData = result.event();
        if (eventData != null) {
          eb.publish(MessagebusEndpoints.MBEP_EVENT, eventData);
        }

      });

    }).completionHandler(result -> {
      if (result.succeeded()) {
        LOGGER.info("Taxonomy end point ready to listen");
      } else {
        LOGGER.error("Error registering the taxonomy handler. Halting the Taxonomy machinery");
        Runtime.getRuntime().halt(1);
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