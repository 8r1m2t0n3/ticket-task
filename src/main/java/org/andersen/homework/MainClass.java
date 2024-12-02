package org.andersen.homework;

import java.util.List;
import org.andersen.homework.config.ApplicationContextConfig;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.entity.user.Admin;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.andersen.homework.service.TicketService;
import org.andersen.homework.service.UserService;
import org.andersen.homework.util.TicketGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainClass {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);

    ticketsStand(context);
    System.out.println("\n/////////////////////////////\n");
    usersStand(context);
  }

  private static void ticketsStand(ApplicationContext context) {
    TicketService ticketService = context.getBean(TicketService.class);

    ConcertTicket concertTicket = TicketGenerator.getRandomConcertTicket();
    Ticket savedConcertTicket = ticketService.save(concertTicket);

    BusTicket busTicket = TicketGenerator.getRandomBusTicket();
    Ticket savedBusTicket = ticketService.save(busTicket);

    Ticket receivedConcertTicket = ticketService.getById(savedConcertTicket.getId());
    System.out.println(receivedConcertTicket);

    Ticket receivedBusTicket = ticketService.getById(savedBusTicket.getId());
    System.out.println(receivedBusTicket);

    List<Ticket> tickets = ticketService.getAll();
    System.out.println("Tickets in db: " + tickets.size());

    System.out.println("Old ticket: " + savedConcertTicket);
    ConcertTicket newConcertTicket = TicketGenerator.getRandomConcertTicket();
    newConcertTicket.setTime(((ConcertTicket) savedConcertTicket).getTime());
    ticketService.update(savedConcertTicket.getId(), newConcertTicket);
    Ticket updatedConcertTicket = ticketService.getById(savedConcertTicket.getId());
    System.out.println("Updated ticket: " + updatedConcertTicket);

    for (Ticket ticket : ticketService.getAll()) {
      ticketService.delete(ticket.getId());
    }
    System.out.println("Tickets in db: " + ticketService.getAll().size());
  }

  public static void usersStand(ApplicationContext context) {
    UserService userService = context.getBean(UserService.class);
    TicketService ticketService = context.getBean(TicketService.class);

    Client client = new Client();
    Ticket ticket = TicketGenerator.getRandomConcertTicket();
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
    ((Client) receivedClient).setTicket(ticketService.save(TicketGenerator.getRandomBusTicket()));
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
}
