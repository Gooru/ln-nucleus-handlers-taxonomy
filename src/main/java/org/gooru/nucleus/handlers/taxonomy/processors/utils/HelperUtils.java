package org.gooru.nucleus.handlers.taxonomy.processors.utils;

import java.util.Collection;
import java.util.Iterator;

public final class HelperUtils {

  public static String toPostgresArrayString(Object[] input) {
    if (input.length == 0) {
      return "{}";
    }

    StringBuilder sb = new StringBuilder();
    sb.append('{');
    int count = 1;
    for (Object code : input) {
      sb.append('"').append(code).append('"');
      if (count == input.length) {
        return sb.append('}').toString();
      }
      sb.append(',');
      count++;
    }

    return null;
  }

  public static String toPostgresArrayString(Collection<String> input) {
    Iterator<String> it = input.iterator();
    if (!it.hasNext()) {
      return "{}";
    }

    StringBuilder sb = new StringBuilder();
    sb.append('{');
    for (; ; ) {
      sb.append('"').append(it.next()).append('"');
      if (!it.hasNext()) {
        return sb.append('}').toString();
      }
      sb.append(',');
    }
  }
}
