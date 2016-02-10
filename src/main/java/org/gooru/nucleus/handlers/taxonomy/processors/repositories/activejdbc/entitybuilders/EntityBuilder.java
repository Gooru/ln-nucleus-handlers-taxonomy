package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.entitybuilders;

import io.vertx.core.json.JsonObject;

import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.converters.ConverterRegistry;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.converters.FieldConverter;
import org.javalite.activejdbc.Model;


public interface EntityBuilder<T extends Model> {

  /*
   * Populate the model from JSON object provided. Note that it does not validate the input with respect to whether the field is allowed, or a
   * field is mandatory to be populated in model but it is not. For that have a look at validators package
   */
  default void build(T model, JsonObject input, ConverterRegistry registry) {
    if (model == null || input == null || input.isEmpty()) {
      return;
    }
    input.forEach(entry -> {
      if (registry != null) {
        FieldConverter converter = registry.lookupConverter(entry.getKey());
        Object value = entry.getValue();
        if (converter != null) {
          value = converter.convertField(entry.getValue());
        }
        model.set(entry.getKey(), value);
      }
    });
  }


}
