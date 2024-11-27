package org.andersen.homework.exception.ticket;

public class UndefinedBusTicketTypeException extends Exception {

  private static final String ERROR_MESSAGE = "Undefined bus ticket type.";

  public UndefinedBusTicketTypeException() {
    super(ERROR_MESSAGE);
  }
}
