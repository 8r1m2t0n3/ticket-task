package org.andersen.homework.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

  private final TicketService ticketService;

  @GetMapping
  public ResponseEntity<List<Ticket>> getAllTickets() {
    return new ResponseEntity<>(ticketService.getAll(), HttpStatus.OK);
  }

  @PostMapping("/bus")
  public ResponseEntity<BusTicket> saveBusTicket(@RequestBody BusTicket busTicket) {
    return new ResponseEntity<>((BusTicket) ticketService.save(busTicket), HttpStatus.CREATED);
  }

  @PostMapping("/concert")
  public ResponseEntity<ConcertTicket> saveConcertTicket(@RequestBody ConcertTicket concertTicket) {
    return new ResponseEntity<>((ConcertTicket) ticketService.save(concertTicket), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicketById(@PathVariable UUID id) {
    ticketService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/bus/{id}")
  public ResponseEntity<Void> updateBusTicket(@PathVariable UUID id, @RequestBody BusTicket busTicket) {
    ticketService.update(id, busTicket);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/concert/{id}")
  public ResponseEntity<Void> updateConcertTicket(@PathVariable UUID id, @RequestBody ConcertTicket concertTicket) {
    ticketService.update(id, concertTicket);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/by-id")
  public ResponseEntity<Ticket> getTicketById(@RequestParam("id") UUID id) {
    return new ResponseEntity<>(ticketService.getById(id), HttpStatus.OK);
  }

  @GetMapping("/by-client-id")
  public ResponseEntity<List<Ticket>> getTicketByClientId(@RequestParam("clientId") UUID clientId) {
    return new ResponseEntity<>(ticketService.getByClientId(clientId), HttpStatus.OK);
  }
}
