package org.andersen.homework.model.dto.ticket.concert;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.andersen.homework.model.dto.ticket.TicketDto;
import org.andersen.homework.model.enums.StadiumSector;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class ConcertTicketDto extends TicketDto {
  private String concertHallName;
  private Short eventCode;
  private Float backpackWeightInKg;
  private StadiumSector stadiumSector;
  private LocalDateTime time;
  private Boolean isPromo;
}
