package org.andersen.homework.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.repository.TicketJpaRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketService {

  private final TicketJpaRepository ticketJpaRepository;

  @Transactional
  public Ticket save(Ticket ticket) {
    return ticketJpaRepository.save(ticket);
  }

  @Transactional
  public void update(UUID id, Ticket ticket) {
    ticket.setId(id);
    ticketJpaRepository.save(ticket);
  }

  @Transactional
  public void delete(UUID id) {
    ticketJpaRepository.deleteById(id);
  }

  public Ticket getById(UUID id) {
    return ticketJpaRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Ticket by id: %s not found".formatted(id)));
  }

  public List<Ticket> getByClientId(UUID clientId) {
    return ticketJpaRepository.findByClientId(clientId);
  }

  public List<Ticket> getAll() {
    return ticketJpaRepository.findAll();
  }

  public List<BusTicket> loadBusTickets() throws IOException {
    Resource resource = new ClassPathResource("/static/bus_tickets.json");

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    return objectMapper.readValue(resource.getInputStream(),
        objectMapper.getTypeFactory().constructCollectionType(List.class, BusTicket.class));
  }
}
