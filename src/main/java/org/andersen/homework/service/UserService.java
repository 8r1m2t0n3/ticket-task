package org.andersen.homework.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dao.jdbc.UserDaoJdbc;
import org.andersen.homework.model.entity.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserDaoJdbc userDao;

  @Transactional
  public User save(User user) {
    return userDao.save(user);
  }

  @Transactional
  public void update(UUID id, User user) {
    userDao.update(id, user);
  }

  @Transactional
  public void delete(UUID id) {
    userDao.delete(id);
  }

  public User getById(UUID id) {
    return userDao.getById(id);
  }

  public List<User> getAll() {
    return userDao.getAll();
  }
}
