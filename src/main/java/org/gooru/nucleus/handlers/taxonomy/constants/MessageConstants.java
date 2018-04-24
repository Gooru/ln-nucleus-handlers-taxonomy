package org.gooru.nucleus.handlers.taxonomy.constants;

public final class MessageConstants {

    public static final String MSG_HEADER_OP = "mb.operation";
    public static final String MSG_OP_STATUS = "mb.operation.status";
    public static final String MSG_KEY_SESSION = "session";
    public static final String MSG_OP_STATUS_SUCCESS = "success";
    public static final String MSG_OP_STATUS_ERROR = "error";
    public static final String MSG_OP_STATUS_VALIDATION_ERROR = "error.validation";
    public static final String MSG_USER_ANONYMOUS = "anonymous";
    public static final String MSG_USER_ID = "user_id";
    public static final String MSG_HTTP_STATUS = "http.status";
    public static final String MSG_HTTP_BODY = "http.body";
    public static final String MSG_HTTP_RESPONSE = "http.response";
    public static final String MSG_HTTP_ERROR = "http.error";
    public static final String MSG_HTTP_VALIDATION_ERROR = "http.validation.error";
    public static final String MSG_HTTP_HEADERS = "http.headers";
    public static final String MSG_MESSAGE = "message";

    // Containers for different responses
    public static final String RESP_CONTAINER_MBUS = "mb.container";
    public static final String RESP_CONTAINER_EVENT = "mb.event";

    // Operation names: Also need to be updated in corresponding handlers
    public static final String MSG_OP_TAXONOMY_SUBJECTS_GET = "taxonomy.subjects.get";
    public static final String MSG_OP_TAXONOMY_COURSES_GET = "taxonomy.courses.get";
    public static final String MSG_OP_TAXONOMY_DOMAINS_GET = "taxonomy.domains.get";
    public static final String MSG_OP_TAXONOMY_DOMAIN_CODES_GET = "taxonomy.domain.codes.get";
    public static final String MSG_OP_TAXONOMY_CODES_GET = "taxonomy.codes.get";
    public static final String MSG_OP_TAXONOMY_CROSSWALK_GDFW_GET = "taxonomy.crosswalk.gdfw.get";
    public static final String MSG_OP_TAXONOMY_CROSSWALK_FW_GET = "taxonomy.crosswalk.fw.get";
    public static final String MSG_OP_TAXONOMY_TRANSFORM = "taxonomy.transform";
    public static final String MSG_OP_TAXONOMY_FRAMEWORKS_GET = "taxonomy.frameworks.get";
    public static final String MSG_OP_TAXONOMY_SUBJECT_CLASSIFICATIONS_GET = "taxonomy.subject.classifications.get";

    public static final String ID_TX_SUBJECT = "subjectId";
    public static final String ID_TX_COURSE = "courseId";
    public static final String ID_TX_DOMAIN = "domainId";
    public static final String ID_TX_STANDARD_FRAMEWORK = "standardFrameworkId";
    public static final String ID_TX_CODE = "codeId";

    private MessageConstants() {
        throw new AssertionError();
    }
}
