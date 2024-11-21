package org.andersen.homework.exception.user;

public class TicketAssignmentToUserErrorException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Error checking ticket assignment.";

  public TicketAssignmentToUserErrorException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
