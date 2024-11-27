package org.andersen.homework.config;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryManager {

  @Getter
  private static final SessionFactory sessionFactory = buildSessionFactory();

  private static SessionFactory buildSessionFactory() {
    try {
      return new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      throw new ExceptionInInitializerError("Initial SessionFactory creation failed." + ex);
    }
  }
}
