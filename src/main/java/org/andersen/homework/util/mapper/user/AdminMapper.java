package org.andersen.homework.util.mapper.user;

import org.andersen.homework.model.dto.user.admin.AdminDto;
import org.andersen.homework.model.dto.user.admin.AdminSaveDto;
import org.andersen.homework.model.entity.user.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
  AdminDto entityToDto(Admin admin);
  Admin dtoToEntity(AdminDto adminDto);
  Admin saveDtoToEntity(AdminSaveDto adminSaveDto);
}
