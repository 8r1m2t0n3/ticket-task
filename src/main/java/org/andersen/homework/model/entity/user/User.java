package org.andersen.homework.model.entity.user;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.enums.UserRole;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class User {

  private UUID id;
  private final UserRole role;

  public User(UserRole role) {
    this.role = role;
  }
}
