package org.andersen.homework.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Ticket {

  private Short id;

  private Float priceInUsd;

  private String concertHallName;

  private Short eventCode;

  private Boolean isPromo;

  private LocalDateTime time;

  private Character stadiumSector;

  private Float backpackWeightInKg;

  public Ticket() {
    this.time = LocalDateTime.now();
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
}
