package org.andersen.homework.model.dto.ticket;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.dto.user.client.ClientIdOnlyDto;

@Getter
@Setter
@ToString
public class TicketDto {
  private UUID id;
  private ClientIdOnlyDto client;
  private BigDecimal priceInUsd;
}
