package org.andersen.homework;


import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.entity.user.Admin;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.andersen.homework.service.TicketService;
import org.andersen.homework.service.UserService;

public class MainClass {

  private static final TicketService TICKET_SERVICE = new TicketService();
  private static final UserService USER_SERVICE = new UserService();

  public static void main(String[] args) {

    for (User user : USER_SERVICE.getAll()) {
      USER_SERVICE.remove(user.getId());
    }
    for (Ticket ticket : TICKET_SERVICE.getAll()) {
      TICKET_SERVICE.remove(ticket.getId());
    }

    ConcertTicket concertTicket = TicketService.getRandomConcertTicket();
    BusTicket busTicket = TicketService.getRandomBusTicket();

    Ticket savedConcertTicket = TICKET_SERVICE.save(concertTicket);
    Ticket savedBusTicket = TICKET_SERVICE.save(busTicket);

    Client client = new Client();
    client.setTicket(savedConcertTicket);

    Admin admin = new Admin();

    User savedClient = USER_SERVICE.save(client);
    User savedAdmin = USER_SERVICE.save(admin);

    System.out.println(USER_SERVICE.getById(savedClient.getId()));
    System.out.println(savedConcertTicket);
    System.out.println(TICKET_SERVICE.getById(savedConcertTicket.getId()));
    System.out.println(TICKET_SERVICE.getByUserId(savedClient.getId()));

    ((Client) savedClient).setTicket(savedBusTicket);
    USER_SERVICE.update(savedClient.getId(), savedClient);

    System.out.println(USER_SERVICE.getById(savedClient.getId()));

    USER_SERVICE.remove(savedAdmin.getId());
    System.out.println(USER_SERVICE.getById(savedAdmin.getId()));

    System.out.println(USER_SERVICE.getAll());
    System.out.println(TICKET_SERVICE.getAll());

    savedConcertTicket.setClient((Client) savedClient);
    TICKET_SERVICE.update(savedConcertTicket.getId(), savedConcertTicket);
    System.out.println(TICKET_SERVICE.getById(savedConcertTicket.getId()));
  }
}
