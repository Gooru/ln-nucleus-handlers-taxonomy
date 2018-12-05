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
    public MessageResponse fetchCodes() {
        return TransactionExecutor.executeTransaction(DBHandlerBuilder.buildFetchCodesHandler(context));
    }

    @Override
    public MessageResponse fetchCrosswalkGDFWCodes() {
        return TransactionExecutor.executeTransaction(DBHandlerBuilder.buildFetchCrosswalkGDFWCodesHandler(context));
    }

    @Override
    public MessageResponse fetchCrosswalkFWCodes() {
        return TransactionExecutor.executeTransaction(DBHandlerBuilder.buildFetchCrosswalkFWCodesHandler(context));
    }

    @Override
    public MessageResponse transformTaxonomy() {
        return TransactionExecutor.executeTransaction(DBHandlerBuilder.buildTaxonomyTransformationHandler(context));
    }

    @Override
    public MessageResponse fetchStandardFrameworks() {
        return TransactionExecutor.executeTransaction(DBHandlerBuilder.buildFetchStandardFrameworksHandler(context));
    }
    
    @Override
    public MessageResponse fetchSubjectClassifications() {
        return TransactionExecutor.executeTransaction(DBHandlerBuilder.buildFetchSubjectClassificationsHandler(context));
    }

    @Override
    public MessageResponse fetchSubjectById() {
        return TransactionExecutor.executeTransaction(DBHandlerBuilder.buildFetchSubjectByIdHandler(context));
    }
}
