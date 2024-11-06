package org.andersen.homework.model.entity;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.andersen.homework.model.enums.UserRole;

@Getter
@Setter
public class Client extends User {

  private Ticket ticket;

  public Client(Short id) {
    super(id, UserRole.CLIENT);
  }

  @Override
  public void sayHi() {
    System.out.println("Hi! I am User");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Client client = (Client) o;
    return Objects.equals(ticket, client.ticket)
        && Objects.equals(getId(), client.getId())
        && Objects.equals(getRole(), client.getRole());
  }

  @Override
  public int hashCode() {
    return Objects.hash(ticket, getId(), getRole());
  }

  @Override
  public String toString() {
    return "User(" +
        "id=" + getId() + ", " +
        "role=" + getRole() + ", " +
        "ticket=" + ticket +
        ')';
  }
}
