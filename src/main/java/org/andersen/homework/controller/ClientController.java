package org.andersen.homework.controller;

import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.user.client.ClientDto;
import org.andersen.homework.model.dto.user.client.ClientSaveDto;
import org.andersen.homework.model.dto.user.client.ClientUpdateDto;
import org.andersen.homework.service.user.ClientService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

  private final ClientService clientService;

  @GetMapping("/{id}")
  public ClientDto getClientById(@PathVariable UUID id) {
    return clientService.getById(id);
  }

  @GetMapping
  public List<ClientDto> getAllClients() {
    return clientService.getAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ClientDto saveClient(@RequestBody ClientSaveDto clientSaveDto) {
    return clientService.save(clientSaveDto);
  }

  @PutMapping
  public void updateClient(@RequestBody ClientUpdateDto clientUpdateDto) {
    clientService.update(clientUpdateDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteClientById(@PathVariable UUID id) {
    clientService.deleteById(id);
  }
}
