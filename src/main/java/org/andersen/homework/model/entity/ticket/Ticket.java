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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.andersen.homework.model.entity.user.Client;

@Entity
@Table(name = "ticket")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @OneToOne(mappedBy = "ticket")
  private Client client;

  @Column(name = "price_in_usd")
  private BigDecimal priceInUsd;

  public Ticket(BigDecimal priceInUsd) {
    this.priceInUsd = priceInUsd;
  }

  @Override
  public String toString() {
    return "Ticket{" +
        "id=" + id +
        ", client_id=" + (client != null ? client.getId() : null) +
        ", priceInUsd=" + priceInUsd +
        '}';
  }
}
