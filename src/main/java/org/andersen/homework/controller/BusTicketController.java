package org.andersen.homework.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.ticket.bus.BusTicketDto;
import org.andersen.homework.model.dto.ticket.bus.BusTicketSaveDto;
import org.andersen.homework.model.dto.ticket.bus.BusTicketUpdateDto;
import org.andersen.homework.service.ticket.BusTicketService;
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
@RequestMapping("/bus-ticket")
public class BusTicketController {

  private final BusTicketService busTicketService;

  @GetMapping("/by-id/{id}")
  public ResponseEntity<BusTicketDto> getTicketById(@PathVariable UUID id) {
    return new ResponseEntity<>(busTicketService.getById(id), HttpStatus.OK);
  }

  @GetMapping("/by-client-id/{clientId}")
  public ResponseEntity<List<BusTicketDto>> getTicketByClientId(@PathVariable UUID clientId) {
    return new ResponseEntity<>(busTicketService.getByClientId(clientId), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<BusTicketDto>> getAllTickets() {
    return new ResponseEntity<>(busTicketService.getAll(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<BusTicketDto> saveTicket(@RequestBody BusTicketSaveDto ticketSaveDto) {
    return new ResponseEntity<>(busTicketService.save(ticketSaveDto), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicketById(@PathVariable UUID id) {
    busTicketService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping
  public ResponseEntity<Void> updateTicket(@RequestBody BusTicketUpdateDto ticketUpdateDto) {
    busTicketService.update(ticketUpdateDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
