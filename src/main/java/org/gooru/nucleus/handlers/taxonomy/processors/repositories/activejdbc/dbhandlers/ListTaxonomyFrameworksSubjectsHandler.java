
package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import java.util.ResourceBundle;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities.AJEntityFramework;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter.JsonFormatterBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.ExecutionResult;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author szgooru Created On 21-Dec-2018
 */
public class ListTaxonomyFrameworksSubjectsHandler implements DBHandler {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ListTaxonomyFrameworksSubjectsHandler.class);
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");

  private final ProcessorContext context;
  private String subjectCode;

  public ListTaxonomyFrameworksSubjectsHandler(ProcessorContext context) {
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

    this.subjectCode = readRequestParam("subject");
    if (this.subjectCode == null || this.subjectCode.isEmpty()) {
      LOGGER.warn("invalid subject code passed");
      return new ExecutionResult<>(
          MessageResponseFactory
              .createInvalidRequestResponse(RESOURCE_BUNDLE.getString("invalid.subject.code")),
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
    LazyList<AJEntityFramework> frameworks =
        AJEntityFramework.findBySQL(AJEntityFramework.LIST_FRAMEWORKS_SUBJECT, this.subjectCode);

    return new ExecutionResult<>(
        MessageResponseFactory
            .createOkayResponse(
                new JsonObject()
                    .put(AJEntityFramework.FRAMEWORKS_RESP_JSON,
                        new JsonArray(JsonFormatterBuilder.buildSimpleJsonFormatter(false,
                            AJEntityFramework.FRAMEWORKS_FIELD_LIST).toJson(frameworks)))),
        ExecutionResult.ExecutionStatus.SUCCESSFUL);
  }

  @Override
  public boolean handlerReadOnly() {
    return true;
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
