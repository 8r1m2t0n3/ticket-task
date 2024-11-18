package org.andersen.homework.model.entity.ticket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.enums.BusTicketClass;

@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BusTicket extends Ticket {

  private BusTicketClass ticketClass;

  private String type;

  private LocalDate startDate;

  @JsonCreator
  @Builder
  public BusTicket(@JsonProperty("ticketClass") BusTicketClass ticketClass,
      @JsonProperty("ticketType") String type,
      @JsonProperty("startDate") LocalDate startDate,
      @JsonProperty("price") Float price) {
    super(price);
    this.ticketClass = ticketClass;
    this.type = type;
    this.startDate = startDate;
  }
}
