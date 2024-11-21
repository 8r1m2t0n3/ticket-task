package org.andersen.homework.exception.ticket;

public class TicketSavingErrorException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Error saving ticket.";

  public TicketSavingErrorException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
