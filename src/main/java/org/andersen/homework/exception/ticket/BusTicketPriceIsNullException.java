package org.andersen.homework.exception.ticket;

public class BusTicketPriceIsNullException extends Exception {

  private static final String ERROR_MESSAGE = "Bus ticket price in usd is null.";

  public BusTicketPriceIsNullException() {
    super(ERROR_MESSAGE);
  }
}
