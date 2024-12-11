package org.andersen.homework;

import java.io.IOException;
import org.andersen.homework.config.HibernateConfig;
import org.andersen.homework.service.TicketService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainClass {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);

    TicketService ticketService = context.getBean(TicketService.class);

    try {
      System.out.println(ticketService.loadBusTickets());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
