package org.andersen.homework;

public class Ticket {

  private Short id;

  private Float priceInUsd;

  private String concertHallName;

  private Short eventCode;

  private Boolean isPromo;

  private final Long creationUnixTimeInMs;

  private Character stadiumSector;

  private Float backpackWeightInKg;

  public Ticket() {
    this.creationUnixTimeInMs = System.currentTimeMillis() / 1000L;
  }

  public Ticket(String concertHallName, Short eventCode) {
    this();
    this.concertHallName = concertHallName;
    this.eventCode = eventCode;
  }

  public Ticket(Short id, Float priceInUsd, String concertHallName, Short eventCode,
      Boolean isPromo, Character stadiumSector, Float backpackWeightInKg) {
    this(concertHallName, eventCode);
    this.id = id;
    this.priceInUsd = priceInUsd;
    this.isPromo = isPromo;
    this.stadiumSector = stadiumSector;
    this.backpackWeightInKg = backpackWeightInKg;
  }

  public Short getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Ticket{" +
        "id=" + id +
        ", priceInUsd=" + priceInUsd +
        ", concertHallName='" + concertHallName + '\'' +
        ", eventCode=" + eventCode +
        ", isPromo=" + isPromo +
        ", creationUnixTimeInMs=" + creationUnixTimeInMs +
        ", stadiumSector=" + stadiumSector +
        ", backpackWeightInKg=" + backpackWeightInKg +
        '}';
  }
}
