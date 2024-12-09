package org.andersen.homework.model.dto.ticket;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TicketSaveDto {
  private BigDecimal priceInUsd;
}
