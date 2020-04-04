package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import java.util.ResourceBundle;
import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityGutToFrameworkTransformation;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

class FetchGutToFrameworkTransformationHandler implements DBHandler {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(FetchGutToFrameworkTransformationHandler.class);
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");
  private final ProcessorContext context;
  private String subjectCode;
  private String frameworkId;
  private final String INCLUDE_MCOMP = "include_mcomp";

  public FetchGutToFrameworkTransformationHandler(ProcessorContext context) {
    this.context = context;
  }

  @Override
  public ExecutionResult<MessageResponse> checkSanity() {
    if (context.userId() == null || context.userId().isEmpty()) {
      LOGGER.warn("Invalid user");
      return new ExecutionResult<>(
          MessageResponseFactory.createForbiddenResponse(RESOURCE_BUNDLE.getString("not.allowed")),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    this.frameworkId = context.standardFrameworkId();
    if (this.frameworkId == null || this.frameworkId.isEmpty()) {
      LOGGER.warn("Framework id is missing");
      return new ExecutionResult<>(
          MessageResponseFactory.createInvalidRequestResponse("framework id is missing"),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    this.subjectCode = context.subjectId();
    if (this.subjectCode == null || this.subjectCode.isEmpty()) {
      LOGGER.warn("Subject code is missing");
      return new ExecutionResult<>(
          MessageResponseFactory.createInvalidRequestResponse("subject code is missing"),
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
    LazyList<AJEntityGutToFrameworkTransformation> crosswalkModels =
        AJEntityGutToFrameworkTransformation.where(
            AJEntityGutToFrameworkTransformation.FETCH_GUT_TO_FRAMEWORK_TRANSFORMATION, frameworkId,
            subjectCode);

    if (crosswalkModels == null || crosswalkModels.isEmpty()) {
      return new ExecutionResult<>(
          MessageResponseFactory.createNotFoundResponse(RESOURCE_BUNDLE.getString("not.found")),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    String key = isIncludeMComp() ? HelperConstants.TRANSFORMED_WITH_MCOMP_DATA
        : HelperConstants.TRANSFORMED_DATA;
    String crosswalk = crosswalkModels.get(0).getString(key);
    JsonObject resultSet = (crosswalk != null) ? new JsonObject(crosswalk) : new JsonObject();
    return new ExecutionResult<>(MessageResponseFactory.createOkayResponse(resultSet),
        ExecutionResult.ExecutionStatus.SUCCESSFUL);

  }

  private boolean isIncludeMComp() {
    JsonArray requestParams = context.request().getJsonArray(INCLUDE_MCOMP);
    if (requestParams == null || requestParams.isEmpty()) {
      return false;
    }
    Boolean value = Boolean.valueOf(requestParams.getString(0));
    return value != null ? value : false;
  }

  @Override
  public boolean handlerReadOnly() {
    return true;
  }

}
