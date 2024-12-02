package org.andersen.homework.model.entity.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
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

  @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
  private Set<Ticket> tickets = new HashSet<>();
}
