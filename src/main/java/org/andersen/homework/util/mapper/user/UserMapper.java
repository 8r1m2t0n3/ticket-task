package org.andersen.homework.util.mapper.user;

import org.andersen.homework.model.dto.user.UserDto;
import org.andersen.homework.model.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface UserMapper {
  UserDto entityToDto(User user);
}
