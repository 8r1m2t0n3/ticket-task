package org.andersen.homework.model.dto.user.client;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.dto.ticket.bus.BusTicketDto;
import org.andersen.homework.model.dto.ticket.concert.ConcertTicketDto;
import org.andersen.homework.model.dto.user.UserDto;

@Getter
@Setter
@ToString(callSuper = true)
public class ClientDto extends UserDto {
  Set<ConcertTicketDto> concertTickets;
  Set<BusTicketDto> busTickets;
}
