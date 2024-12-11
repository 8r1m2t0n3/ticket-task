package org.andersen.homework.model.dto.user.client;

import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.andersen.homework.model.dto.ticket.TicketIdOnlyDto;
import org.andersen.homework.model.dto.user.UserUpdateDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class ClientUpdateDto extends UserUpdateDto {
  private Set<TicketIdOnlyDto> tickets;
}
