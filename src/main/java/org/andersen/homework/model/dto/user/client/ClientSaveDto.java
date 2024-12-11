package org.andersen.homework.model.dto.user.client;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.andersen.homework.model.dto.ticket.TicketIdOnlyDto;
import org.andersen.homework.model.dto.user.UserSaveDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class ClientSaveDto extends UserSaveDto {
  private Set<TicketIdOnlyDto> tickets;
}
