package org.andersen.homework.repository;

import java.util.List;
import java.util.UUID;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertTicketJpaRepository extends JpaRepository<ConcertTicket, UUID> {
  List<ConcertTicket> findByClientId(UUID clientId);
}
