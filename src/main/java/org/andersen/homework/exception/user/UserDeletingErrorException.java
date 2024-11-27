package org.andersen.homework.exception.user;

public class UserDeletingErrorException extends RuntimeException{

  private static final String ERROR_MESSAGE = "Error deleting user.";

  public UserDeletingErrorException(Exception e) {
    super(ERROR_MESSAGE, e);
  }}
