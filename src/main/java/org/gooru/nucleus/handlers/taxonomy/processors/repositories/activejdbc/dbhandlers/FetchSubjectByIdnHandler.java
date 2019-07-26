package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import static org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntitySubject.SELECT_TX_SUBJECT_BY_ID;

import java.util.ResourceBundle;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntitySubject;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult.ExecutionStatus;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This API implementation is completely ignorant of tenant settings and visibility. If one has
 * subject id, this API will give first level information about the subject with specified id.
 *
 * @author ashish.
 */

class FetchSubjectByIdnHandler implements DBHandler {

  private final ProcessorContext context;
  private static final Logger LOGGER = LoggerFactory.getLogger(FetchSubjectByIdnHandler.class);
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");
  private AJEntitySubject subject;

  FetchSubjectByIdnHandler(ProcessorContext context) {
    this.context = context;
  }

  @Override
  public ExecutionResult<MessageResponse> checkSanity() {
    if (context.userId() == null || context.userId().isEmpty()) {
      LOGGER.warn("Invalid user");
      return new ExecutionResult<>(MessageResponseFactory.createForbiddenResponse(),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    if (context.subjectId() == null || context.subjectId().isEmpty()) {
      LOGGER.warn("Missing subject id");
      return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  @Override
  public ExecutionResult<MessageResponse> validateRequest() {
    LazyList<AJEntitySubject> subjects = AJEntitySubject
        .where(SELECT_TX_SUBJECT_BY_ID, context.subjectId());
    if (subjects == null || subjects.isEmpty()) {
      return new ExecutionResult<>(
          MessageResponseFactory.createNotFoundResponse(RESOURCE_BUNDLE.getString("not.found")),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    subject = subjects.get(0);
    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  @Override
  public ExecutionResult<MessageResponse> executeRequest() {
    return new ExecutionResult<>(MessageResponseFactory.createOkayResponse(subject.asJson()),
        ExecutionStatus.SUCCESSFUL);
  }

  @Override
  public boolean handlerReadOnly() {
    return true;
  }
}
