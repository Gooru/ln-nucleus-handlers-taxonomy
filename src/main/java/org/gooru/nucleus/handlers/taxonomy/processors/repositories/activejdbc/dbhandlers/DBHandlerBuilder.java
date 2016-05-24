package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers;

import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;

public final class DBHandlerBuilder {

  private DBHandlerBuilder() {
    throw new AssertionError();
  }

  public static DBHandler buildFetchSubjectsHandler(ProcessorContext context) {
    return new FetchSubjectsHandler(context);
  }

  public static DBHandler buildFetchCoursesHandler(ProcessorContext context) {
    return new FetchCoursesHandler(context);

  }

  public static DBHandler buildFetchDomainsHandler(ProcessorContext context) {
    return new FetchDomainsHandler(context);
  }

  public static DBHandler buildFetchDomainStandardsHandler(ProcessorContext context) {
    return new FetchDomainCodesHandler(context);
  }
  
  public static DBHandler buildFetchStandardsFrameworkHandler(ProcessorContext context) {
    return new FetchGDFSubjectHandler(context);
  }

}
