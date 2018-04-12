package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityLearnerClassification;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTenant;
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

class FetchLearnerClassificationHandler implements DBHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchLearnerClassificationHandler.class);
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");
    private final ProcessorContext context;
    private final List<String> tenantIds = new ArrayList<>();

    public FetchLearnerClassificationHandler(ProcessorContext context) {
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

    @Override
    public ExecutionResult<MessageResponse> validateRequest() {
        LazyList<AJEntityTenantSetting> tenantSettings =
            AJEntityTenantSetting.findBySQL(AJEntityTenantSetting.SELECT_TENANT_SETTING, context.tenant());
        AJEntityTenantSetting tenantSetting = tenantSettings.size() > 0 ? tenantSettings.get(0) : null;
        final String tenantVisibility =
            tenantSetting != null ? tenantSetting.getString(AJEntityTenantSetting.VALUE) : HelperConstants.GLOBAL;
        if (tenantVisibility.contains(HelperConstants.GLOBAL)) {
            LazyList<AJEntityTenant> tenants =
                AJEntityTenant.findBySQL(AJEntityTenant.SELECT_GLOBAL_AND_DISCOVERABLE_TENANTS);
            tenants.forEach(tenant -> this.tenantIds.add(tenant.getString(AJEntityTenant.ID)));
        }

        if (tenantVisibility.contains(HelperConstants.TENANT)) {
            this.tenantIds.add(context.tenant());
        }

        return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
    }

    @Override
    public ExecutionResult<MessageResponse> executeRequest() {
        LazyList<AJEntityLearnerClassification> results =
            AJEntityLearnerClassification.findBySQL(AJEntityLearnerClassification.SELECT_LEARNER_CLASSIFICATIONS,
                HelperUtils.toPostgresArrayString(this.tenantIds.toArray()));
        JsonArray jsonResults = new JsonArray(JsonFormatterBuilder
            .buildSimpleJsonFormatter(false, Arrays.asList(HelperConstants.TX_LEARNER_CLASSIFICATION_RESPONSE_FIELDS))
            .toJson(results));
        return new ExecutionResult<>(
            MessageResponseFactory
                .createOkayResponse(new JsonObject().put(HelperConstants.LEARNER_CLASSIFICATIONS, jsonResults)),
            ExecutionResult.ExecutionStatus.SUCCESSFUL);

    }

    @Override
    public boolean handlerReadOnly() {
        return true;
    }

}
