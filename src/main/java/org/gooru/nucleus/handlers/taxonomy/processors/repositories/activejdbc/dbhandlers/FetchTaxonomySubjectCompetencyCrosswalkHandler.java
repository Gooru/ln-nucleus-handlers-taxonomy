package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import java.util.ResourceBundle;
import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityFrameworkSubjectCompetencyCrosswalk;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.json.JsonObject;

class FetchTaxonomySubjectCompetencyCrosswalkHandler implements DBHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(FetchTaxonomySubjectCompetencyCrosswalkHandler.class);
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");
  private final ProcessorContext context;
  private String subjectCode;
  private String frameworkId;

  public FetchTaxonomySubjectCompetencyCrosswalkHandler(ProcessorContext context) {
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
    if (context.standardFrameworkId() == null || context.standardFrameworkId().isEmpty()) {
      LOGGER.warn("Framework id is missing");
      return new ExecutionResult<>(
          MessageResponseFactory.createInvalidRequestResponse("framework id is missing"),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    this.subjectCode = context.subjectId();
    if (context.subjectId() == null || context.subjectId().isEmpty()) {
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
    LazyList<AJEntityFrameworkSubjectCompetencyCrosswalk> crosswalkModels = AJEntityFrameworkSubjectCompetencyCrosswalk
          .where(AJEntityFrameworkSubjectCompetencyCrosswalk.FETCH_FRAMEWORK_SUBJECT_COMPETENCY_CROSSWALK, frameworkId, subjectCode);

    if (crosswalkModels == null || crosswalkModels.isEmpty()) {
      return new ExecutionResult<>(
          MessageResponseFactory.createNotFoundResponse(RESOURCE_BUNDLE.getString("not.found")),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    String crosswalk = crosswalkModels.get(0).getString(HelperConstants.CROSSWALK);
    JsonObject resultSet = (crosswalk != null) ? new JsonObject(crosswalk) : new JsonObject();
    return new ExecutionResult<>(MessageResponseFactory.createOkayResponse(resultSet),
        ExecutionResult.ExecutionStatus.SUCCESSFUL);

  }

  @Override
  public boolean handlerReadOnly() {
    return true;
  }

}
