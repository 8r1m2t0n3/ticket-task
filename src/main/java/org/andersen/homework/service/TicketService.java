package org.andersen.homework.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.andersen.homework.model.dao.impl.TicketDao;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.enums.BusTicketClass;
import org.andersen.homework.model.enums.BusTicketDuration;
import org.andersen.homework.model.enums.StadiumSector;
import org.andersen.homework.util.RandomizerUtil;

public class TicketService {

  private final TicketDao ticketDao;

  public TicketService() {
    this.ticketDao = new TicketDao();
  }

  public Ticket save(Ticket ticket) {
    return ticketDao.save(ticket);
  }

  public void update(UUID id, Ticket ticket) {
    ticketDao.update(id, ticket);
  }

  public void remove(UUID id) {
    ticketDao.remove(id);
  }

  public Optional<Ticket> getById(UUID id) {
    return Optional.ofNullable(ticketDao.findById(id));
  }

  public Optional<Ticket> getByUserId(UUID userId) {
    return Optional.ofNullable(ticketDao.findByUserId(userId));
  }

  public List<Ticket> getAll() {
    return ticketDao.getAll();
  }

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

    busTicket.setPriceInUsd(BigDecimal.valueOf(RandomizerUtil.getRandomInt(10, 5000)));
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
