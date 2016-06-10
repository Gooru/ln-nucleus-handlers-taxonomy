package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("taxonomy_code")
public class AJEntityTaxonomyCode extends Model {

  public final static String TAXONOMY_CODES_GET = "taxonomy_domain_id  = ? and standard_framework_id = ?";
  public final static String TAXONOMY_CODE_GET = "id  = ANY (?::varchar[])";
}
