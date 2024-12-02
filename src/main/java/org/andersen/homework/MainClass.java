package org.andersen.homework;

import org.andersen.homework.config.HibernateConfig;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.andersen.homework.service.TicketService;
import org.andersen.homework.service.UserService;
import org.andersen.homework.util.TicketGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainClass {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);

    TicketService ticketService = context.getBean(TicketService.class);
    UserService userService = context.getBean(UserService.class);

    for (User user : userService.getAll()) {
      userService.remove(user.getId());
    }
    for (Ticket ticket : ticketService.getAll()) {
      ticketService.remove(ticket.getId());
    }

    Ticket savedTicket1 = ticketService.save(TicketGenerator.getRandomConcertTicket());
    Ticket savedTicket2 = ticketService.save(TicketGenerator.getRandomBusTicket());

    Client client = new Client();

    client.getTickets().add(savedTicket1);
    client.getTickets().add(savedTicket2);
    client.getTickets().add(TicketGenerator.getRandomBusTicket());

    Client savedClient = (Client) userService.save(client);

    Ticket savedTicket3 = ticketService.save(TicketGenerator.getRandomConcertTicket());

    savedClient.getTickets().removeIf(t -> t.getId().equals(savedTicket2.getId()));
    savedClient.getTickets().add(savedTicket3);
    savedClient.getTickets().add(TicketGenerator.getRandomBusTicket());

    userService.update(savedClient.getId(), savedClient);
  }
}
