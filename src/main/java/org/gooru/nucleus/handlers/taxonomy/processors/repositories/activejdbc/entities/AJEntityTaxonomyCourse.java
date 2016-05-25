package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("taxonomy_course")
public class AJEntityTaxonomyCourse extends Model {
    public final static String COURSES_GET = "taxonomy_subject_id = ? and standard_framework_id = ?";
    public final static String COURSES = "courses";
    public final static String GUF_COURSES_GET = "taxonomy_subject_id = ?";
}
