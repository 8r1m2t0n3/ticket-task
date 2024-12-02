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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = true)
  private Client client;

  @Column(name = "price_in_usd")
  private BigDecimal priceInUsd;

  public Ticket(BigDecimal priceInUsd) {
    setPriceInUsd(priceInUsd);
  }

  public void setPriceInUsd(BigDecimal priceInUsd) {
    this.priceInUsd = priceInUsd.stripTrailingZeros().setScale(2, RoundingMode.DOWN);
  }

  @Override
  public String toString() {
    return "Ticket{" +
        "id=" + id +
        ", client_id=" + (client != null ? client.getId() : null) +
        ", priceInUsd=" + priceInUsd +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ticket ticket = (Ticket) o;

    return Objects.equals(id, ticket.id) &&
        Objects.equals(client != null ? client.getId() : null, ticket.client != null ? ticket.client.getId() : null) &&
        Objects.equals(
            priceInUsd != null ? priceInUsd.stripTrailingZeros().setScale(2, RoundingMode.DOWN) : null,
            ticket.priceInUsd != null ? ticket.priceInUsd.stripTrailingZeros().setScale(2, RoundingMode.DOWN) : null);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id,
        client != null ? client.getId() : null,
        priceInUsd != null ? priceInUsd.stripTrailingZeros().setScale(2, RoundingMode.DOWN) : null);
  }
}
