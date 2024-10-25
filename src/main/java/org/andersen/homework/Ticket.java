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

  public Ticket(Short id, Float priceInUsd, String concertHallName, Short eventCode,
      Boolean isPromo,
      Character stadiumSector, Float backpackWeightInKg) {
    this.id = id;
    this.priceInUsd = priceInUsd;
    this.concertHallName = concertHallName;
    this.eventCode = eventCode;
    this.isPromo = isPromo;
    this.creationUnixTimeInMs = getCurrentUnixTime();
    this.stadiumSector = stadiumSector;
    this.backpackWeightInKg = backpackWeightInKg;
  }

  public Ticket(String concertHallName, Short eventCode) {
    this.concertHallName = concertHallName;
    this.eventCode = eventCode;
    this.creationUnixTimeInMs = getCurrentUnixTime();
  }

  public Ticket() {
    this.creationUnixTimeInMs = getCurrentUnixTime();
  }

  private Long getCurrentUnixTime() {
    return System.currentTimeMillis() / 1000L;
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
