package org.gooru.nucleus.handlers.taxonomy.processors.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.gooru.nucleus.handlers.taxonomy.constants.MessageConstants;
import org.gooru.nucleus.handlers.taxonomy.processors.Processor;
import org.gooru.nucleus.handlers.taxonomy.processors.ProcessorContext;
import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish on 2/1/17.
 */
public enum CommandProcessorBuilder {

    DEFAULT("default") {
        private final Logger LOGGER = LoggerFactory.getLogger(CommandProcessorBuilder.class);
        private final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");

        @Override
        public Processor build(ProcessorContext context) {
            return () -> {
                LOGGER.error("Invalid operation type passed in, not able to handle");
                return MessageResponseFactory
                    .createInvalidRequestResponse(RESOURCE_BUNDLE.getString("invalid.operation"));
            };
        }
    },
    TAXONOMY_SUBJECTS_GET(MessageConstants.MSG_OP_TAXONOMY_SUBJECTS_GET) {
        @Override
        public Processor build(ProcessorContext context) {
            return new TaxonomySubjectsProcessor(context);
        }
    },
    TAXONOMY_COURSES_GET(MessageConstants.MSG_OP_TAXONOMY_COURSES_GET) {
        @Override
        public Processor build(ProcessorContext context) {
            return new TaxonomyCoursesProcessor(context);
        }
    },
    TAXONOMY_DOMAINS_GET(MessageConstants.MSG_OP_TAXONOMY_DOMAINS_GET) {
        @Override
        public Processor build(ProcessorContext context) {
            return new TaxonomyDomainsProcessor(context);
        }
    },
    TAXONOMY_DOMAIN_CODES_GET(MessageConstants.MSG_OP_TAXONOMY_DOMAIN_CODES_GET) {
        @Override
        public Processor build(ProcessorContext context) {
            return new TaxonomyDomainCodesProcessor(context);
        }
    },
    TAXONOMY_CODES_GET(MessageConstants.MSG_OP_TAXONOMY_CODES_GET) {
        @Override
        public Processor build(ProcessorContext context) {
            return new TaxonomyCodesProcessor(context);
        }
    },
    TAXONOMY_CROSSWALK_GDFW_CODES_GET(MessageConstants.MSG_OP_TAXONOMY_CROSSWALK_GDFW_GET) {
        @Override
        public Processor build(ProcessorContext context) {
            return new TaxonomyCrosswalkGDFWCodesProcessor(context);
        }
    },
    TAXONOMY_CROSSWALK_FW_CODES_GET(MessageConstants.MSG_OP_TAXONOMY_CROSSWALK_FW_GET) {
        @Override
        public Processor build(ProcessorContext context) {
            return new TaxonomyCrosswalkFWCodesProcessor(context);
        }
    },
    TAXONOMY_TRANSFORM(MessageConstants.MSG_OP_TAXONOMY_TRANSFORM) {
        @Override
        public Processor build(ProcessorContext context) {
            return new TaxonomyTransformProcessor(context);
        }
    },
    TAXONOMY_STANDARD_FRAMEWORKS(MessageConstants.MSG_OP_TAXONOMY_FRAMEWORKS_GET) {
        @Override
        public Processor build(ProcessorContext context) {
            return new TaxonomyStandardFrameworksProcessor(context);
        }
    };

    private String name;

    CommandProcessorBuilder(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    private static final Map<String, CommandProcessorBuilder> LOOKUP = new HashMap<>();

    static {
        for (CommandProcessorBuilder builder : values()) {
            LOOKUP.put(builder.getName(), builder);
        }
    }

    public static CommandProcessorBuilder lookupBuilder(String name) {
        CommandProcessorBuilder builder = LOOKUP.get(name);
        if (builder == null) {
            return DEFAULT;
        }
        return builder;
    }

    public abstract Processor build(ProcessorContext context);
}
