package org.andersen.homework.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.andersen.homework.model.enums.StadiumSector;
import org.andersen.homework.util.RandomizerUtil;
import org.andersen.homework.util.ValidationManager;

public class ConcertTicketService {

  private static final ValidationManager VALIDATION_MANAGER = new ValidationManager();
  private static final List<ConcertTicket> TICKET_LIST = new ArrayList<>();

  public ConcertTicketService() {
    if (TICKET_LIST.isEmpty()) {
      IntStream.range(0, 10)
          .forEach(n -> {
            ConcertTicket ticket = getRandomConcertTicket();
            VALIDATION_MANAGER.validate(ticket);
            TICKET_LIST.add(ticket);
          });
    }
  }

  public Optional<ConcertTicket> findById(UUID id) {
    return TICKET_LIST.stream().filter(o -> o.getId().equals(id)).findFirst();
  }

  public List<ConcertTicket> findByStadiumSector(StadiumSector stadiumSector) {
    return TICKET_LIST.stream()
        .filter(o -> o.getStadiumSector().equals(stadiumSector))
        .collect(Collectors.toList());
  }

  public static ConcertTicket getRandomConcertTicket() {
    ConcertTicket ticket = new ConcertTicket(
        BigDecimal.valueOf(((float) RandomizerUtil.getRandomInt(10, 1000)) / 100),
        RandomizerUtil.getRandomString(RandomizerUtil.getRandomInt(1, 10)),
        (short) RandomizerUtil.getRandomInt(0, 999),
        ((float) RandomizerUtil.getRandomInt(10, 1000)) / 100,
        RandomizerUtil.getRandomFromEnum(StadiumSector.class),
        LocalDateTime.now(),
        RandomizerUtil.getRandomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
    VALIDATION_MANAGER.validate(ticket);
    return ticket;
  }
}
