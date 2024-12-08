package org.andersen.homework.util.mapper.user;

import org.andersen.homework.model.dto.user.UserSaveDto;
import org.andersen.homework.model.dto.user.UserUpdateDto;
import org.andersen.homework.model.dto.user.UserDto;
import org.andersen.homework.model.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, AdminMapper.class})
public interface UserMapper {
  UserDto entityToDto(User user);
  User dtoToEntity(UserDto userDto);
  User saveDtoToEntity(UserSaveDto userSaveDto);
  User updateDtoToEntity(UserUpdateDto userUpdateDto);
}
