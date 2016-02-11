package org.gooru.nucleus.handlers.taxonomy.constants;


public final class HelperConstants {

  public static final String SEQUENCE_ID = "sequence_id";
  public final static String CLASSIFICATION_TYPE = "classification_type";
  public final static String SUBJECTS = "subjects";
  public final static String[] SUBJECTS_RESPONSE_FIELDS = {"code", "display_code"};
  
  private HelperConstants() {
    throw new AssertionError();
  }
}
