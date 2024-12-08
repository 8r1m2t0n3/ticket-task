package org.andersen.homework.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.andersen.homework.model.enums.BusTicketClass;
import org.andersen.homework.model.enums.BusTicketDuration;
import org.andersen.homework.model.enums.StadiumSector;

public class TicketGenerator {

  public static ConcertTicket getRandomConcertTicket() {
    return new ConcertTicket(
        BigDecimal.valueOf(((float) RandomizerUtil.getRandomInt(10, 1000)) / 100),
        RandomizerUtil.getRandomString(RandomizerUtil.getRandomInt(1, 10)),
        (short) RandomizerUtil.getRandomInt(0, 999),
        ((float) RandomizerUtil.getRandomInt(10, 1000)) / 100,
        RandomizerUtil.getRandomFromEnum(StadiumSector.class),
        LocalDateTime.now(),
        RandomizerUtil.getRandomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
  }

  public static BusTicket getRandomBusTicket() {
    BusTicket busTicket = new BusTicket();

    busTicket.setPriceInUsd(BigDecimal.valueOf(((float) RandomizerUtil.getRandomInt(10, 1000)) / 100));
    busTicket.setTicketClass(RandomizerUtil.getRandomFromEnum(BusTicketClass.class));
    busTicket.setDuration(RandomizerUtil.getRandomFromEnum(BusTicketDuration.class));

    LocalDate randomStartDate = LocalDate.of(
        RandomizerUtil.getRandomInt(2000, LocalDate.now().getYear()),
        RandomizerUtil.getRandomInt(1, 12),
        RandomizerUtil.getRandomInt(1, 28)
    );
    busTicket.setStartDate(randomStartDate);

    return busTicket;
  }
}
