package org.andersen.homework.exception.ticket;

public class TicketAlreadyAssignedToAnotherUserException extends RuntimeException {

  private static final String ERROR_MESSAGE = "The ticket is already assigned to another user.";

  public TicketAlreadyAssignedToAnotherUserException() {
    super(ERROR_MESSAGE);
  }
}
