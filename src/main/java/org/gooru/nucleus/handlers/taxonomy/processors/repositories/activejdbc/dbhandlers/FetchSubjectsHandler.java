package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityFramework;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntitySubject;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTaxonomySubjectClassification;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTenantSetting;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatterBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult.ExecutionStatus;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.gooru.nucleus.handlers.taxonomy.processors.utils.HelperUtils;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FetchSubjectsHandler implements DBHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(FetchSubjectsHandler.class);
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");
  private final ProcessorContext context;
  private String classificationType = null;
  private String standardFrameworkId = null;
  private JsonObject taxonomyFrameworkPreferences = null;

  public FetchSubjectsHandler(ProcessorContext context) {
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
    JsonArray classification = context.request().getJsonArray(HelperConstants.CLASSIFICATION_TYPE);
    if (context.request() == null || classification == null || classification.isEmpty()) {
      LOGGER.warn("classification type is missing.");
      return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(
          RESOURCE_BUNDLE.getString("missing.classification.type")), ExecutionStatus.FAILED);
    }
    this.classificationType = classification.getString(0);

    JsonArray framework = context.request().getJsonArray(HelperConstants.FRAMEWORK_ID);
    if (framework != null) {
      this.standardFrameworkId = framework.getString(0);
    }

    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  @Override
  public ExecutionResult<MessageResponse> validateRequest() {
    LazyList<AJEntityTaxonomySubjectClassification> taxonomySubjectClassifications =
        AJEntityTaxonomySubjectClassification.findBySQL(
            AJEntityTaxonomySubjectClassification.SELECT_TAXONOMY_SUBJECT_CLASSIFICATIONS_BY_ID,
            this.classificationType);
    AJEntityTaxonomySubjectClassification taxonomySubjectClassification =
        taxonomySubjectClassifications.size() > 0 ? taxonomySubjectClassifications.get(0) : null;
    if (taxonomySubjectClassification == null) {
      LOGGER
          .warn("Taxonomy Subject Classification {} not found, aborting", this.classificationType);
      return new ExecutionResult<>(
          MessageResponseFactory.createNotFoundResponse(RESOURCE_BUNDLE.getString("not.found")),
          ExecutionStatus.FAILED);

    }
    LazyList<AJEntityTenantSetting> taxonomySubjectClassificationTenantSettings = AJEntityTenantSetting
        .findBySQL(AJEntityTenantSetting.SELECT_TENANT_SETTING_TX_SUB_CLASSIFIER_PREFS,
            context.tenant());
    AJEntityTenantSetting taxonomySubjectClassificationTenantSetting =
        taxonomySubjectClassificationTenantSettings.size() > 0
            ? taxonomySubjectClassificationTenantSettings.get(0)
            : null;
    boolean isGlobalVisible = true;
    JsonArray ids = null;
    if (taxonomySubjectClassificationTenantSetting != null) {
      JsonObject tenantTaxonomySubjectClassificationPrefs =
          new JsonObject(
              taxonomySubjectClassificationTenantSetting.getString(AJEntityTenantSetting.VALUE));
      isGlobalVisible = tenantTaxonomySubjectClassificationPrefs
          .getBoolean(HelperConstants.IS_GLOBAL_VISIBLE);
      ids = tenantTaxonomySubjectClassificationPrefs.getJsonArray(HelperConstants.IDS);
    }
    boolean tenantVisiblity =
        taxonomySubjectClassification
            .getBoolean(AJEntityTaxonomySubjectClassification.TENANT_VISIBILITY);
    if (!(isGlobalVisible && tenantVisiblity) && !(ids != null && ids
        .contains(this.classificationType))) {
      LOGGER.warn("Taxonomy Subject Classification {} is not visible for this tenant {}",
          this.classificationType,
          context.tenant());
      return new ExecutionResult<>(
          MessageResponseFactory.createForbiddenResponse(RESOURCE_BUNDLE.getString("not.allowed")),
          ExecutionResult.ExecutionStatus.FAILED);
    }

    if (standardFrameworkId != null) {
      LazyList<AJEntityFramework> frameworks =
          AJEntityFramework
              .findBySQL(AJEntityFramework.STANDARD_FRAMEWORK, this.standardFrameworkId);
      AJEntityFramework framework = frameworks.size() > 0 ? frameworks.get(0) : null;
      if (framework == null) {
        LOGGER.warn("framework {} not found, aborting", this.standardFrameworkId);
        return new ExecutionResult<>(
            MessageResponseFactory.createNotFoundResponse(RESOURCE_BUNDLE.getString("not.found")),
            ExecutionStatus.FAILED);
      }
    }

    LazyList<AJEntityTenantSetting> taxonomyFrameworkTenantSettings =
        AJEntityTenantSetting
            .findBySQL(AJEntityTenantSetting.SELECT_TENANT_SETTING_TX_FW_PREFS, context.tenant());
    AJEntityTenantSetting taxonomyFrameworkTenantSetting =
        taxonomyFrameworkTenantSettings.size() > 0 ? taxonomyFrameworkTenantSettings.get(0) : null;
    if (taxonomyFrameworkTenantSetting != null) {
      taxonomyFrameworkPreferences =
          new JsonObject(taxonomyFrameworkTenantSetting.getString(AJEntityTenantSetting.VALUE));
    }

    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public ExecutionResult<MessageResponse> executeRequest() {
    if (standardFrameworkId == null) {
      LazyList<AJEntitySubject> results = AJEntitySubject
          .where(AJEntitySubject.GUF_SUBJECTS_GET, this.classificationType)
          .orderBy(HelperConstants.SEQUENCE_ID);
      JsonArray jsonResults = new JsonArray();
      results.forEach(result -> {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put(AJEntitySubject.ID, result.get(AJEntitySubject.ID));
        jsonObject.put(AJEntitySubject.TITLE, result.get(AJEntitySubject.TITLE));
        jsonObject.put(AJEntitySubject.DESCRIPTION, result.get(AJEntitySubject.DESCRIPTION));
        jsonObject.put(AJEntitySubject.CODE, result.get(AJEntitySubject.CODE));
        jsonObject.put(AJEntitySubject.STANDARD_FRAMEWORK_ID,
            result.get(AJEntitySubject.STANDARD_FRAMEWORK_ID));
        if (result.getBoolean(AJEntitySubject.HAS_STANDARD_FRAMEWORK)) {
          boolean isGlobalVisible = true;
          List<String> frameworkIds = new ArrayList<>(0);
          if (taxonomyFrameworkPreferences != null) {
            JsonObject frameworkSubjectPrefs =
                taxonomyFrameworkPreferences.getJsonObject(result.getString(AJEntitySubject.ID));
            if (frameworkSubjectPrefs != null) {
              isGlobalVisible = frameworkSubjectPrefs.getBoolean(HelperConstants.IS_GLOBAL_VISIBLE);
              JsonArray ids = frameworkSubjectPrefs.getJsonArray(HelperConstants.FW_IDS);
              if (ids != null) {
                frameworkIds = ids.getList();
              }

            }
          }
          List<Map> frameworkResults = null;
          if (isGlobalVisible) {
            frameworkResults = Base.findAll(AJEntityFramework.STANDARD_FRAMEWORKS_GLOBALS,
                result.get(AJEntitySubject.ID),
                HelperUtils.toPostgresArrayString(frameworkIds.toArray()));
          } else {
            frameworkResults = Base.findAll(AJEntityFramework.STANDARD_FRAMEWORKS_BY_IDS,
                result.get(AJEntitySubject.ID),
                HelperUtils.toPostgresArrayString(frameworkIds.toArray()));
          }

          jsonObject.put(AJEntityFramework.FRAMEWORKS, frameworkResults);
        }
        jsonResults.add(jsonObject);
      });
      return new ExecutionResult<>(
          MessageResponseFactory
              .createOkayResponse(new JsonObject().put(HelperConstants.SUBJECTS, jsonResults)),
          ExecutionResult.ExecutionStatus.SUCCESSFUL);
    } else {
      LazyList<AJEntitySubject> results =
          AJEntitySubject
              .where(AJEntitySubject.SUBJECTS_GET, this.classificationType, standardFrameworkId)
              .orderBy(HelperConstants.SEQUENCE_ID);
      JsonArray jsonResults = new JsonArray(JsonFormatterBuilder
          .buildSimpleJsonFormatter(false, Arrays.asList(HelperConstants.TX_RESPONSE_FIELDS))
          .toJson(results));
      return new ExecutionResult<>(
          MessageResponseFactory
              .createOkayResponse(new JsonObject().put(HelperConstants.SUBJECTS, jsonResults)),
          ExecutionResult.ExecutionStatus.SUCCESSFUL);
    }

  }

  @Override
  public boolean handlerReadOnly() {
    return true;
  }

}
