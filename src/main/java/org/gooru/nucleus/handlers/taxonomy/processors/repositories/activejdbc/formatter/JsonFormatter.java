package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

public interface JsonFormatter {

  <T extends Model> String toJson(T model);

  <T extends Model> String toJson(LazyList<T> modelList);

  @SuppressWarnings("unchecked")
  static JsonArray toJson(@SuppressWarnings("rawtypes") List<Map> results) {
    JsonArray jsonResults = new JsonArray();
    if (results != null) {
      results.stream().forEach(result -> {
        JsonObject jsonResult = new JsonObject();
        result.forEach((key, value) -> {
          jsonResult.put((String) key, value);
        });
        jsonResults.add(jsonResult);
      });
    }
    return jsonResults;
  }
}
