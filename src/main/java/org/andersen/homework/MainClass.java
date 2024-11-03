package org.andersen.homework;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;
import org.andersen.homework.model.entity.Admin;
import org.andersen.homework.model.entity.Client;
import org.andersen.homework.model.entity.Ticket;
import org.andersen.homework.model.enums.StadiumSector;
import org.andersen.homework.service.TickerService;
import org.andersen.homework.util.RandomizerUtil;
import org.andersen.homework.util.ValidationUtil;

public class MainClass {

  private final static TickerService TICKER_SERVICE = new TickerService();

  private static void printTickets() {
    System.out.print("Found by id: ");
    IntStream.range(0, 999)
        .mapToObj(i -> TICKER_SERVICE.findById((short) i))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .findFirst()
        .ifPresent(System.out::println);

    System.out.println("Found by stadium sector: " +
        TICKER_SERVICE.findByStadiumSector((RandomizerUtil.getRandomFromEnum(StadiumSector.class)))
            .stream()
            .toList());

    Ticket limitedTicket = new Ticket((short) 123, 3.95f, "0123456789", (short) 123,
        Boolean.TRUE, 5.5526f);
    ValidationUtil.validate(limitedTicket);
    System.out.println("Limited Ticket: " + limitedTicket);

    Ticket fullTicket = new Ticket((short) 123, 3.95f, "0123456789", (short) 123,
        Boolean.TRUE, 5.5526f, LocalDateTime.now(),
        RandomizerUtil.getRandomFromEnum(StadiumSector.class));
    ValidationUtil.validate(fullTicket);
    System.out.println("Full Ticket: " + fullTicket);

    System.out.println();

    fullTicket.share("19374682");
    fullTicket.share("18364529", "email@mail.com");
  }

  private static void printUsers() {
    Client client = new Client((short) RandomizerUtil.getRandomInt(0, 999));
    System.out.println("Client id: " + client.getId());
    client.print();
    client.getTicket();

    System.out.println();

    Admin admin = new Admin((short) RandomizerUtil.getRandomInt(0, 999));
    System.out.println("Admin id: " + admin.getId());
    admin.print();
    admin.checkTicket();
  }

  public static void main(String[] args) {
    printTickets();
    System.out.println("\n******************************************\n");
    printUsers();
  }
}
