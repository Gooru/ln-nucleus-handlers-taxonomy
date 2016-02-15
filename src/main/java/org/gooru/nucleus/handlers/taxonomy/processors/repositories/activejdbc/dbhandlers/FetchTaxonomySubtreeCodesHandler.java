package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import java.util.Arrays;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityCode;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatterBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FetchTaxonomySubtreeCodesHandler implements DBHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(FetchTaxonomyRootCodesHandler.class);
  private final ProcessorContext context;
  private static final String CODES = "codes";

  public FetchTaxonomySubtreeCodesHandler(ProcessorContext context) {
    this.context = context;
  }

  @Override
  public ExecutionResult<MessageResponse> checkSanity() {
    if (context.userId() == null || context.userId().isEmpty()) {
      LOGGER.warn("Invalid user");
      return new ExecutionResult<>(MessageResponseFactory.createForbiddenResponse(), ExecutionResult.ExecutionStatus.FAILED);
    }

    // There should be an standard framework and code id present
    if (context.standardFrameworkId() == null || context.standardFrameworkId().isEmpty() || context.codeId() == null || context.codeId().isEmpty()) {
      LOGGER.warn("Missing standard framework / code id");
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
    LazyList<AJEntityCode> results =
            AJEntityCode.where(AJEntityCode.TAXONOMY_SUB_NODE_GET, context.codeId(), context.standardFrameworkId()).orderBy(
                    HelperConstants.SEQUENCE_ID);
    return new ExecutionResult<>(MessageResponseFactory.createOkayResponse(new JsonObject().put(CODES, new JsonArray(JsonFormatterBuilder
            .buildSimpleJsonFormatter(false, Arrays.asList(HelperConstants.TX_DUMMY_CODES_RESPONSE_FIELDS)).toJson(results)))),
            ExecutionResult.ExecutionStatus.SUCCESSFUL);
  }

  @Override
  public boolean handlerReadOnly() {
    return true;
  }

}
