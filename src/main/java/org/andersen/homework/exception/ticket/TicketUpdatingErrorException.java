package org.andersen.homework.exception.ticket;

public class TicketUpdatingErrorException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Error updating ticket.";

  public TicketUpdatingErrorException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
