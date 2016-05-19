package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc;

import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.TaxonomyRepo;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.dbhandlers.DBHandlerBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.transactions.TransactionExecutor;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;


class AJTaxonomyRepo implements TaxonomyRepo {
  private final ProcessorContext context;

  public AJTaxonomyRepo(ProcessorContext context) {
    this.context = context;
  }

  @Override
  public MessageResponse fetchSubjects() {
    return TransactionExecutor.executeTransaction(DBHandlerBuilder.buildFetchSubjectsHandler(context));
  }

  @Override
  public MessageResponse fetchCourses() {
    return TransactionExecutor.executeTransaction(DBHandlerBuilder.buildFetchCoursesHandler(context));
  }

  @Override
  public MessageResponse fetchDomainStandards() {
    return TransactionExecutor.executeTransaction(DBHandlerBuilder.buildFetchDomainStandardsHandler(context));

  }

  @Override
  public MessageResponse fetchDomains() {
    return TransactionExecutor.executeTransaction(DBHandlerBuilder.buildFetchDomainsHandler(context));
  }

  @Override
  public MessageResponse fetchGUFSubjectStandardFrameworks() {
    return TransactionExecutor.executeTransaction(DBHandlerBuilder.buildFetchStandardsFrameworkHandler(context));
  }

}
