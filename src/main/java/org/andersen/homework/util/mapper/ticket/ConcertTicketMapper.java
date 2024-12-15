package org.andersen.homework.util.mapper.ticket;

import org.andersen.homework.model.dto.ticket.concert.ConcertTicketDto;
import org.andersen.homework.model.dto.ticket.concert.ConcertTicketUpdateDto;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConcertTicketMapper {
  ConcertTicketDto entityToDto(ConcertTicket concertTicket);
  ConcertTicket dtoToEntity(ConcertTicketDto concertTicketDto);
  ConcertTicket updateDtoToEntity(ConcertTicketUpdateDto concertTicketUpdateDto);
}
