package org.andersen.homework.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dao.impl.TicketHibernateDao;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketService {

  private final TicketHibernateDao ticketDao;

  @Transactional
  public Ticket save(Ticket ticket) {
    return ticketDao.save(ticket);
  }

  @Transactional
  public void update(UUID id, Ticket ticket) {
    ticket.setId(id);
    ticketDao.update(ticket);
  }

  @Transactional
  public void deleteById(UUID id) {
    ticketDao.deleteById(id);
  }

  @Transactional
  public void delete(Ticket ticket) {
    ticketDao.delete(ticket);
  }

  public Optional<Ticket> getById(UUID id) {
    return Optional.ofNullable(ticketDao.findById(id));
  }

  public List<Ticket> getByClientId(UUID clientId) {
    return ticketDao.findByClientId(clientId);
  }

  public List<Ticket> getAll() {
    return ticketDao.getAll();
  }

  public List<BusTicket> loadBusTickets() throws IOException {
    Resource resource = new ClassPathResource("/static/bus_tickets.json");

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    List<BusTicket> tickets = objectMapper.readValue(resource.getInputStream(),
        objectMapper.getTypeFactory().constructCollectionType(List.class, BusTicket.class));

    return tickets;
  }
}
