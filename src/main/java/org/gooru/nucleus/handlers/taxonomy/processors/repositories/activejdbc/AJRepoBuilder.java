package org.gooru.nucleus.handlers.taxonomy.processors.repositories.activejdbc;

import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.TaxonomyRepo;

public final class AJRepoBuilder {

    private AJRepoBuilder() {
        throw new AssertionError();
    }

    public static TaxonomyRepo buildTaxonomyRepo(ProcessorContext context) {
        return new AJTaxonomyRepo(context);
    }

}
