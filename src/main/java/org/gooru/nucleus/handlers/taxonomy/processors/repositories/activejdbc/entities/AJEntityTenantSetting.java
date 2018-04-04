package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("tenant_setting")
public class AJEntityTenantSetting extends Model {
	public static final String VALUE = "value";

	public static final String SELECT_TENANT_SETTING = "SELECT value FROM tenant_setting WHERE id = ?::uuid and key = 'tx_visibility'";
}
