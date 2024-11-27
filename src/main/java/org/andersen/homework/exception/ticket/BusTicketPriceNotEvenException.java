package org.andersen.homework.exception.ticket;

public class BusTicketPriceNotEvenException extends Exception {

  private static final String ERROR_MESSAGE = "Bus ticket price is not even.";

  public BusTicketPriceNotEvenException() {
    super(ERROR_MESSAGE);
  }
}
