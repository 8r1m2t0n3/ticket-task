package org.andersen.homework.model.dto.user.admin;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.andersen.homework.model.dto.user.UserSaveDto;

@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class AdminSaveDto extends UserSaveDto {
}
