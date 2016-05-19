package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.ModelDelegate;
import org.javalite.common.Convert;
import org.javalite.common.Escape;
import org.postgresql.util.PGobject;


/**
 * Simple Json formatter is not aware of any parent child relationship and just
 * assumes that formatter is needed to work on the entity provided. The
 * difference with AJ provided Model.toJson() is difference in handling of
 * "jsonb" field type in postgres
 */
class SimpleJsonFormatter implements JsonFormatter {
    private final String[] attributes;
    private final boolean pretty;

    public SimpleJsonFormatter(boolean pretty, List<String> attributes) {
        this.pretty = pretty;
        this.attributes = (attributes != null && !attributes.isEmpty()) ? lowerCased(attributes) : null;
    }

    @Override
    public <T extends Model> String toJson(T model) {
        StringBuilder sb = new StringBuilder();
        String indent = "";
        modelToJson(model, sb, indent);
        return sb.toString();
    }

    @Override
    public <T extends Model> String toJson(LazyList<T> modelList) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if (pretty) {
            sb.append('\n');
        }

        for (int i = 0; i < modelList.size(); i++) {
            if (i > 0) {
                sb.append(',');
                if (pretty) {
                    sb.append('\n');
                }
            }
            T model = modelList.get(i);
            modelToJson(model, sb, (pretty ? "  " : ""));
        }
        if (pretty) {
            sb.append('\n');
        }
        sb.append(']');
        return sb.toString();

    }

    private <T extends Model> void modelToJson(T model, StringBuilder sb, String indent) {
        if (pretty) {
            sb.append(indent);
        }
        sb.append('{');
        String[] names;

        if (this.attributes == null) {
            Set<String> attributeNamesAll = ModelDelegate.attributeNames(model.getClass());
            names = lowerCased(attributeNamesAll);
        } else {
            names = this.attributes;
        }
        int count = 0;
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            Object v = model.get(name);
            if (v != null) {
                if (count > 0) {
                    sb.append(',');
                }
                if (pretty) {
                    sb.append("\n  ").append(indent);
                }
                sb.append('"').append(name).append("\":");
                if (v instanceof Number || v instanceof Boolean) {
                    sb.append(v);
                } else if (v instanceof Date) {
                    sb.append('"').append(Convert.toIsoString((Date) v)).append('"');
                } else if (v instanceof PGobject && ((PGobject) v).getType().equalsIgnoreCase("jsonb")) {
                    sb.append(Convert.toString(v));
                } else {
                    sb.append('"');
                    Escape.json(sb, Convert.toString(v));
                    sb.append('"');
                }
                count = 1;
            }
        }
        if (pretty) {
            sb.append('\n').append(indent);
        }
        sb.append('}');
    }

    private String[] lowerCased(Collection<String> collection) {
        String[] array = new String[collection.size()];
        int i = 0;
        for (String elem : collection) {
            array[i++] = elem.toLowerCase();
        }
        return array;
    }
}
