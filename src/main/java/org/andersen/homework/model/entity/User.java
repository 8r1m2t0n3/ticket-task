package org.andersen.homework.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class User {

  private final Short id;

  public abstract void printRole();

  public void print() {
    System.out.println("I am a User");
  }
}
