package org.andersen.homework.exception.db;

public class FileExecutionFailedException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Failed to execute SQL file: %s.";

  public FileExecutionFailedException(String filePath, Exception e) {
    super(ERROR_MESSAGE.formatted(filePath), e);
  }
}
