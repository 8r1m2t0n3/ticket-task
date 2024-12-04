package org.andersen.homework.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.andersen.homework.repository.TicketJpaRepository;
import org.andersen.homework.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserJpaRepository userRepository;
  private final TicketJpaRepository ticketRepository;

  @Value("${client.ticket.update.enabled}")
  private Boolean clientTicketUpdateEnabled;

  @Transactional
  public User save(User user) {
    return userRepository.save(user);
  }

  @Transactional
  public Client saveClientWithTickets(Client client, List<Ticket> tickets) {
    if (!clientTicketUpdateEnabled) {
      throw new RuntimeException("Unable to save client with tickets");
    }
    Client savedClient = userRepository.save(client);
    addTicketsToClient(savedClient, tickets);
    return (Client) getById(savedClient.getId());
  }

  @Transactional
  public void addTicketsToClientById(UUID clientId, List<Ticket> tickets) {
    if (!clientTicketUpdateEnabled) {
      throw new RuntimeException("Unable to save client with tickets");
    }
    userRepository.findById(clientId).ifPresent(potentialClient -> {
      if (potentialClient instanceof Client client) {
        addTicketsToClient(client, tickets);
      }
    });
  }

  @Transactional
  public void update(UUID id, User user) {
    user.setId(id);
    userRepository.save(user);
  }

  @Transactional
  public void deleteById(UUID id) {
    userRepository.deleteById(id);
  }

  public User getById(UUID id) {
    return userRepository.findById(id).orElseThrow(() ->
        new RuntimeException("User by id: %s not found".formatted(id)));
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  private void addTicketsToClient(Client client, List<Ticket> tickets) {
    if (client.getId() != null) {
      tickets.forEach(t -> {
        if (t.getId() != null) {
          t.setClient(client);
          ticketRepository.save(t);
        }
      });
    }
  }
}
