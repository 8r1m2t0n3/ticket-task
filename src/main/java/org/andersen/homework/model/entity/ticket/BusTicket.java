package org.andersen.homework.model.entity.ticket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import org.andersen.homework.model.enums.TicketType;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BusTicket extends Ticket {

  private BusTicketClass ticketClass;

  private BusTicketDuration duration;

  private LocalDate startDate;

  @JsonCreator
  @Builder
  public BusTicket(@JsonProperty("ticketClass") BusTicketClass ticketClass,
      @JsonProperty("ticketDuration") BusTicketDuration duration,
      @JsonProperty("startDate") LocalDate startDate,
      @JsonProperty("price") Float price) {
    super(TicketType.BUS, BigDecimal.valueOf(price));
    this.ticketClass = ticketClass;
    this.duration = duration;
    this.startDate = startDate;
  }
}
