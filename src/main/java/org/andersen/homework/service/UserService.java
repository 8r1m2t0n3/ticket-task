package org.andersen.homework.service;

import java.util.List;
import java.util.UUID;
import org.andersen.homework.model.dao.jdbc.JdbcDaoFactory;
import org.andersen.homework.model.dao.jdbc.UserDaoJdbc;
import org.andersen.homework.model.entity.user.User;

public class UserService {

  private final UserDaoJdbc userDao = JdbcDaoFactory.createUserDao();

  public User save(User user) {
    return userDao.save(user);
  }

  public void update(UUID id, User user) {
    userDao.update(id, user);
  }

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
