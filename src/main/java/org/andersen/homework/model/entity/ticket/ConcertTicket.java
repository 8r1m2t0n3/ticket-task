package org.andersen.homework.model.entity.ticket;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.annotation.NullableWarning;
import org.andersen.homework.model.enums.StadiumSector;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ConcertTicket extends Ticket {

  @Size(max = 10)
  @NullableWarning
  private String concertHallName;

  @Max(999)
  @Min(0)
  @NullableWarning
  private Short eventCode;

  private Float backpackWeightInKg;

  @NullableWarning
  private StadiumSector stadiumSector;

  @NullableWarning
  private LocalDateTime time;

  private Boolean isPromo;

  public ConcertTicket(BigDecimal priceInUsd, String concertHallName, Short eventCode,
                       Float backpackWeightInKg, StadiumSector stadiumSector, LocalDateTime time, Boolean isPromo) {
    super(priceInUsd);
    this.concertHallName = concertHallName;
    this.eventCode = eventCode;
    this.backpackWeightInKg = backpackWeightInKg;
    this.stadiumSector = stadiumSector;
    this.time = time;
    this.isPromo = isPromo;
  }
}
