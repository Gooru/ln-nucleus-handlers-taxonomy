package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("taxonomy_domain")
public class AJEntityTaxonomyDomain extends Model {
  public final static String DOMAINS_GET = "taxonomy_course_id = ? and standard_framework_id = ?";
}