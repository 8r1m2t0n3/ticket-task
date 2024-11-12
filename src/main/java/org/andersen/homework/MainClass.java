package org.andersen.homework;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.andersen.homework.exception.bus_ticket.BusTicketPriceIsNullException;
import org.andersen.homework.exception.bus_ticket.BusTicketPriceNotEvenException;
import org.andersen.homework.exception.bus_ticket.BusTicketStartDateIsInFutureException;
import org.andersen.homework.exception.bus_ticket.BusTicketStartDateIsNullWhenTicketTypeIsDayWeekOrYearException;
import org.andersen.homework.exception.bus_ticket.UndefinedBusTicketTypeException;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.util.BusTicketFileReader;
import org.andersen.homework.util.ValidationManager;

public class MainClass {

  private final static ValidationManager VALIDATION_MANAGER = new ValidationManager();
  private final static String BUS_TICKETS_FILE = "bus_tickets.json";

  private static Path getPathToResource(String resourceFileName) throws URISyntaxException {
    ClassLoader classLoader = MainClass.class.getClassLoader();
    return Paths.get(Objects.requireNonNull(classLoader.getResource(resourceFileName)).toURI());
  }

  private static List<BusTicket> getBusTicketsListFromFile(String file) {
    Path filePath;
    try {
      filePath = getPathToResource(file);
    } catch (URISyntaxException e) {
      System.out.println(e.getMessage());
      return List.of();
    }
    return BusTicketFileReader.readTicketsFromFile(
        filePath.toAbsolutePath().toString());
  }

  public static void main(String[] args) {
    List<BusTicket> busTickets = getBusTicketsListFromFile(BUS_TICKETS_FILE);

    AtomicInteger startDateViolationsCount = new AtomicInteger();
    AtomicInteger priceViolationsCount = new AtomicInteger();
    AtomicInteger ticketTypeViolationsCount = new AtomicInteger();

    AtomicInteger invalidTicketsCounter = new AtomicInteger();

    busTickets.forEach(o -> {
      try {
        VALIDATION_MANAGER.validateBusTicket(o);
      } catch (UndefinedBusTicketTypeException e) {
        ticketTypeViolationsCount.getAndIncrement();
        invalidTicketsCounter.getAndIncrement();
      } catch (BusTicketStartDateIsInFutureException |
               BusTicketStartDateIsNullWhenTicketTypeIsDayWeekOrYearException e) {
        startDateViolationsCount.getAndIncrement();
        invalidTicketsCounter.getAndIncrement();
      } catch (BusTicketPriceIsNullException | BusTicketPriceNotEvenException e) {
        priceViolationsCount.getAndIncrement();
        invalidTicketsCounter.getAndIncrement();
      }
    });

    System.out.println("Total = " + busTickets.size());
    System.out.println("Valid = " + (busTickets.size() - invalidTicketsCounter.intValue()));

    int max = Math.max(
        Math.max(startDateViolationsCount.intValue(), priceViolationsCount.intValue()),
        ticketTypeViolationsCount.intValue());

    if (max == startDateViolationsCount.intValue()) {
      System.out.println("Most popular violation = 'start date'");
    } else if (max == priceViolationsCount.intValue()) {
      System.out.println("Most popular violation = 'price'");
    } else {
      System.out.println("Most popular violation = 'ticket type");
    }
  }
}
