package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import java.util.Arrays;
import java.util.ResourceBundle;

import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTaxonomyDomain;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatterBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult.ExecutionStatus;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

class FetchDomainsHandler implements DBHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchDomainsHandler.class);
    private final ProcessorContext context;
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");
    private String standardFrameworkId;

    public FetchDomainsHandler(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public ExecutionResult<MessageResponse> checkSanity() {
        if (context.userId() == null || context.userId().isEmpty()) {
            LOGGER.warn("Invalid user");
            return new ExecutionResult<>(MessageResponseFactory.createForbiddenResponse(),
                ExecutionResult.ExecutionStatus.FAILED);
        }
        // There should be an subject and course id present
        if (context.subjectId() == null || context.subjectId().isEmpty() || context.courseId() == null || context
            .courseId().isEmpty()) {
            LOGGER.warn("Missing subject/course id");
            return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(),
                ExecutionResult.ExecutionStatus.FAILED);
        }

        JsonObject taxonomyPrefs = context.prefs().getJsonObject(HelperConstants.STANDARD_PREFERECE);
        if (taxonomyPrefs == null || !taxonomyPrefs.containsKey(context.subjectId())) {
            LOGGER.warn("subject  standard framework is missing.");
            return new ExecutionResult<>(MessageResponseFactory
                .createInvalidRequestResponse(RESOURCE_BUNDLE.getString("missing.subject.framework.code")),
                ExecutionStatus.FAILED);
        }
        this.standardFrameworkId = taxonomyPrefs.getString(context.subjectId());
        return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
    }

    @Override
    public ExecutionResult<MessageResponse> validateRequest() {
        return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
    }

    @Override
    public ExecutionResult<MessageResponse> executeRequest() {
        LazyList<AJEntityTaxonomyDomain> results = AJEntityTaxonomyDomain
            .where(AJEntityTaxonomyDomain.DOMAINS_GET, context.courseId() + HelperConstants.PERCENTAGE,
                standardFrameworkId).orderBy(HelperConstants.SEQUENCE_ID);
        return new ExecutionResult<>(MessageResponseFactory.createOkayResponse(new JsonObject()
            .put(HelperConstants.DOMAINS, new JsonArray(JsonFormatterBuilder
                .buildSimpleJsonFormatter(false, Arrays.asList(HelperConstants.TX_DOMAIN_RESPONSE_FIELDS))
                .toJson(results)))), ExecutionResult.ExecutionStatus.SUCCESSFUL);
    }

    @Override
    public boolean handlerReadOnly() {
        return true;
    }

}
