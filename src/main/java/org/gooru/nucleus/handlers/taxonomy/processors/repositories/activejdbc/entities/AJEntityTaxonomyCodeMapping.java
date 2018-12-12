package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("taxonomy_code_mapping")
public class AJEntityTaxonomyCodeMapping extends Model {

  public final static String INTERNAL_SOURCE_CODES_TO_TARGET_CODES =
      "source_taxonomy_code_id = ANY (?::varchar[]) and target_framework_id = ?";
  public final static String DISPLAY_SOURCE_CODES_TO_TARGET_CODES =
      "source_display_code = ANY (?::varchar[]) and target_framework_id = ?";
  public final static String INTERNAL_TARGET_CODES_TO_SOURCE_CODES = "target_taxonomy_code_id = ANY (?::varchar[])";
  public final static String DISPLAY_TARGET_CODES_TO_SOURCE_CODES = "target_display_code = ANY (?::varchar[])";

}
