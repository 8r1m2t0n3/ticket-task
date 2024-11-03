package org.andersen.homework.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class User {

  private Short id;

  public abstract void printRole();

  public void print() {
    System.out.println("I am a User");
  }
}
