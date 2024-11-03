package org.andersen.homework.model.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.andersen.homework.annotation.NullableWarning;
import org.andersen.homework.model.enums.StadiumSector;

@Getter
@Setter
@AllArgsConstructor
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

  public Ticket(Short id, Float priceInUsd, String concertHallName, Short eventCode,
      Boolean isPromo,
      Float backpackWeightInKg) {
    this.id = id;
    this.priceInUsd = priceInUsd;
    this.concertHallName = concertHallName;
    this.eventCode = eventCode;
    this.isPromo = isPromo;
    this.backpackWeightInKg = backpackWeightInKg;
    checkFields();
  }

  public void share(String phoneNumber) {
    System.out.println("Ticket was shared by phone");
  }

  public void share(String phoneNumber, String email) {
    System.out.println("Ticket was shared by phone and email");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ticket ticket = (Ticket) o;
    return Objects.equals(id, ticket.id) && Objects.equals(priceInUsd,
        ticket.priceInUsd) && Objects.equals(concertHallName, ticket.concertHallName)
        && Objects.equals(eventCode, ticket.eventCode) && Objects.equals(isPromo,
        ticket.isPromo) && Objects.equals(backpackWeightInKg, ticket.backpackWeightInKg)
        && Objects.equals(time, ticket.time) && stadiumSector == ticket.stadiumSector;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, priceInUsd, concertHallName, eventCode, isPromo, backpackWeightInKg,
        time,
        stadiumSector);
  }

  @Override
  public String toString() {
    return "Ticket{" +
        "id=" + id +
        ", priceInUsd=" + priceInUsd +
        ", concertHallName='" + concertHallName + '\'' +
        ", eventCode=" + eventCode +
        ", isPromo=" + isPromo +
        ", backpackWeightInKg=" + backpackWeightInKg +
        ", time=" + time +
        ", stadiumSector=" + stadiumSector +
        '}';
  }

  private void checkFields() {
    Class<?> clazz = this.getClass();
    for (var field : clazz.getDeclaredFields()) {
      if (field.isAnnotationPresent(NullableWarning.class)) {
        field.setAccessible(true);
        try {
          if (field.get(this) == null) {
            String message = String.format(field.getAnnotation(NullableWarning.class).message(),
                field.getName(), clazz.getSimpleName());
            System.out.println(message);
          }
        } catch (IllegalAccessException ignore) {
        }
      }
    }
  }
}
