package org.gooru.nucleus.handlers.taxonomy.processors;

import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;

public interface Processor {
  MessageResponse process();
}
