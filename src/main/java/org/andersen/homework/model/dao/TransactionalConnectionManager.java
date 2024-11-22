package org.andersen.homework.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.connection.DBConnectionSingleton;

@RequiredArgsConstructor
public class TransactionalConnectionManager {

  protected final Connection connection;
  protected Savepoint savepoint;

  public TransactionalConnectionManager() {
    connection = DBConnectionSingleton.getInstance().getConnection();
  }

  public void disableAutoCommit() {
    try {
      connection.setAutoCommit(false);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void enableAutoCommit() {
    try {
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void setSavepoint(String name) {
    if (savepoint == null) {
      try {
        savepoint = connection.setSavepoint(name);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void commitTransactions() {
    try {
      connection.commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void rollbackToSavepoint() {
    if (savepoint != null) {
      try {
        connection.rollback(savepoint);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void releaseSavepoint() {
    if (savepoint != null) {
      try {
        connection.releaseSavepoint(savepoint);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      savepoint = null;
    }
  }
}
