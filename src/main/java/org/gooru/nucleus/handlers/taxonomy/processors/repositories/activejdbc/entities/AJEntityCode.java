package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("code")
public class AJEntityCode extends Model {

  public final static String TAXONOMY_ROOT_NODE_GET = "parent_code_id is null and standard_framework_id = ?";
  
  public final static String TAXONOMY_SUB_NODE_GET = "parent_code_id = ? and standard_framework_id = ?";

}
