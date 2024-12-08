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
import org.andersen.homework.model.enums.StadiumSector;

@Entity
@DiscriminatorValue("CONCERT")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ConcertTicket extends Ticket {

  @Size(max = 10)
  @Column(name = "concert_hall_name")
  private String concertHallName;

  @Max(999)
  @Min(0)
  @Column(name = "event_code")
  private Short eventCode;

  @Column(name = "backpack_weight_in_kg")
  private Float backpackWeightInKg;

  @Column(name = "stadium_sector")
  private StadiumSector stadiumSector;

  @Column(name = "time")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private LocalDateTime time;

  @Column(name = "is_promo")
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

  @ToString.Include(name = "time")
  @EqualsAndHashCode.Include
  public LocalDateTime getTime() {
    return time != null ? time.withNano(time.getNano() / 1000000 * 1000000) : null;
  }

}
