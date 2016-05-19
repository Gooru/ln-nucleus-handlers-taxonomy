package org.gooru.nucleus.handlers.taxonomy.processors.repositories;

import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;

public interface TaxonomyRepo {
    MessageResponse fetchSubjects();

    MessageResponse fetchCourses();

    MessageResponse fetchDomains();

    MessageResponse fetchDomainStandards();

    MessageResponse fetchGUFSubjectStandardFrameworks();

}
