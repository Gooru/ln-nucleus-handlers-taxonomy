package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("default_subject")
public class AJEntityDefaultSubject extends Model {

    public final static String SUBJECTS_GET = "subject_classification = ?::subject_classification_type";

}
