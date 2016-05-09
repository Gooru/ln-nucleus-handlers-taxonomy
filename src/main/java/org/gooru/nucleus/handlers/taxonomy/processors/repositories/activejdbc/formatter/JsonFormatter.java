package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.formatter;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

public interface JsonFormatter {

    <T extends Model> String toJson(T model);

    <T extends Model> String toJson(LazyList<T> modelList);

}
