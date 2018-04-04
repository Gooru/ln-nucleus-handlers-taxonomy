package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("learner_classification")
public class AJEntityLearnerClassification extends Model {
	
	public static final String SELECT_LEARNER_CLASSIFICATIONS = "SELECT id, title, code FROM learner_classification WHERE tenant = ANY(?::uuid[]) "
			+ "ORDER BY tenant, sequence_id";
	
}
