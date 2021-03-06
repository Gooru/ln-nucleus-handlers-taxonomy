
package org.gooru.nucleus.handlers.taxonomy.processors.commands;

import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.repositories.RepoBuilder;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;

/**
 * @author renuka
 */
public class GutToFrameworkTransformationProcessor extends AbstractCommandProcessor {

  public GutToFrameworkTransformationProcessor(ProcessorContext context) {
    super(context);
  }

  @Override
  protected void setDeprecatedVersions() {
    // NOOP
  }

  @Override
  protected MessageResponse processCommand() {
    return RepoBuilder.buildTaxonomyRepo(context).fetchGutToFrameworkTransformation();
  }

}
