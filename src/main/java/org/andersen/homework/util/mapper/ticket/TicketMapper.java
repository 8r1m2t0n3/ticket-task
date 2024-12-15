package org.andersen.homework.util.mapper.ticket;

import org.andersen.homework.model.dto.ticket.TicketDto;
import org.andersen.homework.model.dto.ticket.TicketUpdateDto;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ConcertTicketMapper.class, BusTicketMapper.class})
public interface TicketMapper {
  TicketDto entityToDto(Ticket ticket);
  Ticket updateDtoToEntity(TicketUpdateDto ticketUpdateDto);
}

