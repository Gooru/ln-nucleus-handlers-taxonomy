package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("taxonomy_code")
public class AJEntityTaxonomyCode extends Model {

    public final static String TAXONOMY_CODES_GET = "default_code_id  ilike ? and standard_framework_id = ?";
}
