package org.andersen.homework.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dao.impl.TicketHibernateDao;
import org.andersen.homework.model.dao.impl.UserHibernateDao;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserHibernateDao userDao;
  private final TicketHibernateDao ticketDao;

  @Value("${client.ticket.update.enabled}")
  private Boolean clientTicketUpdateEnabled;

  @Transactional
  public User save(User user) {
    return userDao.save(user);
  }

  @Transactional
  public Client saveClientWithTickets(Client client, List<Ticket> tickets) {
    if (!clientTicketUpdateEnabled) {
      throw new RuntimeException("Unable to save client with tickets");
    }
    Client savedClient = (Client) userDao.save(client);
    addTicketsToClient(savedClient, tickets);
    return (Client) getById(savedClient.getId()).get();
  }

  @Transactional
  public void addTicketsToClientById(UUID clientId, List<Ticket> tickets) {
    if (!clientTicketUpdateEnabled) {
      throw new RuntimeException("Unable to save client with tickets");
    }
    Optional.ofNullable(userDao.findById(clientId)).ifPresent(potentialClient -> {
      if (potentialClient instanceof Client client) {
        addTicketsToClient(client, tickets);
      }
    });
  }

  @Transactional
  public void update(UUID id, User user) {
    user.setId(id);
    userDao.update(user);
  }

  @Transactional
  public void deleteById(UUID id) {
    userDao.deleteById(id);
  }

  @Transactional
  public void delete(User user) {
    if (user instanceof Client client) {
      client.getTickets().clear();
    }
    userDao.delete(user);
  }

  public Optional<User> getById(UUID id) {
    return Optional.ofNullable(userDao.findById(id));
  }

  public List<User> getAll() {
    return userDao.getAll();
  }

  private void addTicketsToClient(Client client, List<Ticket> tickets) {
    if (client.getId() != null) {
      tickets.forEach(t -> {
        if (t.getId() != null) {
          t.setClient(client);
          ticketDao.update(t);
        }
      });
    }
  }
}
