package org.gooru.nucleus.handlers.taxonomy.processors;

import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import java.util.ResourceBundle;
import java.util.UUID;
import org.gooru.nucleus.handlers.taxonomy.constants.MessageConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.commands.CommandProcessorBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.exceptions.VersionDeprecatedException;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MessageProcessor implements Processor {

  private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);
  private final Message<Object> message;
  private String userId;
  private JsonObject session;
  private JsonObject request;
  private MultiMap headers;
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");

  public MessageProcessor(Message<Object> message) {
    this.message = message;
  }

  @Override
  public MessageResponse process() {
    try {
      ExecutionResult<MessageResponse> validateResult = validateAndInitialize();
      if (validateResult.isCompleted()) {
        return validateResult.result();
      }

      final String msgOp = message.headers().get(MessageConstants.MSG_HEADER_OP);
      return CommandProcessorBuilder.lookupBuilder(msgOp).build(createContext()).process();
    } catch (VersionDeprecatedException e) {
      LOGGER.error("Version is deprecated");
      return MessageResponseFactory.createVersionDeprecatedResponse();
    } catch (Throwable e) {
      LOGGER.error("Unhandled exception in processing", e);
      return MessageResponseFactory.createInternalErrorResponse();
    }
  }

  private ProcessorContext createContext() {
    return new ProcessorContext(userId, session, request, headers);
  }

  private ExecutionResult<MessageResponse> validateAndInitialize() {
    if (message == null || !(message.body() instanceof JsonObject)) {
      LOGGER.error("Invalid message received, either null or body of message is not JsonObject ");
      return new ExecutionResult<>(
          MessageResponseFactory
              .createInvalidRequestResponse(RESOURCE_BUNDLE.getString("invalid.message")),
          ExecutionResult.ExecutionStatus.FAILED);
    }

    userId = ((JsonObject) message.body()).getString(MessageConstants.MSG_USER_ID);
    if (!validateUser(userId)) {
      LOGGER.error("Invalid user id passed. Not authorized.");
      return new ExecutionResult<>(
          MessageResponseFactory.createForbiddenResponse(RESOURCE_BUNDLE.getString("missing.user")),
          ExecutionResult.ExecutionStatus.FAILED);
    }

    session = ((JsonObject) message.body()).getJsonObject(MessageConstants.MSG_KEY_SESSION);
    request = ((JsonObject) message.body()).getJsonObject(MessageConstants.MSG_HTTP_BODY);
    headers = message.headers();

    if (session == null || session.isEmpty()) {
      LOGGER.error("Invalid session obtained, probably not authorized properly");
      return new ExecutionResult<>(
          MessageResponseFactory
              .createForbiddenResponse(RESOURCE_BUNDLE.getString("missing.session")),
          ExecutionResult.ExecutionStatus.FAILED);
    }

    if (request == null) {
      LOGGER.error("Invalid JSON payload on Message Bus");
      return new ExecutionResult<>(
          MessageResponseFactory
              .createInvalidRequestResponse(RESOURCE_BUNDLE.getString("invalid.payload")),
          ExecutionResult.ExecutionStatus.FAILED);
    }

    // All is well, continue processing
    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  private boolean validateUser(String userId) {
    return !(userId == null || userId.isEmpty()) && (
        userId.equalsIgnoreCase(MessageConstants.MSG_USER_ANONYMOUS)
            || validateUuid(userId));
  }

  private boolean validateUuid(String uuidString) {
    try {
      UUID.fromString(uuidString);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    } catch (Exception e) {
      return false;
    }
  }

}
