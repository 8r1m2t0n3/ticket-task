package org.andersen.homework;

import org.andersen.homework.model.Ticket;
import org.andersen.homework.service.TickerService;
import org.andersen.homework.util.Randomizer;

public class MainClass {

  private final static TickerService TICKER_SERVICE = new TickerService();

  public static void main(String[] args) {
    TICKER_SERVICE.fillTicketsList();

    TICKER_SERVICE.findById((short) Randomizer.getRandomInt(0, 9)).ifPresent(System.out::println);
    System.out.println(
        TICKER_SERVICE.findByStadiumSector(((char) (Randomizer.getRandomInt(0, 2) + 'A'))).stream()
            .toList());

    Ticket emptyTicket = new Ticket();
    System.out.println(emptyTicket);

    Ticket limitedTicket = TICKER_SERVICE.createLimitedTicket("0123456789", (short) 123);
    System.out.println(limitedTicket);

    Ticket fullTicket = TICKER_SERVICE.createFullTicket((short) 1234, 3.95f, "0123456789",
        (short) 123,
        Boolean.TRUE, 'B', 5.5526f);
    System.out.println(fullTicket);
  }
}
