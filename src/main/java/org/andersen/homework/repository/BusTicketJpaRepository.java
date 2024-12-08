package org.andersen.homework.repository;

import java.util.List;
import java.util.UUID;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusTicketJpaRepository extends JpaRepository<BusTicket, UUID> {
  List<BusTicket> findByClientId(UUID clientId);
}
