package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.Arrays;
import java.util.ResourceBundle;
import org.gooru.nucleus.handlers.taxonomy.constants.HelperConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTaxonomyCourse;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityTenantSetting;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatterBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FetchCoursesHandler implements DBHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(FetchCoursesHandler.class);
  private final ProcessorContext context;
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");
  private JsonObject taxonomyFrameworkPreferences = null;


  public FetchCoursesHandler(ProcessorContext context) {
    this.context = context;
  }

  @Override
  public ExecutionResult<MessageResponse> checkSanity() {
    if (context.userId() == null || context.userId().isEmpty()) {
      LOGGER.warn("Invalid user");
      return new ExecutionResult<>(MessageResponseFactory.createForbiddenResponse(),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    // There should be an standard framework and subject id present
    if (context.subjectId() == null || context.subjectId().isEmpty()
        || context.standardFrameworkId() == null || context.standardFrameworkId().isEmpty()) {
      LOGGER.warn("Missing standard framework and subject id");
      return new ExecutionResult<>(MessageResponseFactory.createInvalidRequestResponse(),
          ExecutionResult.ExecutionStatus.FAILED);
    }
    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  @Override
  public ExecutionResult<MessageResponse> validateRequest() {
    LazyList<AJEntityTenantSetting> taxonomyFrameworkTenantSettings = AJEntityTenantSetting
        .findBySQL(AJEntityTenantSetting.SELECT_TENANT_SETTING_TX_FW_PREFS, context.tenant());
    AJEntityTenantSetting taxonomyFrameworkTenantSetting =
        taxonomyFrameworkTenantSettings.size() > 0 ? taxonomyFrameworkTenantSettings.get(0) : null;
    if (taxonomyFrameworkTenantSetting != null) {
      taxonomyFrameworkPreferences =
          new JsonObject(taxonomyFrameworkTenantSetting.getString(AJEntityTenantSetting.VALUE));
    }
    return new ExecutionResult<>(null, ExecutionResult.ExecutionStatus.CONTINUE_PROCESSING);
  }

  @Override
  public ExecutionResult<MessageResponse> executeRequest() {
    LazyList<AJEntityTaxonomyCourse> results =
        AJEntityTaxonomyCourse.where(AJEntityTaxonomyCourse.COURSES_GET, context.subjectId(),
            context.standardFrameworkId()).orderBy(HelperConstants.SEQUENCE_ID);
    String defaultCourseId = null;
    if (taxonomyFrameworkPreferences != null) {
      JsonObject frameworkSubjectPrefs =
          taxonomyFrameworkPreferences.getJsonObject(getGutSubjectCode());
      if (frameworkSubjectPrefs != null) {
        defaultCourseId = frameworkSubjectPrefs.getString(HelperConstants.DEFAULT_COURSE_ID);
      }
    }

    JsonArray courses = new JsonArray(JsonFormatterBuilder
        .buildSimpleJsonFormatter(false, Arrays.asList(HelperConstants.TX_COURSE_RESPONSE_FIELDS))
        .toJson(results));
    populateDefaultCourse(defaultCourseId, courses);
    return new ExecutionResult<>(
        MessageResponseFactory
            .createOkayResponse(new JsonObject().put(HelperConstants.COURSES, courses)),
        ExecutionResult.ExecutionStatus.SUCCESSFUL);
  }


  private void populateDefaultCourse(String defaultCourseId, JsonArray courses) {
    courses.forEach(course -> {
      JsonObject courseAsJsonObject = (JsonObject) course;
      String courseId = courseAsJsonObject.getString(HelperConstants.ID);
      if (defaultCourseId != null && defaultCourseId.equalsIgnoreCase(courseId))
        courseAsJsonObject.put(HelperConstants.IS_DEFAULT, true);
    });
  }

  private String getGutSubjectCode() {
    String[] subjectCodeSplitter = context.subjectId().split("\\.");
    if (subjectCodeSplitter.length == 3) {
      return subjectCodeSplitter[1] + "." + subjectCodeSplitter[2];
    }
    return context.subjectId();
  }

  @Override
  public boolean handlerReadOnly() {
    return true;
  }

}
