package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.Arrays;
import java.util.ResourceBundle;
import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTaxonomyCourse;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatterBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FetchCoursesHandler implements DBHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(FetchCoursesHandler.class);
  private final ProcessorContext context;
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");

  public FetchCoursesHandler(ProcessorContext context) {
    this.context = context;
  }

  @Override
  public ExecutionResult<MessageResponse> checkSanity() {
    if (context.userId() == null || context.userId().isEmpty()) {
      LOGGER.warn("Invalid user");
      return new ExecutionResult<>(MessageResponseFactory.createForbiddenResponse(),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    // There should be an  standard framework and subject id present
    if (context.subjectId() == null || context.subjectId().isEmpty()
        || context.standardFrameworkId() == null || context.standardFrameworkId().isEmpty()) {
      LOGGER.warn("Missing standard framework and subject id");
      return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  @Override
  public ExecutionResult<MessageResponse> validateRequest() {
    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  @Override
  public ExecutionResult<MessageResponse> executeRequest() {
    LazyList<AJEntityTaxonomyCourse> results =
        AJEntityTaxonomyCourse.where(AJEntityTaxonomyCourse.COURSES_GET, context.subjectId(),
            context.standardFrameworkId())
            .orderBy(HelperConstants.SEQUENCE_ID);
    return new ExecutionResult<>(MessageResponseFactory
        .createOkayResponse(new JsonObject().put(HelperConstants.COURSES, new JsonArray(
            JsonFormatterBuilder.buildSimpleJsonFormatter(false,
                Arrays.asList(HelperConstants.TX_COURSE_RESPONSE_FIELDS)).toJson(results)))),
        ExecutionResult.ExecutionStatus.SUCCESSFUL);
  }

  @Override
  public boolean handlerReadOnly() {
    return true;
  }

}
