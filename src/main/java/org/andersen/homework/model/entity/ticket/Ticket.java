package org.andersen.homework.model.entity.ticket;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.annotation.NullableWarning;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

  private final UUID id = UUID.randomUUID();

  @NullableWarning
  private Float priceInUsd;

  public void share(String phoneNumber) {
    System.out.println("Ticket was shared by phone: " + phoneNumber + ".");
  }

  public void share(String phoneNumber, String email) {
    System.out.println("Ticket was shared by phone: " + phoneNumber + " and email: " + email + ".");
  }
}
