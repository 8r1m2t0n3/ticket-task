package org.andersen.homework.model.dto.ticket.bus;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.andersen.homework.model.dto.ticket.TicketUpdateDto;
import org.andersen.homework.model.enums.BusTicketClass;
import org.andersen.homework.model.enums.BusTicketDuration;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class BusTicketUpdateDto extends TicketUpdateDto {
  private BusTicketClass ticketClass;
  private BusTicketDuration duration;
  private LocalDate startDate;
}
