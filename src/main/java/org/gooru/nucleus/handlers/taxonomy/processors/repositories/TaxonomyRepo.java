package org.gooru.nucleus.handlers.taxonomy.processors.repositories;

import org.gooru.nucleus.handlers.taxonomy.processors.responses.MessageResponse;

public interface TaxonomyRepo {

  MessageResponse fetchSubjects();

  MessageResponse fetchCourses();

  MessageResponse fetchDomains();

  MessageResponse fetchDomainStandards();

  MessageResponse fetchCodes();

  MessageResponse fetchCrosswalkGDFWCodes();

  MessageResponse fetchCrosswalkFWCodes();

  MessageResponse transformTaxonomy();

  MessageResponse fetchStandardFrameworks();

  MessageResponse fetchSubjectClassifications();

  MessageResponse fetchSubjectById();

  MessageResponse listTaxonomyFrameworksSubjects();

  MessageResponse fetchGutToFrameworkTransformation();
}
