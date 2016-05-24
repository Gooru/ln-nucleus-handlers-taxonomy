package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityFramework;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntitySubject;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTaxonomyCourse;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatter;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatterBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult.ExecutionStatus;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FetchGUFSubjectHandler implements DBHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchGUFSubjectHandler.class);
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");
    private final ProcessorContext context;
    private static final String SUBJECTS = "subjects";
    private String classificationType = null;

    public FetchGUFSubjectHandler(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public ExecutionResult<MessageResponse> checkSanity() {
        if (context.userId() == null || context.userId().isEmpty()) {
            LOGGER.warn("Invalid user");
            return new ExecutionResult<>(MessageResponseFactory.createForbiddenResponse(),
                ExecutionResult.ExecutionStatus.FAILED);
        }

        JsonArray classification = context.request().getJsonArray(HelperConstants.CLASSIFICATION_TYPE);
        if (context.request() == null || classification == null || classification.isEmpty()) {
            LOGGER.warn("classification type is missing.");
            return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(RESOURCE_BUNDLE
                .getString("missing.classification.type")), ExecutionStatus.FAILED);
        }
        this.classificationType = classification.getString(0);

        return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
    }

    @Override
    public ExecutionResult<MessageResponse> validateRequest() {
        return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ExecutionResult<MessageResponse> executeRequest() {
        LazyList<AJEntitySubject> results =
            AJEntitySubject.where(AJEntitySubject.GUF_SUBJECTS_GET, this.classificationType).orderBy(
                HelperConstants.SEQUENCE_ID);
        JsonArray jsonResults = new JsonArray();
        results.forEach(result -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.put(AJEntitySubject.ID, result.get(AJEntitySubject.ID));
            jsonObject.put(AJEntitySubject.TITLE, result.get(AJEntitySubject.TITLE));
            jsonObject.put(AJEntitySubject.DESCRIPTION, result.get(AJEntitySubject.DESCRIPTION));
            jsonObject.put(AJEntitySubject.CODE, result.get(AJEntitySubject.CODE));
            jsonObject.put(AJEntitySubject.STANDARD_FRAMEWORK_ID, result.get(AJEntitySubject.STANDARD_FRAMEWORK_ID));
            if (result.getBoolean(AJEntitySubject.HAS_STANDARD_FRAMEWORK)) {
                List<Map> frameworkResults =
                    Base.findAll(AJEntityFramework.STANDARD_FRAMEWORKS, result.get(AJEntitySubject.ID));
                jsonObject.put(AJEntityFramework.FRAMEWORKS, frameworkResults);
            } else {
                LazyList<AJEntityTaxonomyCourse> courses = AJEntityTaxonomyCourse.where(AJEntityTaxonomyCourse.GUF_COURSES_GET, result.get(AJEntitySubject.ID)).orderBy(HelperConstants.SEQUENCE_ID);
                jsonObject.put(AJEntityTaxonomyCourse.COURSES, new JsonArray(JsonFormatterBuilder.buildSimpleJsonFormatter(false, Arrays.asList(HelperConstants.TX_COURSE_RESPONSE_FIELDS)).toJson(courses)));
            }
            jsonResults.add(jsonObject);
        });
        return new ExecutionResult<>(MessageResponseFactory.createOkayResponse(new JsonObject().put(SUBJECTS,
            jsonResults)), ExecutionResult.ExecutionStatus.SUCCESSFUL);
    }

    @Override
    public boolean handlerReadOnly() {
        return true;
    }

}
