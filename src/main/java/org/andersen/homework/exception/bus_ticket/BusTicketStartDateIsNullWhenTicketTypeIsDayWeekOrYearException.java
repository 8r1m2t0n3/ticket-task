package org.andersen.homework.exception.bus_ticket;

public class BusTicketStartDateIsNullWhenTicketTypeIsDayWeekOrYearException extends
    RuntimeException {

  private static final String ERROR_MESSAGE =
      "Bus ticket start date is null when ticket type is 'DAY', 'WEEK', or 'YEAR'";

  public BusTicketStartDateIsNullWhenTicketTypeIsDayWeekOrYearException() {
    super(ERROR_MESSAGE);
  }
}
