package org.andersen.homework.exception.ticket;

public class TicketDeletingErrorException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Error deleting ticket.";

  public TicketDeletingErrorException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
