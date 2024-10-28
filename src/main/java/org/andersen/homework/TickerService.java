package org.andersen.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TickerService {

  private final static List<Ticket> TICKETS_LIST = new ArrayList<>();

  public static Ticket createLimitedTicket(String concertHallName, Short eventCode) {
    concertHallNameValidation(concertHallName);
    eventCodeValidation(eventCode);
    return new Ticket(concertHallName, eventCode);
  }

  public static Ticket createFullTicket(Short id, Float priceInUsd, String concertHallName,
      Short eventCode, Boolean isPromo, Character stadiumSector, Float backpackWeightInKg) {
    idValidation(id);
    concertHallNameValidation(concertHallName);
    eventCodeValidation(eventCode);
    stadiumSectorValidation(stadiumSector);
    return new Ticket(id, priceInUsd, concertHallName, eventCode, isPromo, stadiumSector,
        backpackWeightInKg);
  }

  private static void idValidation(Short id) {
    if (id > 9999) {
      throw new IllegalArgumentException("Id value consists of more than 4 digits.");
    }
  }

  private static void stadiumSectorValidation(Character stadiumSector) {
    if (stadiumSector < 'A' || stadiumSector > 'C') {
      throw new IllegalArgumentException("Stadium Sector value is not 'A', 'B' or 'C'.");
    }
  }

  private static void concertHallNameValidation(String concertHallName) {
    if (concertHallName.length() > 10) {
      throw new IllegalArgumentException("Concert Hall Name consists of more than 10 characters.");
    }
  }

  private static void eventCodeValidation(Short eventCode) {
    if (eventCode > 999) {
      throw new IllegalArgumentException("Event Code consists of more than 3 digits.");
    }
  }

  private static void fillTicketsList() {
    IntStream.range(0, 10)
        .forEach(
            n -> TICKETS_LIST.add(createFullTicket((short) n, 5.0f, "center", (short) 287,
                Boolean.TRUE, 'A', 10.0f)));
  }

  public static void main(String[] args) {
    fillTicketsList();

    Ticket emptyTicket = new Ticket();
    System.out.println(emptyTicket);

    Ticket limitedTicket = createLimitedTicket("0123456789", (short) 123);
    System.out.println(limitedTicket);

    Ticket fullTicket = createFullTicket((short) 1234, 3.95f, "0123456789", (short) 123,
        Boolean.TRUE, 'B', 5.5526f);
    System.out.println(fullTicket);
  }
}
