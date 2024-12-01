package org.andersen.homework.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dao.impl.UserDao;
import org.andersen.homework.model.entity.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserDao userDao;

  @Transactional
  public User save(User user) {
    return userDao.save(user);
  }

  @Transactional
  public void update(UUID id, User user) {
    userDao.update(id, user);
  }

  @Transactional
  public void remove(UUID id) {
    userDao.remove(id);
  }

  public Optional<User> getById(UUID id) {
    return Optional.ofNullable(userDao.findById(id));
  }

  public List<User> getAll() {
    return userDao.getAll();
  }
}
