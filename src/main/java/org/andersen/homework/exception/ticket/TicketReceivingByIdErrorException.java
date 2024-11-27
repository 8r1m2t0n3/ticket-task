package org.andersen.homework.exception.ticket;

public class TicketReceivingByIdErrorException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Error receiving ticket by ID.";

  public TicketReceivingByIdErrorException(Exception e) {
    super(ERROR_MESSAGE, e);
  }
}
