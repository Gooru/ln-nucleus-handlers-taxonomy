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

  public static DBHandler buildFetchCodesHandler(ProcessorContext context) {
    return new FetchCodesHandler(context);
  }

  public static DBHandler buildFetchCrosswalkGDFWCodesHandler(ProcessorContext context) {
    return new FetchCrosswalkGDFWCodesHandler(context);
  }

  public static DBHandler buildFetchCrosswalkFWCodesHandler(ProcessorContext context) {
    return new FetchCrosswalkFWCodesHandler(context);
  }

  public static DBHandler buildTaxonomyTransformationHandler(ProcessorContext context) {
    return new TaxonomyTransformationHandler(context);
  }

  public static DBHandler buildFetchStandardFrameworksHandler(ProcessorContext context) {
    return new FetchStandardFrameworksHandler(context);
  }

  public static DBHandler buildFetchSubjectClassificationsHandler(ProcessorContext context) {
    return new FetchTaxonomySubjectClassificationHandler(context);
  }

  public static DBHandler buildFetchSubjectByIdHandler(ProcessorContext context) {
    return new FetchSubjectByIdnHandler(context);
  }
}
