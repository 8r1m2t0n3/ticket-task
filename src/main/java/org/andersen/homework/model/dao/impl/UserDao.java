package org.andersen.homework.model.dao.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dao.Dao;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao implements Dao<User, UUID> {

  private final SessionFactory sessionFactory;

  @Override
  public User save(User user) {
    Transaction transaction;
    User savedUser;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();

      if (user instanceof Client client) {
        List<Ticket> tickets = new LinkedList<>(client.getTickets());
        client.getTickets().clear();

        savedUser = session.merge(client);

        List<Ticket> ticketsToAdd = new LinkedList<>();
        for (Ticket ticket : tickets) {
          ticket.setClient((Client) savedUser);
          if (ticket.getId() == null) {
            ticket = session.merge(ticket);
          } else {
            session.merge(ticket);
          }
          ticketsToAdd.add(ticket);
        }
        ((Client) savedUser).getTickets().addAll(ticketsToAdd);
        savedUser = session.merge(savedUser);
      } else {
        savedUser = session.merge(user);
      }

      transaction.commit();
    }
    return savedUser;
  }

  @Override
  public void update(UUID id, User user) {
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      user.setId(id);
      User existingUser = session.get(User.class, id);

      if (existingUser instanceof Client existingClient && user instanceof Client client) {
        updateClientTickets(session, existingClient, client);
      }

      session.merge(user);
      transaction.commit();
    }
  }

  private void updateClientTickets(Session session, Client existingClient, Client updatedClient) {
    Set<Ticket> savedTickets = existingClient.getTickets();
    Set<Ticket> newTickets = updatedClient.getTickets();

    Set<Ticket> ticketsToRemove = new HashSet<>(savedTickets);
    ticketsToRemove.removeAll(newTickets);
    Set<Ticket> ticketsToAdd = new HashSet<>(newTickets);
    ticketsToAdd.removeAll(savedTickets);

    ticketsToRemove.forEach(ticket -> {
      ticket.setClient(null);
      session.merge(ticket);
    });

    ticketsToAdd.forEach(ticket -> {
      ticket.setClient(updatedClient);
      session.merge(ticket);
    });
  }


  @Override
  public void remove(UUID id) {
    Transaction transaction;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Optional.ofNullable(session.get(User.class, id)).ifPresent(session::remove);
      transaction.commit();
    }
  }

  @Override
  public User findById(UUID id) {
    Transaction transaction;
    User user;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      user = session.get(User.class, id);
      transaction.commit();
    }
    return user;
  }

  @Override
  public List<User> getAll() {
    Transaction transaction;
    List<User> users;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      users = session.createQuery("FROM User", User.class).list();
      transaction.commit();
    }
    return users;
  }
}
