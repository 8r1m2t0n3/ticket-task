package org.andersen.homework.model.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.annotation.NullableWarning;
import org.andersen.homework.model.enums.StadiumSector;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class Ticket {

  @Max(999)
  @Min(0)
  @NullableWarning
  private final Short id;

  @NullableWarning
  private final Float priceInUsd;

  @Size(max = 10)
  @NullableWarning
  private final String concertHallName;

  @Max(999)
  @Min(0)
  @NullableWarning
  private final Short eventCode;

  @NullableWarning
  private final Boolean isPromo;

  @NullableWarning
  private final Float backpackWeightInKg;

  @NullableWarning
  private LocalDateTime time;

  @NullableWarning
  private StadiumSector stadiumSector;

  public void share(String phoneNumber) {
    System.out.println("Ticket was shared by phone: " + phoneNumber + ".");
  }

  public void share(String phoneNumber, String email) {
    System.out.println("Ticket was shared by phone: " + phoneNumber + " and email: " + email + ".");
  }
}
