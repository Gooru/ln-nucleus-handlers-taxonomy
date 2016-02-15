package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("taxonomy_course")
public class AJEntityTaxonomyCourse extends Model {

  public final static String COURSES_GET = "default_course_id ilike ? and standard_framework_id = ?";

}
