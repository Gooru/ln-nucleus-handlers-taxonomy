package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;

public interface DBHandler {
    ExecutionResult<MessageResponse> checkSanity();

    ExecutionResult<MessageResponse> validateRequest();

    ExecutionResult<MessageResponse> executeRequest();

    boolean handlerReadOnly();
}
