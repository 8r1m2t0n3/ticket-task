package org.andersen.homework.service.ticket;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.ticket.concert.ConcertTicketDto;
import org.andersen.homework.model.dto.ticket.concert.ConcertTicketSaveDto;
import org.andersen.homework.model.dto.ticket.concert.ConcertTicketUpdateDto;
import org.andersen.homework.repository.ConcertTicketJpaRepository;
import org.andersen.homework.util.mapper.ticket.ConcertTicketMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertTicketService {

  private final ConcertTicketJpaRepository concertTicketRepository;
  private final ConcertTicketMapper concertTicketMapper;

  @Transactional
  public ConcertTicketDto save(ConcertTicketSaveDto ticketSaveDto) {
    return concertTicketMapper.entityToDto(concertTicketRepository.save(
        concertTicketMapper.saveDtoToEntity(ticketSaveDto)));
  }

  @Transactional
  public void delete(UUID id) {
    concertTicketRepository.findById(id).ifPresent(ticket -> concertTicketRepository.deleteById(id));
  }

  @Transactional
  public void update(ConcertTicketUpdateDto ticketUpdateDto) {
    concertTicketRepository.save(concertTicketMapper.updateDtoToEntity(ticketUpdateDto));
  }

  public ConcertTicketDto getById(UUID id) {
    return concertTicketRepository.findById(id)
        .map(concertTicketMapper::entityToDto)
        .orElseThrow(() -> new RuntimeException("Concert ticket by id: %s not found".formatted(id)));
  }

  public List<ConcertTicketDto> getByClientId(UUID clientId) {
    return concertTicketRepository.findByClientId(clientId).stream()
        .map(concertTicketMapper::entityToDto)
        .toList();
  }

  public List<ConcertTicketDto> getAll() {
    return concertTicketRepository.findAll().stream()
        .map(concertTicketMapper::entityToDto)
        .toList();
  }
}
