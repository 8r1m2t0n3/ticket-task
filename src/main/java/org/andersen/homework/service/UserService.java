package org.andersen.homework.service;

import java.util.List;
import java.util.UUID;
import org.andersen.homework.model.dao.DaoFactory;
import org.andersen.homework.model.dao.UserDaoJDBC;
import org.andersen.homework.model.entity.user.User;

public class UserService {

  private final UserDaoJDBC userDao = DaoFactory.createUserDao();

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
    return userDao.get(id);
  }

  public List<User> getAll() {
    return userDao.getAll();
  }
}
