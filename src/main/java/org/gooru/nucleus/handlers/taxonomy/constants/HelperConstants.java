package org.gooru.nucleus.handlers.taxonomy.constants;

public final class HelperConstants {

  public static final String SEQUENCE_ID = "sequence_id";
  public final static String CLASSIFICATION_TYPE = "classification_type";
  public final static String SUBJECTS = "subjects";
  public final static String[] TX_RESPONSE_FIELDS = { "id", "code", "title", "description", "has_standard_framework", "sequence_id"};
  public final static String SUBJECT_CODE = "subject_code";
  public final static String COURSES = "courses";
  public final static String DOMAINS = "domains";
  public final static String CODES = "codes";
  public static final String FRAMEWORK_ID = "framework_id";
  public static final String TARGET_FRAMEWORK_ID  = "target_framework_id";
  public static final String IS_INTERNAL_CODE  = "is_internal_code";
  public static final String MAPPED_OUT  = "mapped_out";
  public static final String MAPPED  = "mapped";
  public static final String NOT_MAPPED  = "not_mapped";
  public static final String SOURCE_DISPLAY_CODE = "source_display_code";
  public static final String SOURCE_TAXONOMY_CODE_ID = "source_taxonomy_code_id";
  public static final String TARGET_DISPLAY_CODE = "target_display_code";
  public static final String TARGET_TAXONOMY_CODE_ID = "target_taxonomy_code_id";
  public final static String[] TX_COURSE_RESPONSE_FIELDS = {"id", "default_taxonomy_course_id", "code", "title", "description", "sequence_id"};
  public final static String[] TX_DOMAIN_RESPONSE_FIELDS = {"id", "default_taxonomy_domain_id", "code", "title", "description", "sequence_id"};
  public final static String[] TX_CODES_RESPONSE_FIELDS = {"id", "parent_taxonomy_code_id", "code", "title", "description", "code_type", "is_selectable", "sequence_id"};
  public final static String[] TX_CROSSWALK_CODES_RESPONSE_FIELDS = {"target_display_code", "target_title", "target_description", "target_code_type", "target_sequence_id"};


    private HelperConstants() {
        throw new AssertionError();
    }
}
