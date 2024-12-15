package org.andersen.homework.util.mapper.ticket;

import org.andersen.homework.model.dto.ticket.bus.BusTicketDto;
import org.andersen.homework.model.dto.ticket.bus.BusTicketUpdateDto;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusTicketMapper {
  BusTicketDto entityToDto(BusTicket busTicket);
  BusTicket dtoToEntity(BusTicketDto busTicketDto);
  BusTicket updateDtoToEntity(BusTicketUpdateDto busTicketUpdateDto);
}
