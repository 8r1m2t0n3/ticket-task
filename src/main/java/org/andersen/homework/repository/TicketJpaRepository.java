package org.andersen.homework.repository;

import java.util.List;
import java.util.UUID;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketJpaRepository extends JpaRepository<Ticket, UUID> {

  List<Ticket> findByClientId(UUID clientId);
}
