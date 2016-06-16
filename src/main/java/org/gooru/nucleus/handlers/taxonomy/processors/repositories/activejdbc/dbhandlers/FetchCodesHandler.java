package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTaxonomyCode;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatterBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class FetchCodesHandler implements DBHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchCodesHandler.class);      
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");        
    private final ProcessorContext context;
    String codeIdList = null;
    public static final Pattern COMMA = Pattern.compile(",");
    
    public FetchCodesHandler(ProcessorContext context) {
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
        
        codeIdList = readRequestParam("idList");
        if (codeIdList == null) {
            LOGGER.warn("Missing code ids");
            return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(),
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
        String[] codeIds = COMMA.split(codeIdList);
        LazyList<AJEntityTaxonomyCode> results =        
                        AJEntityTaxonomyCode.where(AJEntityTaxonomyCode.TAXONOMY_CODE_GET, toPostgresArrayString(codeIds));
        if (codeIds.length != results.size()) {
            LOGGER.warn("id count does not match with count in database");
            return new ExecutionResult<>(
                MessageResponseFactory.createValidationErrorResponse(
                    new JsonObject().put("message", RESOURCE_BUNDLE.getString("id.count.mismatch"))),
                ExecutionResult.ExecutionStatus.FAILED);
        }
        
        return new ExecutionResult<>(MessageResponseFactory.createOkayResponse(new JsonObject().put(HelperConstants.CODES, new JsonArray(
            JsonFormatterBuilder.buildSimpleJsonFormatter(false, Arrays.asList(HelperConstants.TX_CODES_RESPONSE_FIELDS)).toJson(results)))),
            ExecutionResult.ExecutionStatus.SUCCESSFUL);
    }

    @Override
    public boolean handlerReadOnly() {
        return true;
    }
    
    public static String toPostgresArrayString(String[] input) {
        if (input.length == 0) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        int count = 1;
        for (String code : input) {
            sb.append('"').append(code).append('"');
            if (count == input.length) {
                return sb.append('}').toString();
            }
            sb.append(',');
            count++;
        }

        return null;
    }
    
    private String readRequestParam(String param) {
        JsonArray requestParams = context.request().getJsonArray(param);
        if (requestParams == null || requestParams.isEmpty()) {
            return null;
        }

        String value = requestParams.getString(0);
        return (value != null && !value.isEmpty()) ? value : null;
    }
}