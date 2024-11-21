package org.andersen.homework.exception.db;

public class ConnectionOpeningFailedException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Failed to open connection.";

  public ConnectionOpeningFailedException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
