package org.andersen.homework.model.entity.ticket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Builder;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusTicket extends Ticket {

  @Column(name = "ticket_class")
  private BusTicketClass ticketClass;

  @Column(name = "duration")
  private BusTicketDuration duration;

  @Column(name = "start_date")
  private LocalDate startDate;

  @JsonCreator
  @Builder
  public BusTicket(@JsonProperty("ticketClass") BusTicketClass ticketClass,
      @JsonProperty("ticketDuration") BusTicketDuration duration,
      @JsonProperty("startDate") LocalDate startDate,
      @JsonProperty("price") BigDecimal price) {
    super(price != null ? price : BigDecimal.ZERO);
    this.ticketClass = ticketClass;
    this.duration = duration;
    this.startDate = startDate;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    BusTicket busTicket = (BusTicket) o;
    return ticketClass == busTicket.ticketClass &&
        duration == busTicket.duration &&
        Objects.equals(startDate, busTicket.startDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), ticketClass, duration, startDate);
  }
}
