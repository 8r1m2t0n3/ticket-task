package org.andersen.homework.model.entity.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.entity.Ticket;
import org.andersen.homework.model.enums.UserRole;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Client extends User {

  private Ticket ticket;

  public Client() {
    super(UserRole.CLIENT);
  }

  @Override
  public void sayHi() {
    System.out.println("Hi! I am User");
  }
}
