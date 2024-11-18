package org.andersen.homework.exception.bus_ticket;

public class BusTicketStartDateIsInFutureException extends Exception {

  private static final String ERROR_MESSAGE = "Bus ticket start date is in a future.";

  public BusTicketStartDateIsInFutureException() {
    super(ERROR_MESSAGE);
  }
}
