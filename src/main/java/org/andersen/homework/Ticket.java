package org.andersen.homework;

public class Ticket {

  private Short id;

  private String concertHallName;

  private Short eventCode;

  private Boolean isPromo;

  private Long creationUnixTimeInMs;

  private Character stadiumSector;

  private Float backpackWeightInKg;

  public Ticket(Short id, String concertHallName, Short eventCode, Boolean isPromo,
      Character stadiumSector, Float backpackWeightInKg) {
    this.id = id;
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

  private Long getCurrentUnixTime() {
    return System.currentTimeMillis() / 1000L;
  }

  public Ticket() {}

  public Short getId() {
    return id;
  }

  public String getConcertHallName() {
    return concertHallName;
  }

  public Short getEventCode() {
    return eventCode;
  }

  public Boolean getPromo() {
    return isPromo;
  }

  public Long getCreationUnixTime() {
    return creationUnixTimeInMs;
  }

  public Character getStadiumSector() {
    return stadiumSector;
  }

  public Float getBackpackWeightInKg() {
    return backpackWeightInKg;
  }

  @Override
  public String toString() {
    return "Ticket{" +
        "id=" + id +
        ", concertHallName='" + concertHallName + '\'' +
        ", eventCode=" + eventCode +
        ", isPromo=" + isPromo +
        ", creationUnixTime=" + creationUnixTimeInMs +
        ", stadiumSector=" + stadiumSector +
        ", backpackWeightInKg=" + backpackWeightInKg +
        '}';
  }
}
