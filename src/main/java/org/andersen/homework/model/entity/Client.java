package org.andersen.homework.model.entity;

public class Client extends User {

  @Override
  public void printRole() {
    System.out.println("Client");
  }

  @Override
  public void print() {
    System.out.println("I am a Client");
  }

  public void getTicket() {
    System.out.println("Ticket received");
  }
}
