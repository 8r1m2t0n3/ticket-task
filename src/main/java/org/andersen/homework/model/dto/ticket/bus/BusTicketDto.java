package org.andersen.homework.model.dto.ticket.bus;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.dto.ticket.TicketDto;
import org.andersen.homework.model.enums.BusTicketClass;
import org.andersen.homework.model.enums.BusTicketDuration;

@Getter
@Setter
@ToString(callSuper = true)
public class BusTicketDto extends TicketDto {
  private BusTicketClass ticketClass;
  private BusTicketDuration duration;
  private LocalDate startDate;
}
