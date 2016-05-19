package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.ResourceBundle;

import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntitySubject;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatterBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult.ExecutionStatus;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FetchSubjectsHandler implements DBHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(FetchSubjectsHandler.class);
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");
  private final ProcessorContext context;
  private String classificationType = null;

  public FetchSubjectsHandler(ProcessorContext context) {
    this.context = context;
  }

  @Override
  public ExecutionResult<MessageResponse> checkSanity() {
    if (context.userId() == null || context.userId().isEmpty()) {
      LOGGER.warn("Invalid user");
      return new ExecutionResult<>(MessageResponseFactory.createForbiddenResponse(RESOURCE_BUNDLE.getString("not.allowed")),
              ExecutionResult.ExecutionStatus.FAILED);
    }
    JsonArray classification = context.request().getJsonArray(HelperConstants.CLASSIFICATION_TYPE);
    if (context.request() == null || classification == null || classification.isEmpty()) {
      LOGGER.warn("classification type is missing.");
      return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(RESOURCE_BUNDLE.getString("missing.classification.type")),
              ExecutionStatus.FAILED);
    }
    this.classificationType = classification.getString(0);
    
    // There should be an standard_framework  present
    if (context.standardFrameworkId() == null || context.standardFrameworkId().isEmpty()) {
      LOGGER.warn("Missing standard framework id");
      return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(), ExecutionResult.ExecutionStatus.FAILED);
    }
    
    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  @Override
  public ExecutionResult<MessageResponse> validateRequest() {
    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  @Override
  public ExecutionResult<MessageResponse> executeRequest() {
    LazyList<AJEntitySubject> results =
            AJEntitySubject.where(AJEntitySubject.SUBJECTS_GET, this.classificationType, context.standardFrameworkId()).orderBy(HelperConstants.SEQUENCE_ID);
    return new ExecutionResult<>(MessageResponseFactory.createOkayResponse(new JsonObject().put(HelperConstants.SUBJECTS, new JsonArray(
            JsonFormatterBuilder.buildSimpleJsonFormatter(false, Arrays.asList(HelperConstants.TX_RESPONSE_FIELDS)).toJson(results)))),
            ExecutionResult.ExecutionStatus.SUCCESSFUL);
  }

  @Override
  public boolean handlerReadOnly() {
    return true;
  }

}
