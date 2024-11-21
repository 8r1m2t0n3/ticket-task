package org.andersen.homework.exception.user;

import org.andersen.homework.model.enums.UserRole;

public class UnknownUserRoleException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Unknown user role: %s.";

  public UnknownUserRoleException(UserRole userRole) {
    super(ERROR_MESSAGE.formatted(userRole.name()));
  }
}
