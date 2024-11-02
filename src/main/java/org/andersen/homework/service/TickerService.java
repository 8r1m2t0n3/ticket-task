package org.andersen.homework.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.andersen.homework.model.entity.Ticket;
import org.andersen.homework.model.enums.StadiumSector;
import org.andersen.homework.util.RandomizerUtil;
import org.andersen.homework.util.ValidationUtil;

public class TickerService {

  private final List<Ticket> ticketList = new ArrayList<>();

  public TickerService() {
    fillTicketsList();
  }

  public Optional<Ticket> findById(short id) {
    return ticketList.stream().filter(o -> o.getId().equals(id)).findFirst();
  }

  public List<Ticket> findByStadiumSector(StadiumSector stadiumSector) {
    return ticketList.stream()
        .filter(o -> o.getStadiumSector().equals(stadiumSector))
        .collect(Collectors.toList());
  }

  public List<Ticket> getAll() {
    return ticketList;
  }

  private void fillTicketsList() {
    IntStream.range(0, 10)
        .forEach(n -> {
          Ticket ticket = new Ticket(
              (short) n,
              ((float) RandomizerUtil.getRandomInt(10, 1000)) / 100,
              RandomizerUtil.getRandomString(RandomizerUtil.getRandomInt(1, 10)),
              (short) RandomizerUtil.getRandomInt(0, 999),
              RandomizerUtil.getRandomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE,
              LocalDateTime.now(),
              RandomizerUtil.getRandomFromEnum(StadiumSector.class),
              ((float) RandomizerUtil.getRandomInt(10, 1000)) / 100);
          ValidationUtil.validate(ticket);
          ticketList.add(ticket);
        });
  }
}
