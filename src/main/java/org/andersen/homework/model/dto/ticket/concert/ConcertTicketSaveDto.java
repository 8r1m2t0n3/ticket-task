package org.andersen.homework.model.dto.ticket.concert;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.dto.ticket.TicketSaveDto;
import org.andersen.homework.model.enums.StadiumSector;

@Getter
@Setter
@ToString(callSuper = true)
public class ConcertTicketSaveDto extends TicketSaveDto {
  private String concertHallName;
  private Short eventCode;
  private Float backpackWeightInKg;
  private StadiumSector stadiumSector;
  private LocalDateTime time;
  private Boolean isPromo;
}
