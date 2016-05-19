package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityGUFSubjectStandardFramework;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatter;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FetchGUFSubjectStandardsFrameworkHandler implements DBHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchGUFSubjectStandardsFrameworkHandler.class);
    private final ProcessorContext context;
    private static final String STANDARD_FRAMEWORKS = "standard_frameworks";

    public FetchGUFSubjectStandardsFrameworkHandler(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public ExecutionResult<MessageResponse> checkSanity() {
        if (context.userId() == null || context.userId().isEmpty()) {
            LOGGER.warn("Invalid user");
            return new ExecutionResult<>(MessageResponseFactory.createForbiddenResponse(),
                ExecutionResult.ExecutionStatus.FAILED);
        }
        // There should be an subject id present
        if (context.subjectId() == null || context.subjectId().isEmpty()) {
            LOGGER.warn("Missing subject id");
            return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(),
                ExecutionResult.ExecutionStatus.FAILED);
        }
        return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
    }

    @Override
    public ExecutionResult<MessageResponse> validateRequest() {
        return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ExecutionResult<MessageResponse> executeRequest() {
        List<Map> results = Base.findAll(AJEntityGUFSubjectStandardFramework.STANDARD_FRAMEWORKS, context.subjectId());
        return new ExecutionResult<>(MessageResponseFactory.createOkayResponse(new JsonObject().put(
            STANDARD_FRAMEWORKS, JsonFormatter.toJson(results))), ExecutionResult.ExecutionStatus.SUCCESSFUL);
    }

    @Override
    public boolean handlerReadOnly() {
        return true;
    }

}
