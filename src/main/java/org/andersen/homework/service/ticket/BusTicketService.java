package org.andersen.homework.service.ticket;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.ticket.bus.BusTicketDto;
import org.andersen.homework.model.dto.ticket.bus.BusTicketSaveDto;
import org.andersen.homework.model.dto.ticket.bus.BusTicketUpdateDto;
import org.andersen.homework.repository.BusTicketJpaRepository;
import org.andersen.homework.util.mapper.ticket.BusTicketMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusTicketService {

  private final BusTicketJpaRepository busTicketRepository;
  private final BusTicketMapper busTicketMapper;

  @Transactional
  public BusTicketDto save(BusTicketSaveDto busTicketSaveDto) {
    return busTicketMapper.entityToDto(busTicketRepository.save(
        busTicketMapper.saveDtoToEntity(busTicketSaveDto)));
  }

  @Transactional
  public void delete(UUID id) {
    busTicketRepository.findById(id)
        .ifPresent(ticket -> busTicketRepository.deleteById(id));
  }

  @Transactional
  public void update(BusTicketUpdateDto ticketUpdateDto) {
    busTicketRepository.save(busTicketMapper.updateDtoToEntity(ticketUpdateDto));
  }

  public BusTicketDto getById(UUID id) {
    return busTicketRepository.findById(id)
        .map(busTicketMapper::entityToDto)
        .orElseThrow(() -> new RuntimeException("Bus ticket by id: %s not found".formatted(id)));
  }

  public List<BusTicketDto> getByClientId(UUID clientId) {
    return busTicketRepository.findByClientId(clientId).stream()
        .map(busTicketMapper::entityToDto)
        .toList();
  }

  public List<BusTicketDto> getAll() {
    return busTicketRepository.findAll().stream()
        .map(busTicketMapper::entityToDto)
        .toList();
  }
}
