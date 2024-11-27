package org.andersen.homework.exception.user;

public class UserUpdatingErrorException extends RuntimeException{
  private static final String ERROR_MESSAGE = "Error updating user.";

  public UserUpdatingErrorException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
