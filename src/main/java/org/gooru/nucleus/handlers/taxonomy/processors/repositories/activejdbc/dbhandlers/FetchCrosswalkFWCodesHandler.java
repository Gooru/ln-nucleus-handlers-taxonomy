package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTaxonomyCodeMapping;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatterBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.gooru.nucleus.handlers.taxonomy.processors.utils.HelperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

class FetchCrosswalkFWCodesHandler implements DBHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(FetchCrosswalkFWCodesHandler.class);
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");
  private final ProcessorContext context;
  private JsonArray codes;
  private Boolean isInternalCode;
  private Boolean mappedOut;
  private String targetFrameworkId;

  public FetchCrosswalkFWCodesHandler(ProcessorContext context) {
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
    if (context.request() == null || context.request().isEmpty()) {
      LOGGER.warn("invalid request received to get crosswalk codes");
      return new ExecutionResult<>(
          MessageResponseFactory
              .createInvalidRequestResponse("Invalid data provided to fetch crosswalk codes"),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    this.codes = context.request().getJsonArray(HelperConstants.CODES);
    if (this.codes == null || codes.isEmpty()) {
      LOGGER.warn("input internal / display codes missing");
      return new ExecutionResult<>(
          MessageResponseFactory
              .createInvalidRequestResponse("input internal / display code missing"),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    this.targetFrameworkId = context.request().getString(HelperConstants.TARGET_FRAMEWORK_ID);
    if (this.targetFrameworkId == null || this.targetFrameworkId.isEmpty()) {
      LOGGER.warn("target framework id is missing");
      return new ExecutionResult<>(
          MessageResponseFactory.createInvalidRequestResponse("target framework id is missing"),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    this.isInternalCode = context.request().getBoolean(HelperConstants.IS_INTERNAL_CODE);
    this.mappedOut = context.request().getBoolean(HelperConstants.MAPPED_OUT);
    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  @Override
  public ExecutionResult<MessageResponse> validateRequest() {
    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  @Override
  public ExecutionResult<MessageResponse> executeRequest() {
    final JsonObject results = new JsonObject();
    JsonObject mappedResults = new JsonObject();
    List<AJEntityTaxonomyCodeMapping> codes = null;
    List<AJEntityTaxonomyCodeMapping> targetCodes = null;
    final String key =
        (this.isInternalCode == null || !this.isInternalCode) ? HelperConstants.TARGET_DISPLAY_CODE
            : HelperConstants.TARGET_TAXONOMY_CODE_ID;
    String inputCodes = HelperUtils.toPostgresArrayString(this.codes.getList().toArray());
    if (this.isInternalCode == null || !this.isInternalCode) {
      targetCodes = AJEntityTaxonomyCodeMapping
          .where(AJEntityTaxonomyCodeMapping.DISPLAY_TARGET_CODES_TO_SOURCE_CODES, inputCodes);
    } else {
      targetCodes = AJEntityTaxonomyCodeMapping
          .where(AJEntityTaxonomyCodeMapping.INTERNAL_TARGET_CODES_TO_SOURCE_CODES, inputCodes);
    }

    if (targetCodes != null && targetCodes.size() > 0) {
      final List<String> sourceCodes = new ArrayList<>();
      targetCodes.forEach(code -> {
        sourceCodes.add(code.getString(HelperConstants.SOURCE_TAXONOMY_CODE_ID));
      });
      codes = AJEntityTaxonomyCodeMapping
          .where(AJEntityTaxonomyCodeMapping.INTERNAL_SOURCE_CODES_TO_TARGET_CODES,
              HelperUtils.toPostgresArrayString(sourceCodes.toArray()), targetFrameworkId);

      if (codes != null && codes.size() > 0) {
        codes.forEach(code -> {
          results.put(code.getString(key),
              new JsonObject(JsonFormatterBuilder.buildSimpleJsonFormatter(false,
                  Arrays.asList(HelperConstants.TX_CROSSWALK_CODES_RESPONSE_FIELDS)).toJson(code)));
        });
      }
    }
    mappedResults.put(HelperConstants.MAPPED, results.copy());
    JsonObject notMappedResults = new JsonObject();
    this.codes.forEach(code -> {
      if (!results.containsKey((String) code)) {
        results.put((String) code, (Object) null);
        notMappedResults.put((String) code, (Object) null);
      }
    });
    mappedResults.put(HelperConstants.NOT_MAPPED, notMappedResults);
    JsonObject resultSet = (mappedOut == null || mappedOut) ? results : mappedResults;
    return new ExecutionResult<>(MessageResponseFactory.createOkayResponse(resultSet),
        ExecutionResult.ExecutionStatus.SUCCESSFUL);

  }

  @Override
  public boolean handlerReadOnly() {
    return true;
  }

}
