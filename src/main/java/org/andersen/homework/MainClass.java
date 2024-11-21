package org.andersen.homework;

import java.util.List;
import org.andersen.homework.connection.DBConnectionSingleton;
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
    DB_CONNECTION.openConnection();

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

    DB_CONNECTION.closeConnection();
  }

  public static void usersStand() {
    DB_CONNECTION.openConnection();

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
    System.out.println("Users in db " + userService.getAll().size());
    System.out.println("Tickets in db " + ticketService.getAll().size());

    for (Ticket t : ticketService.getAll()) {
      ticketService.delete(t.getId());
    }

    DB_CONNECTION.closeConnection();
  }

  public static void main(String[] args) {
    DB_CONNECTION.openConnection();
    DB_CONNECTION.executeSQLFile(TICKET_AND_USER_TABLES_CREATION_SQL_FILE);
    DB_CONNECTION.closeConnection();

    ticketsStand();
    System.out.println("\n/////////////////////////////\n");
    usersStand();
  }
}
