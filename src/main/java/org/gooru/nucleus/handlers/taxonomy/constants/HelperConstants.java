package org.gooru.nucleus.handlers.taxonomy.constants;

public final class HelperConstants {

  public static final String SEQUENCE_ID = "sequence_id";
  public final static String CLASSIFICATION_TYPE = "classification_type";
  public final static String SUBJECTS = "subjects";
  public final static String[] TX_RESPONSE_FIELDS = { "id", "code", "title", "description", "has_standard_framework"};
  public final static String SUBJECT_CODE = "subject_code";
  public final static String COURSES = "courses";
  public final static String DOMAINS = "domains";
  public final static String CODES = "codes";
  public static final String FRAMEWORK_ID = "framework_id";
  public final static String[] TX_COURSE_RESPONSE_FIELDS = {"id", "default_taxonomy_course_id", "code", "title", "description"};
  public final static String[] TX_DOMAIN_RESPONSE_FIELDS = {"id", "default_taxonomy_domain_id", "code", "title", "description"};
  public final static String[] TX_CODES_RESPONSE_FIELDS = {"id", "code", "title", "description", "code_type", "is_selectable"};


    private HelperConstants() {
        throw new AssertionError();
    }
}
