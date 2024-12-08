package org.andersen.homework.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.ticket.concert.ConcertTicketDto;
import org.andersen.homework.model.dto.ticket.concert.ConcertTicketSaveDto;
import org.andersen.homework.model.dto.ticket.concert.ConcertTicketUpdateDto;
import org.andersen.homework.service.ticket.ConcertTicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert-ticket")
public class ConcertTicketController {

  private final ConcertTicketService concertTicketService;

  @GetMapping("/by-id/{id}")
  public ResponseEntity<ConcertTicketDto> getTicketById(@PathVariable UUID id) {
    return new ResponseEntity<>(concertTicketService.getById(id), HttpStatus.OK);
  }

  @GetMapping("/by-client-id/{clientId}")
  public ResponseEntity<List<ConcertTicketDto>> getTicketByClientId(@PathVariable UUID clientId) {
    return new ResponseEntity<>(concertTicketService.getByClientId(clientId), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<ConcertTicketDto>> getAllTickets() {
    return new ResponseEntity<>(concertTicketService.getAll(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ConcertTicketDto> saveTicket(@RequestBody ConcertTicketSaveDto ticketSaveDto) {
    return new ResponseEntity<>(concertTicketService.save(ticketSaveDto), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicketById(@PathVariable UUID id) {
    concertTicketService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping
  public ResponseEntity<Void> updateTicket(@RequestBody ConcertTicketUpdateDto ticketUpdateDto) {
    concertTicketService.update(ticketUpdateDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
