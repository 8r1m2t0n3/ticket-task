package org.andersen.homework.model.dto.ticket;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
public class TicketDto {
  private UUID id;
  private UUID clientId;
  private BigDecimal priceInUsd;
}
