package org.andersen.homework.model.entity.ticket;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.enums.BusTicketClass;
import org.andersen.homework.model.enums.BusTicketDuration;

@Entity
@DiscriminatorValue("BUS")
@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BusTicket extends Ticket {

  @Column(name = "ticket_class")
  private BusTicketClass ticketClass;

  @Column(name = "duration")
  private BusTicketDuration duration;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Builder
  public BusTicket(BusTicketClass ticketClass,
                   BusTicketDuration duration,
                   LocalDate startDate,
                   BigDecimal price) {
    super(price != null ? price : BigDecimal.ZERO);
    this.ticketClass = ticketClass;
    this.duration = duration;
    this.startDate = startDate;
  }
}

