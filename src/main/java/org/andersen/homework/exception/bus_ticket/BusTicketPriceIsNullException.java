package org.andersen.homework.exception.bus_ticket;

public class BusTicketPriceIsNullException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Bus ticket price in usd is null.";

  public BusTicketPriceIsNullException() {
    super(ERROR_MESSAGE);
  }
}
