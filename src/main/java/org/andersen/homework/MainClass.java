package org.andersen.homework;

import java.util.List;
import org.andersen.homework.connection.DBConnectionSingleton;
import org.andersen.homework.exception.ticket.TicketSavingErrorException;
import org.andersen.homework.exception.user.UserSavingErrorException;
import org.andersen.homework.util.TransactionalConnectionManager;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.entity.user.Admin;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.andersen.homework.service.TicketService;
import org.andersen.homework.service.UserService;

public class MainClass {

  private static final String TICKET_AND_USER_TABLES_CREATION_SQL_FILE = "db/sql/ticket_and_user_tables_creation.sql";
  private static final DBConnectionSingleton DB_CONNECTION = DBConnectionSingleton.getInstance();

  private static void ticketsStand() {
    TicketService ticketService = new TicketService();

    ConcertTicket concertTicket = TicketService.getRandomConcertTicket();
    Ticket savedConcertTicket = ticketService.save(concertTicket);

    BusTicket busTicket = TicketService.getRandomBusTicket();
    Ticket savedBusTicket = ticketService.save(busTicket);

    Ticket receivedConcertTicket = ticketService.getById(savedConcertTicket.getId());
    System.out.println(receivedConcertTicket);

    Ticket receivedBusTicket = ticketService.getById(savedBusTicket.getId());
    System.out.println(receivedBusTicket);

    List<Ticket> tickets = ticketService.getAll();
    System.out.println("Tickets in db: " + tickets.size());

    System.out.println("Old ticket: " + savedConcertTicket);
    ConcertTicket newConcertTicket = TicketService.getRandomConcertTicket();
    newConcertTicket.setTime(((ConcertTicket) savedConcertTicket).getTime());
    ticketService.update(savedConcertTicket.getId(), newConcertTicket);
    Ticket updatedConcertTicket = ticketService.getById(savedConcertTicket.getId());
    System.out.println("Updated ticket: " + updatedConcertTicket);

    for (Ticket ticket : ticketService.getAll()) {
      ticketService.delete(ticket.getId());
    }
    System.out.println("Tickets in db: " + ticketService.getAll().size());
  }

  public static void usersStand() {
    UserService userService = new UserService();
    TicketService ticketService = new TicketService();

    Client client = new Client();
    Ticket ticket = TicketService.getRandomConcertTicket();
    Ticket savedTicket = ticketService.save(ticket);
    client.setTicket(savedTicket);
    User savedClient = userService.save(client);

    Admin admin = new Admin();
    User savedAdmin = userService.save(admin);

    List<User> users = userService.getAll();
    System.out.println("Users in db: " + users.size());

    User receivedClient = userService.getById(savedClient.getId());
    System.out.println("Client in db: " + receivedClient);
    User receivedAdmin = userService.getById(savedAdmin.getId());
    System.out.println("Admin in db: " + receivedAdmin);

    System.out.println("Old client " + receivedClient);
    ((Client) receivedClient).setTicket(ticketService.save(TicketService.getRandomBusTicket()));
    userService.update(receivedClient.getId(), receivedClient);
    System.out.println("Updated client " + userService.getById(receivedClient.getId()));

    System.out.println("Tickets count is db: " + ticketService.getAll().size());
    for (User user : users) {
      userService.delete(user.getId());
    }
    for (Ticket t : ticketService.getAll()) {
      ticketService.delete(t.getId());
    }
    System.out.println("Users in db " + userService.getAll().size());
  }

  private static void transactionalConnectionStand() {
    UserService userService = new UserService();
    TicketService ticketService = new TicketService();

    TransactionalConnectionManager transactionalManager = new TransactionalConnectionManager();

    User client1 = new Client();
    User client2 = new Client();
    User client3 = new Client();

    System.out.println("Users number in db before failed transactional connection: " + userService.getAll().size());
    System.out.println("Tickets number in db before failed transactional connection: " + ticketService.getAll().size());

    transactionalManager.disableAutoCommit();

    transactionalManager.setSavepoint("SaveUsers1");
    try {
      userService.save(client1);
      userService.save(client2);
      userService.save(client3);

      Ticket ticket = TicketService.getRandomConcertTicket();
      ticketService.save(ticket);

      throw new UserSavingErrorException(new Exception());

    } catch (UserSavingErrorException | TicketSavingErrorException e) {
      transactionalManager.rollbackToSavepoint();
    } finally {
      transactionalManager.releaseSavepoint();
    }
    transactionalManager.commitTransactions();

    transactionalManager.enableAutoCommit();

    System.out.println("Users number in db after failed transactional connection: " + userService.getAll().size());
    System.out.println("Tickets number in db after failed transactional connection: " + ticketService.getAll().size());

    transactionalManager.disableAutoCommit();

    transactionalManager.setSavepoint("SaveUsers2");
    try {
      userService.save(client1);
      userService.save(client2);
      userService.save(client3);

      Ticket ticket = TicketService.getRandomConcertTicket();
      ticketService.save(ticket);

    } catch (UserSavingErrorException | TicketSavingErrorException e) {
      transactionalManager.rollbackToSavepoint();
    } finally {
      transactionalManager.releaseSavepoint();
    }
    transactionalManager.commitTransactions();

    transactionalManager.enableAutoCommit();

    System.out.println("Users number in db after successful transactional connection: " + userService.getAll().size());
    System.out.println("Tickets number in db after successful transactional connection: " + ticketService.getAll().size());

    for (User user : userService.getAll()) {
      userService.delete(user.getId());
    }
    for (Ticket t : ticketService.getAll()) {
      ticketService.delete(t.getId());
    }
  }

  public static void main(String[] args) {
    DB_CONNECTION.openConnection();
    DB_CONNECTION.executeSQLFile(TICKET_AND_USER_TABLES_CREATION_SQL_FILE);

    ticketsStand();
    System.out.println("\n/////////////////////////////\n");
    usersStand();
    System.out.println("\n/////////////////////////////\n");
    transactionalConnectionStand();

    DB_CONNECTION.closeConnection();
  }
}
