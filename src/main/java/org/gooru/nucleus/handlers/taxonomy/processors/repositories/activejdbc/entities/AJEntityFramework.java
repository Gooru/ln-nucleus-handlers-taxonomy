package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("standard_framework")
public class AJEntityFramework extends Model {
  public static final String FRAMEWORKS = "frameworks";
  public static final String  STANDARD_FRAMEWORKS_GLOBALS = "select sf.id as standard_framework_id, sf.title, s.id as taxonomy_subject_id, s.title as taxonomy_subject_title "
      + "from standard_framework sf inner join taxonomy_subject s on s.standard_framework_id = sf.id where s.default_taxonomy_subject_id = ? "
      + "AND (sf.tenant_visibility = true OR sf.id = ANY(?::varchar[])) ORDER BY sf.sequence_id";
  public static final String  STANDARD_FRAMEWORKS_BY_IDS = "select sf.id as standard_framework_id, sf.title, s.id as taxonomy_subject_id, s.title as taxonomy_subject_title "
      + "from standard_framework sf inner join taxonomy_subject s on s.standard_framework_id = sf.id where s.default_taxonomy_subject_id = ? "
      + "AND sf.id = ANY(?::varchar[]) ORDER BY sf.sequence_id";
  public static final String STANDARD_FRAMEWORK = "SELECT id FROM standard_framework WHERE id = ?";
}
