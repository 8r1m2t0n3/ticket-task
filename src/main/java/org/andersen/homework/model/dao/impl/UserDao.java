package org.andersen.homework.model.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.model.dao.Dao;
import org.andersen.homework.model.entity.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao implements Dao<User, UUID> {

  private final SessionFactory sessionFactory;

  @Override
  public User save(User user) {
    Transaction transaction;
    User savedUser;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      savedUser = session.merge(user);
      transaction.commit();
    }
    return savedUser;
  }

  @Override
  public void update(UUID id, User user) {
    Transaction transaction;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();

      user.setId(id);
      session.merge(user);

      transaction.commit();
    }
  }

  @Override
  public void remove(UUID id) {
    Transaction transaction;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Optional.ofNullable(session.get(User.class, id)).ifPresent(session::remove);
      transaction.commit();
    }
  }

  @Override
  public User findById(UUID id) {
    Transaction transaction;
    User user;
    try (Session session = sessionFactory.openSession()) {
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
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      users = session.createQuery("FROM User", User.class).list();
      transaction.commit();
    }
    return users;
  }
}
