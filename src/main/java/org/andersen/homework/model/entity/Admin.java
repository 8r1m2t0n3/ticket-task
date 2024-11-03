package org.andersen.homework.model.entity;

public class Admin extends User {

  @Override
  public void printRole() {
    System.out.println("Admin");
  }

  @Override
  public void print() {
    System.out.println("I am an Admin");
  }

  public void checkTicket() {
    System.out.println("Ticket checked");
  }
}
