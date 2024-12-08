package org.andersen.homework.model.dto.user.client;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.andersen.homework.model.dto.ticket.TicketIdOnlyDto;
import org.andersen.homework.model.dto.user.UserSaveDto;

@Getter
@Setter
@ToString(callSuper = true)
public class ClientSaveDto extends UserSaveDto {
  private Set<TicketIdOnlyDto> tickets;
}
