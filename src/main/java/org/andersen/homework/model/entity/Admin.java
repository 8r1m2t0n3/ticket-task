package org.andersen.homework.model.entity;

import org.andersen.homework.model.enums.UserRole;

public class Admin extends User {

  public Admin(Short id) {
    super(id, UserRole.ADMIN);
  }

  public void checkTicket(Ticket ticket) {
    System.out.println("Ticket checked: " + ticket);
  }

  @Override
  public void sayHi() {
    System.out.println("Hi! I am Admin");
  }
}
