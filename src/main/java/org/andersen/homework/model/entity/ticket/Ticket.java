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
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.entity.user.Client;

@Entity
@Table(name = "ticket")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  @ToString.Include
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Client client;

  @Column(name = "price_in_usd")
  @ToString.Include
  @EqualsAndHashCode.Include
  private BigDecimal priceInUsd;

  public Ticket(BigDecimal priceInUsd) {
    setPriceInUsd(priceInUsd);
  }

  public void setPriceInUsd(BigDecimal priceInUsd) {
    this.priceInUsd = priceInUsd != null
        ? priceInUsd.stripTrailingZeros().setScale(2, RoundingMode.DOWN)
        : null;
  }

  @ToString.Include(name = "client_id")
  @EqualsAndHashCode.Include
  private UUID getClientId() {
    return client != null ? client.getId() : null;
  }
}

