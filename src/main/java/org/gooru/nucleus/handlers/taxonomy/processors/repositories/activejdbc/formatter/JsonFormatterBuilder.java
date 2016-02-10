package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter;

import java.util.List;

public final class JsonFormatterBuilder {

  private JsonFormatterBuilder() {
  }

  public static JsonFormatter buildSimpleJsonFormatter(boolean pretty, List<String> attributes) {

    return new SimpleJsonFormatter(pretty, attributes);
  }

  public static JsonFormatter buildSimpleJsonFormatter(boolean pretty) {

    return new SimpleJsonFormatter(pretty, null);
  }
}
