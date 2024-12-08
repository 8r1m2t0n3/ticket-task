package org.andersen.homework.controller;

import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.user.client.ClientDto;
import org.andersen.homework.model.dto.user.client.ClientSaveDto;
import org.andersen.homework.model.dto.user.client.ClientUpdateDto;
import org.andersen.homework.service.user.ClientService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

  private final ClientService clientService;

  @GetMapping("/{id}")
  public ResponseEntity<ClientDto> getClientById(@PathVariable UUID id) {
    return new ResponseEntity<>(clientService.getById(id), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<ClientDto>> getAllClients() {
    return new ResponseEntity<>(clientService.getAll(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ClientDto> saveClient(@RequestBody ClientSaveDto clientSaveDto) {
    return new ResponseEntity<>(clientService.save(clientSaveDto), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<Void> updateClient(@RequestBody ClientUpdateDto clientUpdateDto) {
    clientService.update(clientUpdateDto);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClientById(@PathVariable UUID id) {
    clientService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
