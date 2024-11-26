package org.andersen.homework.model.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.andersen.homework.model.dao.Dao;
import org.andersen.homework.model.entity.user.User;
import org.andersen.homework.util.SessionFactoryManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDao implements Dao<User, UUID> {

  @Override
  public User save(User user) {
    Transaction transaction;
    User savedUser;
    try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      savedUser = session.merge(user);
      transaction.commit();
    }
    return savedUser;
  }

  @Override
  public void update(UUID id, User user) {
    Transaction transaction;
    try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();

      user.setId(id);
      session.merge(user);

      transaction.commit();
    }
  }

  @Override
  public void remove(UUID id) {
    Transaction transaction;
    try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Optional.ofNullable(session.get(User.class, id)).ifPresent(session::remove);
      transaction.commit();
    }
  }

  @Override
  public User findById(UUID id) {
    Transaction transaction;
    User user;
    try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      user = session.get(User.class, id);
      transaction.commit();
    }
    return user;
  }

  @Override
  public List<User> getAll() {
    Transaction transaction;
    List<User> users;
    try (Session session = SessionFactoryManager.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      users = session.createQuery("FROM User", User.class).list();
      transaction.commit();
    }
    return users;
  }
}
