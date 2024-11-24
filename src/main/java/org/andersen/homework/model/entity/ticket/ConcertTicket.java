package org.andersen.homework.model.entity.ticket;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
import org.andersen.homework.model.enums.TicketType;

@Entity
@DiscriminatorValue("CONCERT")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ConcertTicket extends Ticket {

  @Size(max = 10)
  @NullableWarning
  @Column(name = "concert_hall_name")
  private String concertHallName;

  @Max(999)
  @Min(0)
  @NullableWarning
  @Column(name = "event_code")
  private Short eventCode;

  @Column(name = "backpack_weight_in_kg")
  private Float backpackWeightInKg;

  @NullableWarning
  @Column(name = "stadium_sector")
  private StadiumSector stadiumSector;

  @NullableWarning
  @Column(name = "time")
  private LocalDateTime time;

  @Column(name = "is_promo")
  private Boolean isPromo;

  public ConcertTicket(BigDecimal priceInUsd, String concertHallName, Short eventCode,
                       Float backpackWeightInKg, StadiumSector stadiumSector, LocalDateTime time, Boolean isPromo) {
    super(TicketType.CONCERT, priceInUsd);
    this.concertHallName = concertHallName;
    this.eventCode = eventCode;
    this.backpackWeightInKg = backpackWeightInKg;
    this.stadiumSector = stadiumSector;
    this.time = time;
    this.isPromo = isPromo;
  }
}
