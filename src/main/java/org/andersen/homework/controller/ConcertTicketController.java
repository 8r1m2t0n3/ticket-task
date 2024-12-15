package org.andersen.homework.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.ticket.concert.ConcertTicketDto;
import org.andersen.homework.model.dto.ticket.concert.ConcertTicketUpdateDto;
import org.andersen.homework.service.ticket.ConcertTicketService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert-ticket")
public class ConcertTicketController {

  private final ConcertTicketService concertTicketService;

  @GetMapping("/by-id")
  public ConcertTicketDto getTicketById(@RequestParam UUID id) {
    return concertTicketService.getById(id);
  }

  @GetMapping("/by-client-id")
  public List<ConcertTicketDto> getTicketByClientId(@RequestParam UUID clientId) {
    return concertTicketService.getByClientId(clientId);
  }

  @GetMapping
  public List<ConcertTicketDto> getAllTickets() {
    return concertTicketService.getAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ConcertTicketDto saveTicket(@RequestBody ConcertTicketUpdateDto ticketSaveDto) {
    return concertTicketService.save(ticketSaveDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTicketById(@PathVariable UUID id) {
    concertTicketService.delete(id);
  }

  @PutMapping
  public void updateTicket(@RequestBody ConcertTicketUpdateDto ticketUpdateDto) {
    concertTicketService.update(ticketUpdateDto);
  }
}
