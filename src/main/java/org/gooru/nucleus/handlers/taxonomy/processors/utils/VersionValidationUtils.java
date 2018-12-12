package org.gooru.nucleus.handlers.taxonomy.processors.utils;

import io.vertx.core.MultiMap;
import java.util.List;
import org.gooru.nucleus.handlers.taxonomy.processors.exceptions.VersionDeprecatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish on 2/1/17.
 */
public final class VersionValidationUtils {

  private static final String API_VERSION = "api.version";
  private static final Logger LOGGER = LoggerFactory.getLogger(VersionValidationUtils.class);

  private VersionValidationUtils() {
    throw new AssertionError();
  }

  public static String validateVersion(List<String> deprecatedVersions, MultiMap messageHeaders) {
    String currentVersion = findVersionFromMessageHeaders(messageHeaders);
    if (currentVersion == null || deprecatedVersions.contains(currentVersion)) {
      LOGGER.error("Version is not supported or null: {}", currentVersion);
      throw new VersionDeprecatedException();
    }
    return currentVersion;
  }

  private static String findVersionFromMessageHeaders(MultiMap messageHeaders) {
    return messageHeaders.get(API_VERSION);
  }
}
