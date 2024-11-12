package org.andersen.homework.exception.bus_ticket;

public class UndefinedBusTicketTypeException extends Exception {

  private static final String ERROR_MESSAGE = "Undefined bus ticket type.";

  public UndefinedBusTicketTypeException() {
    super(ERROR_MESSAGE);
  }
}
