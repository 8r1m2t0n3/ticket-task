package org.andersen.homework.model.entity.user;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.andersen.homework.model.entity.Ticket;
import org.andersen.homework.model.enums.UserRole;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {

  public Admin() {
    super(UserRole.ADMIN);
  }

  public void checkTicket(Ticket ticket) {
    System.out.println("Ticket checked: " + ticket);
  }

  @Override
  public void sayHi() {
    System.out.println("Hi! I am Admin");
  }
}
