package org.andersen.homework.model.dto.user;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserIdOnlyDto {
  @NotNull
  private UUID id;
}
