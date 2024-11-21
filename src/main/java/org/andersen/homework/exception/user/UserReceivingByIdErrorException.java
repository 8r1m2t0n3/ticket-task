package org.andersen.homework.exception.user;

public class UserReceivingByIdErrorException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Error receiving user by ID.";

  public UserReceivingByIdErrorException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
