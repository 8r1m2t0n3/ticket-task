package org.andersen.homework.exception.user;

public class UserSavingErrorException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Error saving user.";

  public UserSavingErrorException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
