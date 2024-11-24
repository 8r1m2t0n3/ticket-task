package org.andersen.homework.model.dao.hibernate;

import java.util.List;
import java.util.UUID;
import org.andersen.homework.model.dao.Dao;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.util.SessionFactoryManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TicketDaoHibernate implements Dao<Ticket, UUID> {

  @Override
  public Ticket save(Ticket ticket) {
    Transaction transaction;
    Ticket savedTicket;
    try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();

      savedTicket = session.merge(ticket);
      transaction.commit();
    }
    return savedTicket;
  }

  @Override
  public void update(UUID id, Ticket ticket) {

  }

  @Override
  public void delete(UUID id) {

  }

  @Override
  public Ticket get(UUID id) {
    return null;
  }

  @Override
  public List<Ticket> getAll() {
    return List.of();
  }
}
