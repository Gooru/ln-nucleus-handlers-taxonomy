
package org.gooru.nucleus.handlers.taxonomy.processors.commands;

import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.RepoBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;

/**
 * @author szgooru Created On 21-Dec-2018
 */
public class TaxonomyFrameworksSubjectsListProcessor extends AbstractCommandProcessor {

  public TaxonomyFrameworksSubjectsListProcessor(ProcessorContext context) {
    super(context);
  }

  @Override
  protected void setDeprecatedVersions() {
    // NOOP
  }

  @Override
  protected MessageResponse processCommand() {
    return RepoBuilder.buildTaxonomyRepo(context).listTaxonomyFrameworksSubjects();
  }

}
