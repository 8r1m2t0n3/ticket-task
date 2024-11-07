package org.andersen.homework.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.enums.UserRole;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public abstract class User {

  @Setter
  private Short id;
  private UserRole role;

  public abstract void sayHi();
}
