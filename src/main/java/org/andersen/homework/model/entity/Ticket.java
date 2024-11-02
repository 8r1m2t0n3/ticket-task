package org.andersen.homework.model.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.enums.StadiumSector;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

  @Max(999)
  @Min(0)
  private Short id;

  private Float priceInUsd;

  @Size(max = 10)
  private String concertHallName;

  @Max(999)
  @Min(0)
  private Short eventCode;

  private Boolean isPromo;

  private LocalDateTime time;

  private StadiumSector stadiumSector;

  private Float backpackWeightInKg;
}
