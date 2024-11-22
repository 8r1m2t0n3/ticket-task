package org.andersen.homework.model.dao.impl;

import org.andersen.homework.connection.DBConnectionSingleton;

public class DaoFactory {

  public static TicketDaoJdbc createTicketDao() {
    return new TicketDaoJdbc(DBConnectionSingleton.getInstance().getConnection());
  }

  public static UserDaoJdbc createUserDao() {
    return new UserDaoJdbc(DBConnectionSingleton.getInstance().getConnection());
  }
}
