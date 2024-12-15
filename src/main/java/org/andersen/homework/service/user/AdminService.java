package org.andersen.homework.service.user;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dto.user.UserDto;
import org.andersen.homework.model.entity.user.Admin;
import org.andersen.homework.repository.UserJpaRepository;
import org.andersen.homework.util.mapper.user.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

  private final UserJpaRepository userRepository;
  private final UserMapper userMapper;

  @Transactional
  public UserDto save() {
    return userMapper.entityToDto(userRepository.save(new Admin()));
  }

  @Transactional
  public void deleteById(UUID id) {
    userRepository.deleteById(id);
  }

  public UserDto getById(UUID id) {
    return userRepository.findById(id)
        .filter(Admin.class::isInstance)
        .map(Admin.class::cast)
        .map(userMapper::entityToDto)
        .orElseThrow(() ->
        new RuntimeException("Admin by id: %s not found".formatted(id)));
  }

  public List<UserDto> getAll() {
    return userRepository.findAll().stream()
        .filter(Admin.class::isInstance)
        .map(Admin.class::cast)
        .map(userMapper::entityToDto)
        .toList();
  }
}
