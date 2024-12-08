package org.andersen.homework.model.dto.user;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateDto {
  @NotNull
  private UUID id;
}
