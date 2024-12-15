package org.andersen.homework.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.andersen.homework.model.dto.ticket.bus.BusTicketDto;
import org.andersen.homework.model.dto.ticket.concert.ConcertTicketDto;
import org.andersen.homework.model.dto.user.client.ClientDto;
import org.andersen.homework.model.dto.user.client.ClientUpdateDto;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
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
  void save_shouldSaveClientAndUpdateTickets_whenTicketsAreUnboundAndExisting() {
    UUID concertTicketId = UUID.randomUUID();
    UUID busTicketId = UUID.randomUUID();

    ConcertTicket concertTicket = ConcertTicket.builder().id(concertTicketId).build();
    ConcertTicketDto concertTicketDto = ConcertTicketDto.builder().id(concertTicketId).build();

    BusTicket busTicket = BusTicket.builder().id(busTicketId).build();
    BusTicketDto busTicketDto = BusTicketDto.builder().id(busTicketId).build();

    ClientUpdateDto clientUpdateDto = ClientUpdateDto.builder()
        .ticketIds(Set.of(concertTicketId, busTicketId))
        .build();

    Client client = Client.builder()
        .concertTickets(Set.of(concertTicket))
        .busTickets(Set.of(busTicket))
        .build();

    UUID savedClientId = UUID.randomUUID();
    Client savedClient = Client.builder()
        .id(savedClientId)
        .concertTickets(Set.of(concertTicket))
        .busTickets(Set.of(busTicket))
        .build();

    ClientDto savedClientDto = ClientDto.builder()
        .id(savedClientId)
        .concertTickets(Set.of(concertTicketDto))
        .busTickets(Set.of(busTicketDto))
        .build();

    Mockito.when(clientMapper.updateDtoToEntity(clientUpdateDto)).thenReturn(client);

    Mockito.when(userRepository.save(client)).thenReturn(savedClient);

    Mockito.when(clientMapper.entityToDto(savedClient)).thenReturn(savedClientDto);

    Mockito.when(ticketRepository.findById(concertTicketId)).thenReturn(Optional.of(concertTicket));
    Mockito.when(ticketRepository.findById(busTicketId)).thenReturn(Optional.of(busTicket));

    Mockito.when(ticketRepository.save(concertTicket)).thenReturn(concertTicket);
    Mockito.when(ticketRepository.save(busTicket)).thenReturn(busTicket);

    Mockito.when(concertTicketMapper.entityToDto(concertTicket)).thenReturn(concertTicketDto);
    Mockito.when(busTicketMapper.entityToDto(busTicket)).thenReturn(busTicketDto);

    ClientDto result = clientService.save(clientUpdateDto);
    Assertions.assertEquals(result, savedClientDto);

    Mockito.verify(ticketRepository).findById(concertTicketId);
    Mockito.verify(ticketRepository).findById(busTicketId);

    Mockito.verify(ticketRepository).save(Mockito.any(ConcertTicket.class));
    Mockito.verify(ticketRepository).save(Mockito.any(BusTicket.class));

    Mockito.verify(concertTicketMapper).entityToDto(concertTicket);
    Mockito.verify(busTicketMapper).entityToDto(busTicket);
  }

  @Test
  void save_shouldSaveClient_whenTicketsListIsEmpty() {
    ClientUpdateDto clientUpdateDto = new ClientUpdateDto();
    Client client = new Client();

    UUID savedClientId = UUID.randomUUID();
    Client savedClient = Client.builder().id(savedClientId).build();

    ClientDto savedClientDto = ClientDto.builder().id(savedClientId).build();

    Mockito.when(clientMapper.updateDtoToEntity(clientUpdateDto)).thenReturn(client);
    Mockito.when(userRepository.save(client)).thenReturn(savedClient);
    Mockito.when(clientMapper.entityToDto(savedClient)).thenReturn(savedClientDto);

    ClientDto result = clientService.save(clientUpdateDto);
    Assertions.assertEquals(result, savedClientDto);

    Mockito.verifyNoInteractions(ticketRepository);
    Mockito.verifyNoInteractions(concertTicketMapper);
    Mockito.verifyNoInteractions(busTicketMapper);
  }

  @Test
  void save_shouldSaveClient_whenTicketExistsAndBoundOnClient() {
    UUID concertTicketId = UUID.randomUUID();

    UUID clientId = UUID.randomUUID();
    Client client = Client.builder().id(clientId).build();
    ClientUpdateDto clientUpdateDto = ClientUpdateDto.builder().ticketIds(Set.of(concertTicketId)).build();

    ConcertTicket concertTicketWithClient = ConcertTicket.builder()
        .id(concertTicketId)
        .client(client)
        .build();

    client.setConcertTickets(Set.of(concertTicketWithClient));

    Client savedClient = Client.builder()
        .id(clientId)
        .concertTickets(Set.of(concertTicketWithClient))
        .build();

    ConcertTicketDto concertTicketWithClientDto = ConcertTicketDto.builder()
        .id(concertTicketId)
        .clientId(clientId)
        .build();

    ClientDto savedClientDto = ClientDto.builder()
        .id(clientId)
        .concertTickets(Set.of(concertTicketWithClientDto))
        .build();

    Mockito.when(clientMapper.updateDtoToEntity(clientUpdateDto)).thenReturn(client);
    Mockito.when(userRepository.save(client)).thenReturn(savedClient);
    Mockito.when(clientMapper.entityToDto(savedClient)).thenReturn(savedClientDto);
    Mockito.when(ticketRepository.findById(concertTicketId)).thenReturn(Optional.of(concertTicketWithClient));

    ClientDto result = clientService.save(clientUpdateDto);
    Assertions.assertEquals(result, savedClientDto);

    Mockito.verify(ticketRepository).findById(concertTicketId);
    Mockito.verify(ticketRepository, Mockito.never()).save(concertTicketWithClient);
    Mockito.verify(concertTicketMapper, Mockito.never()).entityToDto(concertTicketWithClient);
  }

  @Test
  void save_shouldThrowException_whenTicketExistsAndBoundOnAnotherClient() {
    UUID anotherClientId = UUID.randomUUID();
    Client anotherClient = Client.builder().id(anotherClientId).build();

    UUID concertTicketId = UUID.randomUUID();
    ConcertTicket concertTicketWithAnotherClient = ConcertTicket.builder()
        .id(concertTicketId)
        .client(anotherClient)
        .build();

    Client client = Client.builder().concertTickets(Set.of(concertTicketWithAnotherClient)).build();
    ClientUpdateDto clientUpdateDto = ClientUpdateDto.builder().ticketIds(Set.of(concertTicketId)).build();

    UUID savedClientId = UUID.randomUUID();
    Client savedClient = Client.builder()
        .id(savedClientId)
        .concertTickets(Set.of(concertTicketWithAnotherClient))
        .build();

    ConcertTicketDto concertTicketWithClientDto = ConcertTicketDto.builder()
        .id(concertTicketId)
        .clientId(anotherClientId)
        .build();

    ClientDto savedClientDto = ClientDto.builder()
        .id(savedClientId)
        .concertTickets(Set.of(concertTicketWithClientDto))
        .build();

    Mockito.when(clientMapper.updateDtoToEntity(clientUpdateDto)).thenReturn(client);
    Mockito.when(userRepository.save(client)).thenReturn(savedClient);
    Mockito.when(clientMapper.entityToDto(savedClient)).thenReturn(savedClientDto);
    Mockito.when(ticketRepository.findById(concertTicketId)).thenReturn(Optional.of(concertTicketWithAnotherClient));

    Assertions.assertThrows(RuntimeException.class, () -> clientService.save(clientUpdateDto));

    Mockito.verify(ticketRepository).findById(concertTicketId);
    Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.any(ConcertTicket.class));
  }

  @Test
  void save_shouldThrowException_whenTicketHasNonexistingId() {
    UUID nonexistingConcertTicketId = UUID.randomUUID();

    UUID clientId = UUID.randomUUID();
    Client client = Client.builder().id(clientId).build();
    ClientUpdateDto clientUpdateDto = ClientUpdateDto.builder().ticketIds(Set.of(nonexistingConcertTicketId)).build();

    ConcertTicket concertTicket = ConcertTicket.builder()
        .id(nonexistingConcertTicketId)
        .client(client)
        .build();

    client.setConcertTickets(Set.of(concertTicket));

    UUID savedClientId = UUID.randomUUID();
    Client savedClient = Client.builder()
        .id(savedClientId)
        .concertTickets(Set.of(concertTicket))
        .build();

    ConcertTicketDto concertTicketDto = ConcertTicketDto.builder()
        .id(nonexistingConcertTicketId)
        .clientId(clientId)
        .build();

    ClientDto savedClientDto = ClientDto.builder()
        .id(savedClientId)
        .concertTickets(Set.of(concertTicketDto))
        .build();

    Mockito.when(clientMapper.updateDtoToEntity(clientUpdateDto)).thenReturn(client);
    Mockito.when(userRepository.save(client)).thenReturn(savedClient);
    Mockito.when(clientMapper.entityToDto(savedClient)).thenReturn(savedClientDto);
    Mockito.when(ticketRepository.findById(nonexistingConcertTicketId)).thenReturn(Optional.empty());

    Assertions.assertThrows(RuntimeException.class, () -> clientService.save(clientUpdateDto));

    Mockito.verify(ticketRepository).findById(nonexistingConcertTicketId);
    Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.any(ConcertTicket.class));
  }

  @Test
  void update_shouldUpdateClientAndUpdateTickets_whenUserExistsByIdAndIsInstanceOfClientClassAndTicketsAreUnboundAndExisting() {
    UUID concertTicketId = UUID.randomUUID();
    ConcertTicket concertTicket = ConcertTicket.builder().id(concertTicketId).build();

    UUID busTicketId = UUID.randomUUID();
    BusTicket busTicket = BusTicket.builder().id(busTicketId).build();

    UUID userId = UUID.randomUUID();
    Client client = Client.builder().id(userId).build();
    ClientUpdateDto clientUpdateDto = ClientUpdateDto.builder()
        .id(userId)
        .ticketIds(Set.of(concertTicketId, busTicketId))
        .build();

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
    UUID nonexistingUserId = UUID.randomUUID();
    ClientUpdateDto clientUpdateDto = ClientUpdateDto.builder().id(nonexistingUserId).build();

    Mockito.when(userRepository.findById(nonexistingUserId)).thenReturn(Optional.empty());

    Assertions.assertThrows(RuntimeException.class, () -> clientService.update(clientUpdateDto));
  }

  @Test
  void update_shouldThrowException_whenUserExistsByIdAndIsNotInstanceOfClientClass() {
    UUID userId = UUID.randomUUID();
    User notClient = User.builder().id(userId).build();
    ClientUpdateDto clientUpdateDto = ClientUpdateDto.builder().id(userId).build();

    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(notClient));

    Assertions.assertThrows(RuntimeException.class, () -> clientService.update(clientUpdateDto));
  }

  @Test
  void update_shouldThrowException_whenUserExistsByIdAndIsInstanceOfClientClassAndTicketNotExists() {
    UUID nonexistingConcertTicketId = UUID.randomUUID();
    ConcertTicket nonexistingConcertTicket = ConcertTicket.builder().id(nonexistingConcertTicketId).build();

    UUID userId = UUID.randomUUID();
    Client client = Client.builder().id(userId).build();
    ClientUpdateDto clientUpdateDto = ClientUpdateDto.builder()
        .id(userId)
        .ticketIds(Set.of(nonexistingConcertTicketId))
        .build();

    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(client));
    Mockito.when(ticketRepository.findById(nonexistingConcertTicketId)).thenReturn(Optional.empty());

    Assertions.assertThrows(RuntimeException.class, () -> clientService.update(clientUpdateDto));

    Mockito.verify(ticketRepository, Mockito.never()).save(nonexistingConcertTicket);
  }

  @Test
  void update_shouldThrowException_whenUserExistsByIdAndIsInstanceOfClientClassAndTicketExistsAndBoundOnAnotherClient() {
    UUID anotherClientId = UUID.randomUUID();
    Client anotherClient = Client.builder().id(anotherClientId).build();

    UUID concertTicketId = UUID.randomUUID();
    ConcertTicket concertTicket = ConcertTicket.builder()
        .id(concertTicketId)
        .client(anotherClient)
        .build();

    UUID userId = UUID.randomUUID();
    Client client = Client.builder().id(userId).build();
    ClientUpdateDto clientUpdateDto = ClientUpdateDto.builder()
        .id(userId)
        .ticketIds(Set.of(concertTicketId))
        .build();

    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(client));
    Mockito.when(ticketRepository.findById(concertTicketId)).thenReturn(Optional.of(concertTicket));

    Assertions.assertThrows(RuntimeException.class, () -> clientService.update(clientUpdateDto));

    Mockito.verify(ticketRepository, Mockito.never()).save(concertTicket);
  }

  @Test
  void update_shouldUpdateClient_whenUserExistsByIdAndIsInstanceOfClientClassAndTicketExistsAndBoundOnClient() {
    UUID userId = UUID.randomUUID();
    Client client = Client.builder().id(userId).build();
    ClientUpdateDto clientUpdateDto = ClientUpdateDto.builder().id(userId).build();

    UUID concertTicketId = UUID.randomUUID();
    ConcertTicket concertTicket = ConcertTicket.builder()
        .id(userId)
        .client(client)
        .build();

    clientUpdateDto.setTicketIds(Set.of(concertTicketId));

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
    Client client = Client.builder().id(userId).build();

    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(client));
    Mockito.when(clientMapper.entityToDto(client)).thenReturn(Mockito.any(ClientDto.class));

    clientService.getById(userId);

    Mockito.verify(clientMapper).entityToDto(client);
  }

  @Test
  void getById_shouldThrowException_whenUserNotExistsById() {
    UUID nonexistingUserId = UUID.randomUUID();
    Client nonexistingClient = Client.builder().id(nonexistingUserId).build();

    Mockito.when(userRepository.findById(nonexistingUserId)).thenReturn(Optional.empty());

    Assertions.assertThrows(RuntimeException.class, () -> clientService.getById(nonexistingUserId));

    Mockito.verify(clientMapper, Mockito.never()).entityToDto(nonexistingClient);
  }


  @Test
  void getById_shouldThrowException_whenUserExistsByIdAndUserIsNotInstanceOfClientClass() {
    UUID notClientId = UUID.randomUUID();
    User notClient = User.builder().id(notClientId).build();

    Mockito.when(userRepository.findById(notClientId)).thenReturn(Optional.of(notClient));

    Assertions.assertThrows(RuntimeException.class, () -> clientService.getById(notClientId));

    Mockito.verifyNoInteractions(clientMapper);
  }

  @Test
  void getAll_shouldReturnListWithEntries_whenClientsExistInDB() {
    Client client = new Client();
    User notClient1 = new User();
    User notClient2 = new User();

    ClientDto clientDto = new ClientDto();

    Mockito.when(clientMapper.entityToDto(Mockito.any(Client.class))).thenReturn(clientDto);
    Mockito.when(userRepository.findAll()).thenReturn(List.of(client, notClient1, notClient2));

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
