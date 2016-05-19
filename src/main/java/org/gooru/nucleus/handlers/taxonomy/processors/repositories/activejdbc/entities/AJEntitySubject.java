package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("taxonomy_subject")
public class AJEntitySubject extends Model {
  public final static String SUBJECTS_GET = "subject_classification = ?::subject_classification_type and standard_framework_id = ? and  is_visible = true";
}
