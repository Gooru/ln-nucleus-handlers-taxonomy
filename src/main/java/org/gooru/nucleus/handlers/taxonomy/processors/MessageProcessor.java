package org.gooru.nucleus.handlers.taxonomy.processors;

import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import java.util.ResourceBundle;
import java.util.UUID;

import org.gooru.nucleus.handlers.taxonomy.constants.MessageConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.RepoBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MessageProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);
    private final Message<Object> message;
    private String userId;
    private JsonObject prefs;
    private JsonObject request;
    private MultiMap headers;
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");

    public MessageProcessor(Message<Object> message) {
        this.message = message;
    }

    @Override
    public MessageResponse process() {
        MessageResponse result;
        try {
            // Validate the message itself
            ExecutionResult<MessageResponse> validateResult = validateAndInitialize();
            if (validateResult.isCompleted()) {
                return validateResult.result();
            }

            final String msgOp = message.headers().get(MessageConstants.MSG_HEADER_OP);
            switch (msgOp) {
            case MessageConstants.MSG_OP_TAXONOMY_SUBJECTS_GET:
                result = processSubjects();
                break;
            case MessageConstants.MSG_OP_TAXONOMY_COURSES_GET:
                result = processCourses();
                break;
            case MessageConstants.MSG_OP_TAXONOMY_DOMAINS_GET:
                result = processDomains();
                break;
            case MessageConstants.MSG_OP_TAXONOMY_DOMAIN_CODES_GET:
                result = processDomainStandards();
                break;
            case MessageConstants.MSG_OP_TAXONOMY_CODES_GET:
                result = processCodes();
                break;
            default:
                LOGGER.error("Invalid operation type passed in, not able to handle");
                return MessageResponseFactory.createInvalidRequestResponse(RESOURCE_BUNDLE
                    .getString("invalid.operation"));
            }
            return result;
        } catch (Throwable e) {
            LOGGER.error("Unhandled exception in processing", e);
            return MessageResponseFactory.createInternalErrorResponse();
        }
    }

    private MessageResponse processSubjects() {
        ProcessorContext context = createContext();
        return RepoBuilder.buildTaxonomyRepo(context).fetchSubjects();
    }

    private MessageResponse processCourses() {
        ProcessorContext context = createContext();
        return RepoBuilder.buildTaxonomyRepo(context).fetchCourses();
    }

    private MessageResponse processDomains() {
        ProcessorContext context = createContext();
        return RepoBuilder.buildTaxonomyRepo(context).fetchDomains();
    }

    private MessageResponse processDomainStandards() {
        ProcessorContext context = createContext();
        return RepoBuilder.buildTaxonomyRepo(context).fetchDomainStandards();
    }
    
    private MessageResponse processCodes() {
        ProcessorContext context = createContext();
        return RepoBuilder.buildTaxonomyRepo(context).fetchCodes();
    }

    private ProcessorContext createContext() {
        return new ProcessorContext(userId, prefs, request, headers);
    }

    private ExecutionResult<MessageResponse> validateAndInitialize() {
        if (message == null || !(message.body() instanceof JsonObject)) {
            LOGGER.error("Invalid message received, either null or body of message is not JsonObject ");
            return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(RESOURCE_BUNDLE
                .getString("invalid.message")), ExecutionResult.ExecutionStatus.FAILED);
        }

        userId = ((JsonObject) message.body()).getString(MessageConstants.MSG_USER_ID);
        if (!validateUser(userId)) {
            LOGGER.error("Invalid user id passed. Not authorized.");
            return new ExecutionResult<>(MessageResponseFactory.createForbiddenResponse(RESOURCE_BUNDLE
                .getString("missing.user")), ExecutionResult.ExecutionStatus.FAILED);
        }

        prefs = ((JsonObject) message.body()).getJsonObject(MessageConstants.MSG_KEY_PREFS);
        request = ((JsonObject) message.body()).getJsonObject(MessageConstants.MSG_HTTP_BODY);
        headers = message.headers();

        if (prefs == null || prefs.isEmpty()) {
            LOGGER.error("Invalid preferences obtained, probably not authorized properly");
            return new ExecutionResult<>(MessageResponseFactory.createForbiddenResponse(RESOURCE_BUNDLE
                .getString("missing.preferences")), ExecutionResult.ExecutionStatus.FAILED);
        }

        if (request == null) {
            LOGGER.error("Invalid JSON payload on Message Bus");
            return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(RESOURCE_BUNDLE
                .getString("invalid.payload")), ExecutionResult.ExecutionStatus.FAILED);
        }

        // All is well, continue processing
        return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
    }

    private boolean validateUser(String userId) {
        return !(userId == null || userId.isEmpty())
            && (userId.equalsIgnoreCase(MessageConstants.MSG_USER_ANONYMOUS) || validateUuid(userId));
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
