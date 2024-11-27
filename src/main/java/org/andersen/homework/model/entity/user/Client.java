package org.andersen.homework.model.entity.user;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.entity.ticket.Ticket;
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

  public Client(UUID id, UserRole role) {
    super(id, role);
  }
}
