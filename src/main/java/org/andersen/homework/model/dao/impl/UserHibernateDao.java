package org.andersen.homework.model.dao.impl;

import java.util.UUID;
import org.andersen.homework.model.dao.AbstractHibernateDao;
import org.andersen.homework.model.entity.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserHibernateDao extends AbstractHibernateDao<User, UUID> {

  public UserHibernateDao() {
    setClazz(User.class);
  }
}
