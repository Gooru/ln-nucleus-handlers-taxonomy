package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

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
  public final static String SUBJECTS_GET = "subject_classification = ?::subject_classification_type and standard_framework_id = ? and  is_visible = true";
  public final static String GUF_SUBJECTS_GET = "subject_classification = ?::subject_classification_type and standard_framework_id = 'GUF' and  is_visible = true";
}
