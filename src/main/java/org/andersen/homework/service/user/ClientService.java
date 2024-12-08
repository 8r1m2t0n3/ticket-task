package org.andersen.homework.service.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.ticket.TicketIdOnlyDto;
import org.andersen.homework.model.dto.user.client.ClientDto;
import org.andersen.homework.model.dto.user.client.ClientSaveDto;
import org.andersen.homework.model.dto.user.client.ClientUpdateDto;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.andersen.homework.repository.TicketJpaRepository;
import org.andersen.homework.repository.UserJpaRepository;
import org.andersen.homework.util.mapper.ticket.BusTicketMapper;
import org.andersen.homework.util.mapper.ticket.ConcertTicketMapper;
import org.andersen.homework.util.mapper.user.ClientMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

  private final TicketJpaRepository ticketRepository;
  private final UserJpaRepository userRepository;
  private final ClientMapper clientMapper;
  private final ConcertTicketMapper concertTicketMapper;
  private final BusTicketMapper busTicketMapper;

  @Transactional
  public ClientDto save(ClientSaveDto clientSaveDto) {
    Client savedClient = userRepository.save(clientMapper.saveDtoToEntity(clientSaveDto));

    return addTicketsToClientDto(
        clientMapper.entityToDto(savedClient),
        updateClientTickets(savedClient, clientSaveDto.getTickets()));
  }

  @Transactional
  public void update(ClientUpdateDto clientUpdateDto) {
    updateClientTickets(getClientById(clientUpdateDto.getId()), clientUpdateDto.getTickets());
  }

  @Transactional
  public void deleteById(UUID id) {
    userRepository.deleteById(id);
  }

  public ClientDto getById(UUID id) {
    return clientMapper.entityToDto(getClientById(id));
  }

  public List<ClientDto> getAll() {
    return userRepository.findAll().stream()
        .filter(Client.class::isInstance)
        .map(Client.class::cast)
        .map(clientMapper::entityToDto)
        .toList();
  }

  private ClientDto addTicketsToClientDto(ClientDto clientDto, Set<Ticket> tickets) {
    for (Ticket ticket : tickets) {
      if (ticket instanceof ConcertTicket concertTicket) {
        clientDto.getConcertTickets().add(concertTicketMapper.entityToDto(concertTicket));
      } else if (ticket instanceof BusTicket busTicket) {
        clientDto.getBusTickets().add(busTicketMapper.entityToDto(busTicket));
      }
    }
    return clientDto;
  }

  private Set<Ticket> updateClientTickets(Client client, Set<TicketIdOnlyDto> ticketIds) {
    Set<Ticket> clientUpdatedTickets = new HashSet<>();

    if (ticketIds != null) {
      for (TicketIdOnlyDto ticketIdOnlyDto : ticketIds) {

        Ticket ticket = ticketRepository.findById(ticketIdOnlyDto.getId())
            .orElseThrow(() -> new RuntimeException("Ticket with id: %s not exist".formatted(ticketIdOnlyDto.getId())));

        if (ticket.getClient() != null && ticket.getClient().getId() != client.getId()) {
          throw new RuntimeException("Ticket with id: %s already bound with another client".formatted(ticket.getId()));
        } else {
          ticket.setClient(client);
          ticketRepository.save(ticket);
          clientUpdatedTickets.add(ticket);
        }
      }
    }
    return clientUpdatedTickets;
  }

  private Client getClientById(UUID id) {
    return userRepository.findById(id)
        .filter(Client.class::isInstance)
        .map(Client.class::cast)
        .orElseThrow(() -> new RuntimeException("Client by id: %s not found".formatted(id)));
  }
}
