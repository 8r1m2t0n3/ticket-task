package org.andersen.homework.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.ticket.bus.BusTicketDto;
import org.andersen.homework.model.dto.ticket.bus.BusTicketSaveDto;
import org.andersen.homework.model.dto.ticket.bus.BusTicketUpdateDto;
import org.andersen.homework.service.ticket.BusTicketService;
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
@RequestMapping("/bus-ticket")
public class BusTicketController {

  private final BusTicketService busTicketService;

  @GetMapping("/by-id")
  public BusTicketDto getTicketById(@RequestParam UUID id) {
    return busTicketService.getById(id);
  }

  @GetMapping("/by-client-id")
  public List<BusTicketDto> getTicketByClientId(@RequestParam UUID clientId) {
    return busTicketService.getByClientId(clientId);
  }

  @GetMapping
  public List<BusTicketDto> getAllTickets() {
    return busTicketService.getAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BusTicketDto saveTicket(@RequestBody BusTicketSaveDto ticketSaveDto) {
    return busTicketService.save(ticketSaveDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTicketById(@PathVariable UUID id) {
    busTicketService.delete(id);
  }

  @PutMapping
  public void updateTicket(@RequestBody BusTicketUpdateDto ticketUpdateDto) {
    busTicketService.update(ticketUpdateDto);
  }
}
