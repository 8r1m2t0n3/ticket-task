package org.andersen.homework.util.mapper.user;

import org.andersen.homework.model.dto.user.client.ClientDto;
import org.andersen.homework.model.dto.user.client.ClientIdOnlyDto;
import org.andersen.homework.model.dto.user.client.ClientSaveDto;
import org.andersen.homework.model.dto.user.client.ClientUpdateDto;
import org.andersen.homework.model.entity.user.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
  ClientDto entityToDto(Client client);
  Client dtoToEntity(ClientDto clientDto);
  Client saveDtoToEntity(ClientSaveDto clientSaveDto);
  Client updateDtoToEntity(ClientUpdateDto clientUpdateDto);
  ClientIdOnlyDto entityToIdOnlyDto(Client client);
}
