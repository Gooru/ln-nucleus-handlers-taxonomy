package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entities;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("tenant_setting")
public class AJEntityTenantSetting extends Model {
	public static final String VALUE = "value";

	public static final String SELECT_TENANT_SETTING = "";
	public static final String SELECT_TENANT_SETTING_TX_SUB_CLASSIFIER_PREFS = "SELECT value FROM tenant_setting WHERE id = ?::uuid and key = 'tx_sub_classifier_prefs'";
	public static final String SELECT_TENANT_SETTING_TX_FW_PREFS = "SELECT value FROM tenant_setting WHERE id = ?::uuid and key = 'tx_fw_prefs'";
}
