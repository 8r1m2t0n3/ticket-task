package org.andersen.homework.model.entity.ticket;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.annotation.NullableWarning;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.enums.TicketType;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

  private UUID id;

  private Client client;

  @NotNull
  private TicketType type;

  @NullableWarning
  private BigDecimal priceInUsd;

  public Ticket(TicketType type, BigDecimal priceInUsd) {
    this.type = type;
    this.priceInUsd = priceInUsd;
  }

  public void share(String phoneNumber) {
    System.out.println("Ticket was shared by phone: " + phoneNumber + ".");
  }

  public void share(String phoneNumber, String email) {
    System.out.println("Ticket was shared by phone: " + phoneNumber + " and email: " + email + ".");
  }
}
