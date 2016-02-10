package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.converters;

import java.sql.SQLException;

import org.postgresql.util.PGobject;

public interface FieldConverter {
  static PGobject convertFieldToJson(String value) {
    String JSONB_TYPE = "jsonb";
    PGobject pgObject = new PGobject();
    pgObject.setType(JSONB_TYPE);
    try {
      pgObject.setValue(value);
      return pgObject;
    } catch (SQLException e) {
      return null;
    }
  }

  static PGobject convertFieldToUuid(String value) {
    String UUID_TYPE = "uuid";
    PGobject pgObject = new PGobject();
    pgObject.setType(UUID_TYPE);
    try {
      pgObject.setValue(value);
      return pgObject;
    } catch (SQLException e) {
      return null;
    }
  }

  static PGobject convertFieldToNamedType(String value, String type) {
    PGobject pgObject = new PGobject();
    pgObject.setType(type);
    try {
      pgObject.setValue(value);
      return pgObject;
    } catch (SQLException e) {
      return null;
    }
  }

  PGobject convertField(Object fieldValue);
}
