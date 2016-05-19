package org.gooru.nucleus.handlers.taxonomy.processors.repositories;

import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc.AJRepoBuilder;

public final class RepoBuilder {

    private RepoBuilder() {
        throw new AssertionError();
    }

    public static TaxonomyRepo buildTaxonomyRepo(ProcessorContext context) {
        return AJRepoBuilder.buildTaxonomyRepo(context);
    }

}
