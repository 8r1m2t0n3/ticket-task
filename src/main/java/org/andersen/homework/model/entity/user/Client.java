package org.andersen.homework.model.entity.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.entity.ticket.Ticket;

@Entity
@DiscriminatorValue("CLIENT")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Client extends User {

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "ticket_id", referencedColumnName = "id")
  private Ticket ticket;
}
