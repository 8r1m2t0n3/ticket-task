package org.andersen.homework.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.andersen.homework.model.dao.impl.UserDao;
import org.andersen.homework.model.entity.user.User;

public class UserService {

  private final UserDao userDao;

  public UserService() {
    this.userDao = new UserDao();
  }

  public User save(User user) {
    return userDao.save(user);
  }

  public void update(UUID id, User user) {
    userDao.update(id, user);
  }

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
