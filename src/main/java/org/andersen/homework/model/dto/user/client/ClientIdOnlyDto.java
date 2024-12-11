package org.andersen.homework.model.dto.user.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.andersen.homework.model.dto.user.UserIdOnlyDto;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class ClientIdOnlyDto extends UserIdOnlyDto {
}
