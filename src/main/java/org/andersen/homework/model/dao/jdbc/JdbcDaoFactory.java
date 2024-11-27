package org.andersen.homework.model.dao.jdbc;

import org.andersen.homework.connection.DBConnectionSingleton;

public class JdbcDaoFactory {

  public static TicketDaoJdbc createTicketDao() {
    return new TicketDaoJdbc(DBConnectionSingleton.getInstance().getConnection());
  }

  public static UserDaoJdbc createUserDao() {
    return new UserDaoJdbc(DBConnectionSingleton.getInstance().getConnection());
  }
}
