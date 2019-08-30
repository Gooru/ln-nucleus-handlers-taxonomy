package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("gut_to_fw_xformation")
public class AJEntityGutToFrameworkTransformation extends Model {

  public final static String FETCH_GUT_TO_FRAMEWORK_TRANSFORMATION =
      "fw_code = ? AND subject_code = ?";

}
