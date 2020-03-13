package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import io.vertx.core.json.JsonObject;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("taxonomy_subject")
public class AJEntitySubject extends Model {

  public final static String HAS_STANDARD_FRAMEWORK = "has_standard_framework";
  public final static String ID = "id";
  public final static String TITLE = "title";
  public final static String CODE = "code";
  public final static String STANDARD_FRAMEWORK_ID = "standard_framework_id";
  public final static String DESCRIPTION = "description";
  public final static String SUBJECTS_GET =
      "taxonomy_subject_classification_id = ? and standard_framework_id = ? "
          + "and  is_visible = true";
  public final static String GUT_SUBJECTS_GET_GLOBALS =
      "taxonomy_subject_classification_id = ? AND standard_framework_id is null AND "
          + "default_taxonomy_subject_id is null and  is_visible = true AND (tenant_visibility = true OR id = ANY(?::varchar[]))";
  public final static String GUT_SUBJECTS_GET_BY_IDS =
      "taxonomy_subject_classification_id = ? AND standard_framework_id is null AND "
          + "default_taxonomy_subject_id is null and  is_visible = true AND id = ANY(?::varchar[])";
  public final static String SELECT_TX_SUBJECT_BY_ID = "id = ?";

  public JsonObject asJson() {
    JsonObject result = new JsonObject();
    result.put(ID, this.getString(ID));
    result.put(TITLE, this.getString(TITLE));
    result.put(STANDARD_FRAMEWORK_ID, this.getString(STANDARD_FRAMEWORK_ID));
    result.put(CODE, this.getString(CODE));
    result.put(DESCRIPTION, this.getString(DESCRIPTION));
    return result;
  }
}
