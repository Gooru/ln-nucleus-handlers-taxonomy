package org.gooru.nucleus.handlers.taxonomy.processors.commands;

import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.RepoBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;

/**
 * @author ashish on 2/1/17.
 */
class TaxonomyCodesProcessor extends AbstractCommandProcessor {

  public TaxonomyCodesProcessor(ProcessorContext context) {
    super(context);
  }

  @Override
  protected void setDeprecatedVersions() {

  }

  @Override
  protected MessageResponse processCommand() {
    return RepoBuilder.buildTaxonomyRepo(context).fetchCodes();
  }
}
