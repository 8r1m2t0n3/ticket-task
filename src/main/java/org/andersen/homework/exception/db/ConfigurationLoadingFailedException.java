package org.andersen.homework.exception.db;

public class ConfigurationLoadingFailedException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Failed to load configuration.";

  public ConfigurationLoadingFailedException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
