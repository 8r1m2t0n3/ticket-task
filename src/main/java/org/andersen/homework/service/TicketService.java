package org.andersen.homework.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dao.jdbc.TicketDaoJdbc;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketService {

  private final TicketDaoJdbc ticketDao;

  @Transactional
  public Ticket save(Ticket ticket) {
    return ticketDao.save(ticket);
  }

  @Transactional
  public void update(UUID id, Ticket ticket) {
    ticketDao.update(id, ticket);
  }

  @Transactional
  public void delete(UUID id) {
    ticketDao.delete(id);
  }

  public Ticket getById(UUID id) {
    return ticketDao.getById(id);
  }

  public List<Ticket> getAll() {
    return ticketDao.getAll();
  }
}
