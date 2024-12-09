package org.andersen.homework.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.andersen.homework.model.dto.ticket.TicketIdOnlyDto;
import org.andersen.homework.model.dto.ticket.bus.BusTicketDto;
import org.andersen.homework.model.dto.ticket.concert.ConcertTicketDto;
import org.andersen.homework.model.dto.user.client.ClientDto;
import org.andersen.homework.model.dto.user.client.ClientIdOnlyDto;
import org.andersen.homework.model.dto.user.client.ClientSaveDto;
import org.andersen.homework.model.dto.user.client.ClientUpdateDto;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.andersen.homework.repository.TicketJpaRepository;
import org.andersen.homework.repository.UserJpaRepository;
import org.andersen.homework.service.user.ClientService;
import org.andersen.homework.util.mapper.ticket.BusTicketMapper;
import org.andersen.homework.util.mapper.ticket.ConcertTicketMapper;
import org.andersen.homework.util.mapper.user.ClientMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientServiceUnitTest {

  @Mock
  TicketJpaRepository ticketRepository;
  @Mock
  UserJpaRepository userRepository;
  @Mock
  ClientMapper clientMapper;
  @Mock
  ConcertTicketMapper concertTicketMapper;
  @Mock
  BusTicketMapper busTicketMapper;

  @InjectMocks
  ClientService clientService;

  @Test
  void save_shouldSaveClientAndUpdateTickets_whenClientSaveDtoContainsUnboundAndExistingTickets() {
    ClientSaveDto clientSaveDto = new ClientSaveDto();

    UUID concertTicketId = UUID.randomUUID();
    TicketIdOnlyDto concertTicketIdOnlyDto = new TicketIdOnlyDto();
    concertTicketIdOnlyDto.setId(concertTicketId);

    UUID busTicketId = UUID.randomUUID();
    TicketIdOnlyDto busTicketIdOnlyDto = new TicketIdOnlyDto();
    busTicketIdOnlyDto.setId(busTicketId);

    clientSaveDto.setTickets(Set.of(concertTicketIdOnlyDto, busTicketIdOnlyDto));


    Client client = new Client();

    ConcertTicket concertTicket = new ConcertTicket();
    concertTicket.setId(concertTicketId);

    client.setConcertTickets(Set.of(concertTicket));

    BusTicket busTicket = new BusTicket();
    busTicket.setId(busTicketId);

    client.setBusTickets(Set.of(busTicket));

    Mockito.when(clientMapper.saveDtoToEntity(clientSaveDto)).thenReturn(client);


    UUID savedClientId = UUID.randomUUID();


    Client savedClient = new Client();
    savedClient.setId(savedClientId);
    savedClient.setConcertTickets(Set.of(concertTicket));
    savedClient.setBusTickets(Set.of(busTicket));

    Mockito.when(userRepository.save(client)).thenReturn(savedClient);

    ClientDto savedClientDto = new ClientDto();
    savedClientDto.setId(savedClientId);

    Mockito.when(clientMapper.entityToDto(savedClient)).thenReturn(savedClientDto);


    Mockito.when(ticketRepository.findById(concertTicketId)).thenReturn(Optional.of(concertTicket));
    Mockito.when(ticketRepository.findById(busTicketId)).thenReturn(Optional.of(busTicket));

    Mockito.when(ticketRepository.save(concertTicket)).thenReturn(concertTicket);
    Mockito.when(ticketRepository.save(busTicket)).thenReturn(busTicket);


    ConcertTicketDto concertTicketDto = new ConcertTicketDto();
    concertTicketDto.setId(concertTicketId);
    savedClientDto.setConcertTickets(Set.of(concertTicketDto));

    BusTicketDto busTicketDto = new BusTicketDto();
    busTicketDto.setId(busTicketId);
    savedClientDto.setBusTickets(Set.of(busTicketDto));

    Mockito.when(concertTicketMapper.entityToDto(concertTicket)).thenReturn(concertTicketDto);
    Mockito.when(busTicketMapper.entityToDto(busTicket)).thenReturn(busTicketDto);


    ClientDto result = clientService.save(clientSaveDto);
    Assertions.assertEquals(result, savedClientDto);


    Mockito.verify(ticketRepository).findById(concertTicketId);
    Mockito.verify(ticketRepository).findById(busTicketId);

    Mockito.verify(ticketRepository).save(Mockito.any(ConcertTicket.class));
    Mockito.verify(ticketRepository).save(Mockito.any(BusTicket.class));

    Mockito.verify(concertTicketMapper).entityToDto(concertTicket);
    Mockito.verify(busTicketMapper).entityToDto(busTicket);
  }

  @Test
  void save_shouldSaveClient_whenClientSaveDtoContainsNoTickets() {
    ClientSaveDto clientSaveDto = new ClientSaveDto();
    Client client = new Client();

    Mockito.when(clientMapper.saveDtoToEntity(clientSaveDto)).thenReturn(client);

    UUID savedClientId = UUID.randomUUID();

    Client savedClient = new Client();
    savedClient.setId(savedClientId);

    Mockito.when(userRepository.save(client)).thenReturn(savedClient);

    ClientDto savedClientDto = new ClientDto();
    savedClientDto.setId(savedClientId);

    Mockito.when(clientMapper.entityToDto(savedClient)).thenReturn(savedClientDto);

    Mockito.verifyNoInteractions(ticketRepository);
    Mockito.verifyNoInteractions(concertTicketMapper);
    Mockito.verifyNoInteractions(busTicketMapper);

    ClientDto result = clientService.save(clientSaveDto);
    Assertions.assertEquals(result, savedClientDto);
  }

  @Test
  void save_shouldSaveClient_whenClientSaveDtoContainsExistingAndBoundOnClientTicket() {
    ClientSaveDto clientSaveDto = new ClientSaveDto();

    TicketIdOnlyDto concertTicketIdOnlyDto = new TicketIdOnlyDto();
    UUID concertTicketId = UUID.randomUUID();
    concertTicketIdOnlyDto.setId(concertTicketId);

    clientSaveDto.setTickets(Set.of(concertTicketIdOnlyDto));


    Client client = new Client();
    UUID clientId = UUID.randomUUID();
    client.setId(clientId);

    ConcertTicket concertTicketWithClient = new ConcertTicket();
    concertTicketWithClient.setId(concertTicketId);
    concertTicketWithClient.setClient(client);

    client.setConcertTickets(Set.of(concertTicketWithClient));


    Mockito.when(clientMapper.saveDtoToEntity(clientSaveDto)).thenReturn(client);


    Client savedClient = new Client();
    savedClient.setId(clientId);
    savedClient.setConcertTickets(Set.of(concertTicketWithClient));

    Mockito.when(userRepository.save(client)).thenReturn(savedClient);

    ClientDto savedClientDto = new ClientDto();
    savedClientDto.setId(clientId);

    Mockito.when(clientMapper.entityToDto(savedClient)).thenReturn(savedClientDto);


    Mockito.when(ticketRepository.findById(concertTicketId)).thenReturn(Optional.of(concertTicketWithClient));


    ConcertTicketDto concertTicketWithClientDto = new ConcertTicketDto();
    concertTicketWithClientDto.setId(concertTicketId);

    ClientIdOnlyDto clientIdOnlyDto = new ClientIdOnlyDto();
    clientIdOnlyDto.setId(clientId);

    concertTicketWithClientDto.setClient(clientIdOnlyDto);

    savedClientDto.setConcertTickets(Set.of(concertTicketWithClientDto));


    ClientDto result = clientService.save(clientSaveDto);
    Assertions.assertEquals(result, savedClientDto);


    Mockito.verify(ticketRepository).findById(concertTicketId);

    Mockito.verify(ticketRepository, Mockito.never()).save(concertTicketWithClient);

    Mockito.verify(concertTicketMapper, Mockito.never()).entityToDto(concertTicketWithClient);
  }

  @Test
  void save_shouldThrowException_whenClientSaveDtoContainsExistingAndBoundOnAnotherClientTicket() {
    ClientSaveDto clientSaveDto = new ClientSaveDto();

    UUID concertTicketId = UUID.randomUUID();
    TicketIdOnlyDto concertTicketIdOnlyDto = new TicketIdOnlyDto();
    concertTicketIdOnlyDto.setId(concertTicketId);

    clientSaveDto.setTickets(Set.of(concertTicketIdOnlyDto));


    Client client = new Client();

    ConcertTicket concertTicketWithAnotherClient = new ConcertTicket();
    concertTicketWithAnotherClient.setId(concertTicketId);

    Client anotherClient = new Client();
    UUID anotherClientId = UUID.randomUUID();
    anotherClient.setId(anotherClientId);

    concertTicketWithAnotherClient.setClient(anotherClient);

    client.setConcertTickets(Set.of(concertTicketWithAnotherClient));


    Mockito.when(clientMapper.saveDtoToEntity(clientSaveDto)).thenReturn(client);


    UUID savedClientId = UUID.randomUUID();

    Client savedClient = new Client();
    savedClient.setId(savedClientId);
    savedClient.setConcertTickets(Set.of(concertTicketWithAnotherClient));

    Mockito.when(userRepository.save(client)).thenReturn(savedClient);

    ClientDto savedClientDto = new ClientDto();
    savedClientDto.setId(savedClientId);


    Mockito.when(clientMapper.entityToDto(savedClient)).thenReturn(savedClientDto);


    Mockito.when(ticketRepository.findById(concertTicketId)).thenReturn(Optional.of(concertTicketWithAnotherClient));


    ConcertTicketDto concertTicketWithClientDto = new ConcertTicketDto();
    concertTicketWithClientDto.setId(concertTicketId);

    ClientIdOnlyDto clientIdOnlyDto = new ClientIdOnlyDto();
    clientIdOnlyDto.setId(anotherClientId);

    concertTicketWithClientDto.setClient(clientIdOnlyDto);

    savedClientDto.setConcertTickets(Set.of(concertTicketWithClientDto));


    Assertions.assertThrows(RuntimeException.class, () -> clientService.save(clientSaveDto));


    Mockito.verify(ticketRepository).findById(concertTicketId);
    Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.any(ConcertTicket.class));
  }

  @Test
  void save_shouldThrowException_whenClientSaveDtoContainsTicketWithNonexistingId() {
    ClientSaveDto clientSaveDto = new ClientSaveDto();

    UUID nonexistingConcertTicketId = UUID.randomUUID();
    TicketIdOnlyDto concertTicketIdOnlyDto = new TicketIdOnlyDto();
    concertTicketIdOnlyDto.setId(nonexistingConcertTicketId);

    clientSaveDto.setTickets(Set.of(concertTicketIdOnlyDto));


    Client client = new Client();
    UUID clientId = UUID.randomUUID();
    client.setId(clientId);

    ConcertTicket concertTicket = new ConcertTicket();
    concertTicket.setId(nonexistingConcertTicketId);
    concertTicket.setClient(client);

    client.setConcertTickets(Set.of(concertTicket));

    Mockito.when(clientMapper.saveDtoToEntity(clientSaveDto)).thenReturn(client);


    UUID savedClientId = UUID.randomUUID();


    Client savedClient = new Client();
    savedClient.setId(savedClientId);
    savedClient.setConcertTickets(Set.of(concertTicket));

    Mockito.when(userRepository.save(client)).thenReturn(savedClient);

    ClientDto savedClientDto = new ClientDto();
    savedClientDto.setId(savedClientId);

    Mockito.when(clientMapper.entityToDto(savedClient)).thenReturn(savedClientDto);


    Mockito.when(ticketRepository.findById(nonexistingConcertTicketId)).thenReturn(Optional.empty());

    ConcertTicketDto concertTicketDto = new ConcertTicketDto();
    concertTicketDto.setId(nonexistingConcertTicketId);

    ClientIdOnlyDto clientIdOnlyDto = new ClientIdOnlyDto();
    clientIdOnlyDto.setId(clientId);

    concertTicketDto.setClient(clientIdOnlyDto);
    savedClientDto.setConcertTickets(Set.of(concertTicketDto));


    Assertions.assertThrows(RuntimeException.class, () -> clientService.save(clientSaveDto));


    Mockito.verify(ticketRepository).findById(nonexistingConcertTicketId);
    Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.any(ConcertTicket.class));
  }

  @Test
  void update_shouldUpdateClientAndUpdateTickets_whenUserExistsByIdAndIsInstanceOfClientClassAndClientUpdateDtoContainsUnboundAndExistingTickets() {
    ClientUpdateDto clientUpdateDto = new ClientUpdateDto();


    UUID userId = UUID.randomUUID();

    Client client = new Client();
    client.setId(userId);


    clientUpdateDto.setId(userId);


    ConcertTicket concertTicket = new ConcertTicket();
    UUID concertTicketId = UUID.randomUUID();
    concertTicket.setId(concertTicketId);

    TicketIdOnlyDto concertTicketIdOnlyDto = new TicketIdOnlyDto();
    concertTicketIdOnlyDto.setId(concertTicketId);


    BusTicket busTicket = new BusTicket();
    UUID busTicketId = UUID.randomUUID();
    busTicket.setId(busTicketId);

    TicketIdOnlyDto busTicketIdOnlyDto = new TicketIdOnlyDto();
    busTicketIdOnlyDto.setId(busTicketId);


    clientUpdateDto.setTickets(Set.of(concertTicketIdOnlyDto, busTicketIdOnlyDto));


    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(client));

    Mockito.when(ticketRepository.findById(concertTicketId)).thenReturn(Optional.of(concertTicket));
    Mockito.when(ticketRepository.findById(busTicketId)).thenReturn(Optional.of(busTicket));

    Mockito.when(ticketRepository.save(concertTicket)).thenReturn(concertTicket);
    Mockito.when(ticketRepository.save(busTicket)).thenReturn(busTicket);


    clientService.update(clientUpdateDto);

    Mockito.verify(ticketRepository).save(concertTicket);
    Mockito.verify(ticketRepository).save(busTicket);

  }

  @Test
  void update_shouldThrowException_whenUserNotExistsById() {
    ClientUpdateDto clientUpdateDto = new ClientUpdateDto();


    UUID nonexistingUserId = UUID.randomUUID();

    Client client = new Client();
    client.setId(nonexistingUserId);

    clientUpdateDto.setId(nonexistingUserId);


    Mockito.when(userRepository.findById(nonexistingUserId)).thenReturn(Optional.empty());

    Assertions.assertThrows(RuntimeException.class, () -> clientService.update(clientUpdateDto));
  }

  @Test
  void update_shouldThrowException_whenUserExistsByIdAndIsNotInstanceOfClientClass() {
    ClientUpdateDto clientUpdateDto = new ClientUpdateDto();

    UUID userId = UUID.randomUUID();

    User notClient = new User();
    notClient.setId(userId);

    clientUpdateDto.setId(userId);


    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(notClient));

    Assertions.assertThrows(RuntimeException.class, () -> clientService.update(clientUpdateDto));
  }

  @Test
  void update_shouldThrowException_whenUserExistsByIdAndIsInstanceOfClientClassAndClientUpdateDtoContainsNonexistingTicket() {
    ClientUpdateDto clientUpdateDto = new ClientUpdateDto();


    UUID userId = UUID.randomUUID();

    Client client = new Client();
    client.setId(userId);


    clientUpdateDto.setId(userId);


    ConcertTicket nonexistingConcertTicket = new ConcertTicket();
    UUID nonexistingConcertTicketId = UUID.randomUUID();
    nonexistingConcertTicket.setId(nonexistingConcertTicketId);

    TicketIdOnlyDto concertTicketIdOnlyDto = new TicketIdOnlyDto();
    concertTicketIdOnlyDto.setId(nonexistingConcertTicketId);


    clientUpdateDto.setTickets(Set.of(concertTicketIdOnlyDto));


    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(client));

    Mockito.when(ticketRepository.findById(nonexistingConcertTicketId)).thenReturn(Optional.empty());


    Assertions.assertThrows(RuntimeException.class, () -> clientService.update(clientUpdateDto));

    Mockito.verify(ticketRepository, Mockito.never()).save(nonexistingConcertTicket);
  }

  @Test
  void update_shouldThrowException_whenUserExistsByIdAndIsInstanceOfClientClassAndClientUpdateDtoContainsExistingAndBoundOnAnotherClientTicket() {
    ClientUpdateDto clientUpdateDto = new ClientUpdateDto();


    UUID userId = UUID.randomUUID();

    Client client = new Client();
    client.setId(userId);


    clientUpdateDto.setId(userId);


    ConcertTicket concertTicket = new ConcertTicket();
    UUID concertTicketId = UUID.randomUUID();

    Client anotherClient = new Client();
    UUID anotherClientId = UUID.randomUUID();
    anotherClient.setId(anotherClientId);

    concertTicket.setId(concertTicketId);
    concertTicket.setClient(anotherClient);

    TicketIdOnlyDto concertTicketIdOnlyDto = new TicketIdOnlyDto();
    concertTicketIdOnlyDto.setId(concertTicketId);


    clientUpdateDto.setTickets(Set.of(concertTicketIdOnlyDto));


    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(client));

    Mockito.when(ticketRepository.findById(concertTicketId)).thenReturn(Optional.of(concertTicket));


    Assertions.assertThrows(RuntimeException.class, () -> clientService.update(clientUpdateDto));

    Mockito.verify(ticketRepository, Mockito.never()).save(concertTicket);
  }

  @Test
  void update_shouldUpdateClient_whenUserExistsByIdAndIsInstanceOfClientClassAndClientUpdateDtoContainsExistingAndBoundOnClientTicket() {
    ClientUpdateDto clientUpdateDto = new ClientUpdateDto();


    UUID userId = UUID.randomUUID();

    Client client = new Client();
    client.setId(userId);


    clientUpdateDto.setId(userId);


    ConcertTicket concertTicket = new ConcertTicket();
    UUID concertTicketId = UUID.randomUUID();

    concertTicket.setId(concertTicketId);
    concertTicket.setClient(client);

    TicketIdOnlyDto concertTicketIdOnlyDto = new TicketIdOnlyDto();
    concertTicketIdOnlyDto.setId(concertTicketId);


    clientUpdateDto.setTickets(Set.of(concertTicketIdOnlyDto));


    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(client));

    Mockito.when(ticketRepository.findById(concertTicketId)).thenReturn(Optional.of(concertTicket));


    clientService.update(clientUpdateDto);

    Mockito.verify(ticketRepository, Mockito.never()).save(concertTicket);
  }

  @Test
  void deleteTest() {
    UUID userId = UUID.randomUUID();

    Mockito.doNothing().when(userRepository).deleteById(userId);

    clientService.deleteById(userId);
  }

  @Test
  void getById_shouldReturnClient_whenUserExistsByIdAndUserIsInstanceOfClientClass() {
    UUID userId = UUID.randomUUID();
    Client client = new Client();
    client.setId(userId);

    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(client));

    Mockito.when(clientMapper.entityToDto(client)).thenReturn(Mockito.any(ClientDto.class));

    clientService.getById(userId);

    Mockito.verify(clientMapper).entityToDto(client);
  }

  @Test
  void getById_shouldThrowException_whenUserNotExistsById() {
    UUID nonexistingUserId = UUID.randomUUID();
    Client nonexistingClient = new Client();
    nonexistingClient.setId(nonexistingUserId);

    Mockito.when(userRepository.findById(nonexistingUserId)).thenReturn(Optional.empty());

    Assertions.assertThrows(RuntimeException.class, () -> clientService.getById(nonexistingUserId));

    Mockito.verify(clientMapper, Mockito.never()).entityToDto(nonexistingClient);
  }


  @Test
  void getById_shouldThrowException_whenUserExistsByIdAndUserIsNotInstanceOfClientClass() {
    UUID notClientId = UUID.randomUUID();
    User notClient = new User();
    notClient.setId(notClientId);

    Mockito.when(userRepository.findById(notClientId)).thenReturn(Optional.of(notClient));

    Assertions.assertThrows(RuntimeException.class, () -> clientService.getById(notClientId));

    Mockito.verifyNoInteractions(clientMapper);
  }

  @Test
  void getAll_shouldReturnListWithEntries_whenClientsExistInDB() {
    Client client = new Client();
    User notClient1 = new User();
    User notClient2 = new User();

    Mockito.when(userRepository.findAll()).thenReturn(List.of(client, notClient1, notClient2));

    ClientDto clientDto = new ClientDto();
    Mockito.when(clientMapper.entityToDto(Mockito.any(Client.class))).thenReturn(clientDto);

    List<ClientDto> result = clientService.getAll();

    Assertions.assertEquals(1, result.size());
  }

  @Test
  void getAll_shouldReturnEmptyList_whenNoClientsExistInDB() {
    User notClient1 = new User();
    User notClient2 = new User();

    Mockito.when(userRepository.findAll()).thenReturn(List.of(notClient1, notClient2));

    List<ClientDto> result = clientService.getAll();

    Assertions.assertEquals(0, result.size());
  }
}
