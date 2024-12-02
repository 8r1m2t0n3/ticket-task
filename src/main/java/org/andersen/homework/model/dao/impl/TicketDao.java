package org.andersen.homework.model.dao.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dao.Dao;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TicketDao implements Dao<Ticket, UUID> {

  private final SessionFactory sessionFactory;

  @Override
  public Ticket save(Ticket ticket) {
    Transaction transaction;
    Ticket savedTicket;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      savedTicket = session.merge(ticket);
      transaction.commit();
    }
    return savedTicket;
  }

  @Override
  public void update(UUID id, Ticket ticket) {
    Transaction transaction;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();

      ticket.setId(id);
      session.merge(ticket);

      transaction.commit();
    }
  }

  @Override
  public void remove(UUID id) {
    Transaction transaction;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      session.remove(session.get(Ticket.class, id));
      transaction.commit();
    }
  }

  @Override
  public Ticket findById(UUID id) {
    Transaction transaction;
    Ticket ticket;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      ticket = session.get(Ticket.class, id);
      transaction.commit();
    }
    return ticket;
  }

  public Ticket findByUserId(UUID userId) {
    try (Session session = sessionFactory.openSession()) {
      String hql = "SELECT u.ticket FROM User u WHERE u.id = :userId";
      Query<Ticket> query = session.createQuery(hql, Ticket.class);
      query.setParameter("userId", userId);
      return query.uniqueResult();
    }
  }

  @Override
  public List<Ticket> getAll() {
    Transaction transaction;
    List<Ticket> tickets;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      tickets = session.createQuery("FROM Ticket", Ticket.class).list();
      transaction.commit();
    }
    return tickets;
  }
}
