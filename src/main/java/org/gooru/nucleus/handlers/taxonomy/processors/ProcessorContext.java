package org.gooru.nucleus.handlers.taxonomy.processors;

import org.gooru.nucleus.handlers.taxonomy.constants.MessageConstants;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

public class ProcessorContext {

    private final String userId;
    private final JsonObject session;
    private final JsonObject request;
    private final MultiMap headers;

    public ProcessorContext(String userId, JsonObject session, JsonObject request, MultiMap headers) {
        if (session == null || userId == null || session.isEmpty()) {
            throw new IllegalStateException("Processor Context creation failed because of invalid values");
        }
        this.userId = userId;
        this.session = session.copy();
        this.request = request != null ? request.copy() : null;
        this.headers = headers;
    }

    public String userId() {
        return this.userId;
    }

    public JsonObject session() {
        return this.session.copy();
    }

    public JsonObject request() {
        return this.request;
    }

    public String subjectId() {
        return this.headers != null ? this.headers.get(MessageConstants.ID_TX_SUBJECT) : null;
    }

    public String domainId() {
        return this.headers != null ? this.headers.get(MessageConstants.ID_TX_DOMAIN) : null;
    }

    public String courseId() {
        return this.headers != null ? this.headers.get(MessageConstants.ID_TX_COURSE) : null;
    }

    public String standardFrameworkId() {
        return this.headers != null ? this.headers.get(MessageConstants.ID_TX_STANDARD_FRAMEWORK) : null;
    }

    public MultiMap requestHeaders() {
        return this.headers;
    }

}
