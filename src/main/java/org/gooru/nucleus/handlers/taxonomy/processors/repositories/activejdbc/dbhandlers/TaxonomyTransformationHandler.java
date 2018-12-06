package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import io.vertx.core.json.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTaxonomyCodeMapping;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.gooru.nucleus.handlers.taxonomy.processors.utils.HelperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaxonomyTransformationHandler implements DBHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(FetchDomainCodesHandler.class);
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");
  private JsonObject standardPrefs;
  private JsonObject taxonomy;
  private final ProcessorContext context;

  public TaxonomyTransformationHandler(ProcessorContext context) {
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
      LOGGER.warn("invalid request received to do taxonomy transformation");
      return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(
          "invalid request received to do taxonomy transformation"),
          ExecutionResult.ExecutionStatus.FAILED);
    }

    standardPrefs = context.request().getJsonObject(HelperConstants.STANDARD_PREFS);
    if (standardPrefs == null || standardPrefs.isEmpty()) {
      LOGGER.warn("user standard preference is missing");
      return new ExecutionResult<>(
          MessageResponseFactory
              .createInvalidRequestResponse("user standard preference is missing"),
          ExecutionResult.ExecutionStatus.FAILED);
    }

    taxonomy = context.request().getJsonObject(HelperConstants.TAXONOMY);
    if (taxonomy == null || taxonomy.isEmpty()) {
      LOGGER.warn("taxonomy is missing for transformation");
      return new ExecutionResult<>(
          MessageResponseFactory
              .createInvalidRequestResponse("taxonomy is missing for transformation"),
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
    JsonObject resultSet = null;
    JsonObject transformedTaxonomyCodes = transformTaxonomyCodeByUserPref();
    if (transformedTaxonomyCodes.isEmpty()) {
      resultSet = this.taxonomy;
    } else {
      resultSet = mergeTransformedCodes(transformedTaxonomyCodes);
    }
    return new ExecutionResult<>(MessageResponseFactory.createOkayResponse(resultSet),
        ExecutionResult.ExecutionStatus.SUCCESSFUL);
  }

  private Set<String> getTargetCodes() {
    final Set<String> targetCodes = new HashSet<>();
    taxonomy.fieldNames().forEach(id -> {
      JsonObject standards = taxonomy.getJsonObject(id);
      standards.fieldNames().forEach(codeId -> {
        final String subject = getSubjectFromCodeId(codeId);
        if (standardPrefs.containsKey(subject)) {
          final String framework = standardPrefs.getString(subject);
          if (framework != null && !codeId.startsWith(framework + HelperConstants.DOT)) {
            targetCodes.add(codeId);
          }
        }
      });
    });
    return targetCodes;
  }

  private JsonObject transformTaxonomyCodeByUserPref() {
    final JsonObject transformedCodes = new JsonObject();
    Set<String> targetCodes = getTargetCodes();
    if (targetCodes.size() > 0) {
      final List<AJEntityTaxonomyCodeMapping> sourceCodes =
          AJEntityTaxonomyCodeMapping
              .where(AJEntityTaxonomyCodeMapping.INTERNAL_TARGET_CODES_TO_SOURCE_CODES,
                  HelperUtils.toPostgresArrayString(targetCodes.toArray()));
      if (sourceCodes.size() > 0) {
        final Map<String, String> codeMapping = new HashMap<>();
        final Map<String, List<String>> frameworkCodeMap = new HashMap<>();
        sourceCodes.forEach(sourceCode -> {
          final String sourceCodeId = sourceCode.getString(HelperConstants.SOURCE_TAXONOMY_CODE_ID);
          final String subject = getSubjectFromCodeId(sourceCodeId);
          final String framework = standardPrefs.getString(subject);
          codeMapping
              .put(sourceCodeId, sourceCode.getString(HelperConstants.TARGET_TAXONOMY_CODE_ID));
          List<String> targetInputCodes = null;
          if (frameworkCodeMap.containsKey(framework)) {
            targetInputCodes = frameworkCodeMap.get(framework);
          } else {
            targetInputCodes = new ArrayList<>();
          }
          targetInputCodes.add(sourceCodeId);
          frameworkCodeMap.put(framework, targetInputCodes);
        });
        frameworkCodeMap.forEach((framework, code) -> {
          final List<AJEntityTaxonomyCodeMapping> userPrefCodes = AJEntityTaxonomyCodeMapping.where(
              AJEntityTaxonomyCodeMapping.INTERNAL_SOURCE_CODES_TO_TARGET_CODES,
              HelperUtils.toPostgresArrayString(code.toArray()), framework);
          if (userPrefCodes.size() > 0) {
            userPrefCodes.forEach(codes -> {
              final String sourceCodeId = codes.getString(HelperConstants.SOURCE_TAXONOMY_CODE_ID);
              final String targetCodeId = codes.getString(HelperConstants.TARGET_TAXONOMY_CODE_ID);
              JsonObject transformedCode = new JsonObject();
              transformedCode.put(HelperConstants.TITLE, codes.get(HelperConstants.TARGET_TITLE));
              transformedCode
                  .put(HelperConstants.CODE, codes.get(HelperConstants.TARGET_DISPLAY_CODE));
              transformedCode.put(HelperConstants.FRAMEWORK_CODE,
                  codes.get(HelperConstants.TARGET_FRAMEWORK_ID));
              transformedCode.put(HelperConstants.TARGET_TAXONOMY_CODE_ID, targetCodeId);
              transformedCodes.put(codeMapping.get(sourceCodeId), transformedCode);

            });
          }
        });
      }
    }
    return transformedCodes;
  }

  private JsonObject mergeTransformedCodes(JsonObject transformedCodes) {
    final JsonObject transformedTaxonomy = new JsonObject();
    taxonomy.fieldNames().forEach(id -> {
      JsonObject standards = taxonomy.getJsonObject(id);
      JsonObject standard = new JsonObject();
      standards.fieldNames().forEach(codeId -> {
        JsonObject code = standards.getJsonObject(codeId);
        if (transformedCodes.containsKey(codeId)) {
          final JsonObject transformedCode = transformedCodes.getJsonObject(codeId).copy();
          final String targetCodeId = transformedCode
              .getString(HelperConstants.TARGET_TAXONOMY_CODE_ID);
          transformedCode
              .put(HelperConstants.PARENT_TITLE, code.getString(HelperConstants.PARENT_TITLE));
          transformedCode.remove(HelperConstants.TARGET_TAXONOMY_CODE_ID);
          standard.put(targetCodeId, transformedCode);
        } else {
          standard.put(codeId, code);
        }
      });
      transformedTaxonomy.put(id, standard);
    });
    return transformedTaxonomy;
  }

  private String getSubjectFromCodeId(String codeId) {
    return codeId.substring((codeId.indexOf(HelperConstants.DOT) + 1),
        codeId.indexOf(HelperConstants.HYPHEN));
  }

  @Override
  public boolean handlerReadOnly() {
    return true;
  }
}
