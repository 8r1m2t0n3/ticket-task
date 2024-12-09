package org.andersen.homework.model.dao.impl;

import java.util.List;
import java.util.UUID;
import org.andersen.homework.model.dao.AbstractHibernateDao;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TicketHibernateDao extends AbstractHibernateDao<Ticket, UUID> {

  public TicketHibernateDao() {
    setClazz(Ticket.class);
  }

  public List<Ticket> findByClientId(UUID clientId) {
    String hql = "SELECT t FROM Ticket t WHERE t.client.id = :clientId";
    Query<Ticket> query = getCurrentSession().createQuery(hql, Ticket.class);
    query.setParameter("clientId", clientId);
    return query.getResultList();
  }
}
