package org.andersen.homework.model.entity.ticket;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
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
import org.andersen.homework.model.enums.TicketType;

@Entity
@Table(name = "ticket")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotNull
  @Column(name = "type")
  private TicketType type;

  @NullableWarning
  @Column(name = "price_in_usd")
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
