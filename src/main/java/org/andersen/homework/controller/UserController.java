package org.andersen.homework.controller;

import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.andersen.homework.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable UUID id) {
    return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateUser(@PathVariable UUID id, @RequestBody User user) {
    userService.update(id, user);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserById(@PathVariable UUID id) {
    userService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/{clientId}/tickets")
  public ResponseEntity<Void> addTicketsToClient(@PathVariable UUID clientId, @RequestBody List<Ticket> tickets) {
    userService.addTicketsToClientById(clientId, tickets);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/client")
  public ResponseEntity<Client> saveClientWithTickets(@RequestBody Client client, @RequestParam List<Ticket> tickets) {
    return new ResponseEntity<>(userService.saveClientWithTickets(client, tickets), HttpStatus.CREATED);
  }
}
