package org.andersen.homework.model.entity.user;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.andersen.homework.model.enums.UserRole;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public abstract class User {

  private final UUID id = UUID.randomUUID();
  private final UserRole role;

  public abstract void sayHi();
}
