package org.andersen.homework.exception.db;

public class ClosingConnectionFailedException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Failed to close connection.";

  public ClosingConnectionFailedException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
