package org.andersen.homework.service.user;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.user.admin.AdminDto;
import org.andersen.homework.model.dto.user.admin.AdminSaveDto;
import org.andersen.homework.model.entity.user.Admin;
import org.andersen.homework.repository.UserJpaRepository;
import org.andersen.homework.util.mapper.user.AdminMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

  private final UserJpaRepository userRepository;
  private final AdminMapper adminMapper;

  @Transactional
  public AdminDto save(AdminSaveDto adminSaveDto) {
    return adminMapper.entityToDto(userRepository.save(adminMapper.saveDtoToEntity(adminSaveDto)));
  }

  @Transactional
  public void deleteById(UUID id) {
    userRepository.deleteById(id);
  }

  public AdminDto getById(UUID id) {
    return userRepository.findById(id)
        .filter(Admin.class::isInstance)
        .map(Admin.class::cast)
        .map(adminMapper::entityToDto)
        .orElseThrow(() ->
        new RuntimeException("Admin by id: %s not found".formatted(id)));
  }

  public List<AdminDto> getAll() {
    return userRepository.findAll().stream()
        .filter(Admin.class::isInstance)
        .map(Admin.class::cast)
        .map(adminMapper::entityToDto)
        .toList();
  }
}
