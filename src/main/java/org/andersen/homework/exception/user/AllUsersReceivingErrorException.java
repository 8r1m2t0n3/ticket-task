package org.andersen.homework.exception.user;

public class AllUsersReceivingErrorException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Error receiving all users.";

  public AllUsersReceivingErrorException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
