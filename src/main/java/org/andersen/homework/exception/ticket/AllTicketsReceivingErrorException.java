package org.andersen.homework.exception.ticket;

public class AllTicketsReceivingErrorException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Error receiving all ticket.";

  public AllTicketsReceivingErrorException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
