package org.andersen.homework.service.user;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.entity.user.User;
import org.andersen.homework.repository.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

  private final UserJpaRepository userRepository;

  @Transactional
  public User save(User user) {
    return userRepository.save(user);
  }

  @Transactional
  public void deleteById(UUID id) {
    userRepository.deleteById(id);
  }

  public User getById(UUID id) {
    return userRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Admin by id: %s not found".formatted(id)));
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }
}
