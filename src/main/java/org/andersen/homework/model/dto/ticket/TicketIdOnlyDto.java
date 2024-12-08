package org.andersen.homework.model.dto.ticket;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TicketIdOnlyDto {
  @NotNull
  private UUID id;
}
