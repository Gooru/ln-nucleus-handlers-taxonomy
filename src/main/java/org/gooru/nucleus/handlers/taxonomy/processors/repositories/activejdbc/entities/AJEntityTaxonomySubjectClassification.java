package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("taxonomy_subject_classification")
public class AJEntityTaxonomySubjectClassification extends Model {
	
	public static final String SELECT_TAXONOMY_SUBJECT_CLASSIFICATIONS_GOLBAL = "SELECT id, title, code FROM taxonomy_subject_classification WHERE tenant_visibility = true "
	    + "OR id = ANY(?::varchar[]) ORDER BY sequence_id";
	
	public static final String SELECT_TAXONOMY_SUBJECT_CLASSIFICATIONS_BY_IDS = "SELECT id, title, code FROM taxonomy_subject_classification WHERE id = ANY(?::varchar[]) ORDER BY sequence_id";
	
	public static final String SELECT_TAXONOMY_SUBJECT_CLASSIFICATIONS_BY_ID = "SELECT id, tenant_visibility from taxonomy_subject_classification where id = ?";
	
	public static final String TENANT_VISIBILITY = "tenant_visibility";
}
