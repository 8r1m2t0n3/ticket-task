package org.andersen.homework.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.andersen.homework.model.dao.impl.TicketDao;
import org.andersen.homework.model.entity.ticket.Ticket;

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
}
