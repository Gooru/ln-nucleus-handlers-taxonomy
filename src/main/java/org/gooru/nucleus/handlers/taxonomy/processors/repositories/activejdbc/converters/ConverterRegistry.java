package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.converters;

public interface ConverterRegistry {
  FieldConverter lookupConverter(String fieldName);
}
