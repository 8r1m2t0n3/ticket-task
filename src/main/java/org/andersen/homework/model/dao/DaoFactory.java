package org.andersen.homework.model.dao;

import org.andersen.homework.connection.DBConnectionSingleton;

public class DaoFactory {

  public static TicketDaoJDBC createTicketDao() {
    return new TicketDaoJDBC(DBConnectionSingleton.getInstance().getConnection());
  }

  public static UserDaoJDBC createUserDao() {
    return new UserDaoJDBC(DBConnectionSingleton.getInstance().getConnection());
  }
}
