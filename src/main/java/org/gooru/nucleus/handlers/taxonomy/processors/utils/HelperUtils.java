package org.gooru.nucleus.handlers.taxonomy.processors.utils;

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
}
