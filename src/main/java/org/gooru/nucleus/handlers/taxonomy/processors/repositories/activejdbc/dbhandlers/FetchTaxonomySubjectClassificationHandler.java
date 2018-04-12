package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTaxonomySubjectClassification;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTenantSetting;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatterBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.gooru.nucleus.handlers.taxonomy.processors.utils.HelperUtils;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

class FetchTaxonomySubjectClassificationHandler implements DBHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchTaxonomySubjectClassificationHandler.class);
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");
    private final ProcessorContext context;
    private boolean isGlobalVisible = true;
    private List<String> subjectClassificationIds = new ArrayList<>();

    public FetchTaxonomySubjectClassificationHandler(ProcessorContext context) {
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
        return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ExecutionResult<MessageResponse> validateRequest() {
        LazyList<AJEntityTenantSetting> tenantSettings = AJEntityTenantSetting
            .findBySQL(AJEntityTenantSetting.SELECT_TENANT_SETTING_TX_SUB_CLASSIFIER_PREFS, context.tenant());
        AJEntityTenantSetting tenantSetting = tenantSettings.size() > 0 ? tenantSettings.get(0) : null;
        if (tenantSetting != null) {
            JsonObject tenantTaxonomySubjectClassificationPrefs =
                new JsonObject(tenantSetting.getString(AJEntityTenantSetting.VALUE));
            if (tenantTaxonomySubjectClassificationPrefs != null) {
                this.isGlobalVisible =
                    tenantTaxonomySubjectClassificationPrefs.getBoolean(HelperConstants.IS_GLOBAL_VISIBLE);
                JsonArray ids = tenantTaxonomySubjectClassificationPrefs.getJsonArray(HelperConstants.IDS);
                if (ids != null) {
                    this.subjectClassificationIds = ids.getList();
                }
            }
        }

        return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
    }

    @Override
    public ExecutionResult<MessageResponse> executeRequest() {
        LazyList<AJEntityTaxonomySubjectClassification> results;
        if (this.isGlobalVisible) {
            results = AJEntityTaxonomySubjectClassification.findBySQL(
                AJEntityTaxonomySubjectClassification.SELECT_TAXONOMY_SUBJECT_CLASSIFICATIONS_GOLBAL,
                HelperUtils.toPostgresArrayString(this.subjectClassificationIds.toArray()));
        } else {
            results = AJEntityTaxonomySubjectClassification.findBySQL(
                AJEntityTaxonomySubjectClassification.SELECT_TAXONOMY_SUBJECT_CLASSIFICATIONS_BY_IDS,
                HelperUtils.toPostgresArrayString(this.subjectClassificationIds.toArray()));
        }

        JsonArray jsonResults = new JsonArray(JsonFormatterBuilder
            .buildSimpleJsonFormatter(false, Arrays.asList(HelperConstants.TX_LEARNER_CLASSIFICATION_RESPONSE_FIELDS))
            .toJson(results));
        return new ExecutionResult<>(
            MessageResponseFactory
                .createOkayResponse(new JsonObject().put(HelperConstants.SUBJECT_CLASSIFICATIONS, jsonResults)),
            ExecutionResult.ExecutionStatus.SUCCESSFUL);

    }

    @Override
    public boolean handlerReadOnly() {
        return true;
    }

}
