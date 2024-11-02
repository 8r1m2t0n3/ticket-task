package org.andersen.homework;

import java.time.LocalDateTime;
import org.andersen.homework.model.entity.Ticket;
import org.andersen.homework.model.enums.StadiumSector;
import org.andersen.homework.service.TickerService;
import org.andersen.homework.util.RandomizerUtil;
import org.andersen.homework.util.ValidationUtil;

public class MainClass {

  private final static TickerService TICKER_SERVICE = new TickerService();

  public static void main(String[] args) {
    System.out.print("Found by id: ");
    TICKER_SERVICE.findById((short) RandomizerUtil.getRandomInt(0, 9))
        .ifPresent(System.out::println);

    System.out.println("Found by stadium sector: " +
        TICKER_SERVICE.findByStadiumSector((RandomizerUtil.getRandomFromEnum(StadiumSector.class)))
            .stream()
            .toList());

    Ticket emptyTicket = new Ticket();
    System.out.println("Empty Ticket: " + emptyTicket);

    Ticket limitedTicket = Ticket.builder()
        .concertHallName(RandomizerUtil.getRandomString(RandomizerUtil.getRandomInt(0, 10)))
        .eventCode((short) RandomizerUtil.getRandomInt(0, 999))
        .build();
    ValidationUtil.validate(limitedTicket);
    System.out.println("Limited Ticket: " + limitedTicket);

    Ticket fullTicket = new Ticket((short) 123, 3.95f, "0123456789", (short) 123,
        Boolean.TRUE, LocalDateTime.now(), RandomizerUtil.getRandomFromEnum(StadiumSector.class),
        5.5526f);
    ValidationUtil.validate(fullTicket);
    System.out.println("Full Ticket: " + fullTicket);
  }
}
