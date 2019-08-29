package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("fw_subject_competency_crosswalk")
public class AJEntityFrameworkSubjectCompetencyCrosswalk extends Model {

  public final static String FETCH_FRAMEWORK_SUBJECT_COMPETENCY_CROSSWALK =
      "fw_code = ? AND subject_code = ?";

}
