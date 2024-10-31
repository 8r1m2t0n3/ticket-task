package org.andersen.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class TickerService {
    private final static List<Ticket> TICKETS_LIST = new ArrayList<>();

    public static List<Ticket> findByStadiumSector(Character stadiumSector) {
        return TICKETS_LIST.stream()
                .filter(o -> o.getStadiumSector().equals(stadiumSector))
                .collect(Collectors.toList());
    }

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

  public static Optional<Ticket> findById(Short id) {
    return TICKETS_LIST.stream().filter(o -> o.getId().equals(id)).findFirst();
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
            n -> TICKETS_LIST.add(
                createFullTicket(
                    (short) n,
                    ((float) getRandomInt(10, 1000)) / 100,
                    getRandomString(getRandomInt(1, 10)),
                    (short) getRandomInt(0, 999),
                    getRandomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE,
                    (char) (getRandomInt(0, 2) + 'A'),
                    ((float) getRandomInt(10, 1000)) / 100)));
  }

  private static String getRandomString(int length) {
    StringBuilder stringBuilder = new StringBuilder();
    IntStream.range(0, length).forEach(n -> stringBuilder.append(getRandomInt(0, 9)));
    return stringBuilder.toString();
  }

  private static int getRandomInt(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }

  public static void main(String[] args) {
    fillTicketsList();

    findById((short) getRandomInt(0, 9)).ifPresent(System.out::println);

    Ticket emptyTicket = new Ticket();
    System.out.println(emptyTicket);

    Ticket limitedTicket = createLimitedTicket("0123456789", (short) 123);
    System.out.println(limitedTicket);

    Ticket fullTicket = createFullTicket((short) 1234, 3.95f, "0123456789", (short) 123,
        Boolean.TRUE, 'B', 5.5526f);
    System.out.println(fullTicket);
  }
}
