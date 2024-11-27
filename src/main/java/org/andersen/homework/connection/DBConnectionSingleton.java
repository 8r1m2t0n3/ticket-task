package org.andersen.homework.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;
import org.andersen.homework.exception.db.ClosingConnectionFailedException;
import org.andersen.homework.exception.db.ConfigurationLoadingFailedException;
import org.andersen.homework.exception.db.ConnectionOpeningFailedException;
import org.andersen.homework.exception.db.FileExecutionFailedException;

public class DBConnectionSingleton {

  private static final String DB_PROPERTIES_FILE = "db/db.properties";
  private static final String DB_URL_LABEL = "url";
  private static final String USER_LABEL = "user";
  private static final String PASSWORD_LABEL = "password";

  private final String dbUrl;
  private final String user;
  private final String password;

  private static DBConnectionSingleton dbConnectionSingleton = null;
  private static Connection connection = null;

  private DBConnectionSingleton() {
    Properties properties = loadProperties();
    dbUrl = properties.getProperty(DB_URL_LABEL);
    user = properties.getProperty(USER_LABEL);
    password = properties.getProperty(PASSWORD_LABEL);
  }

  public static synchronized DBConnectionSingleton getInstance() {
    if (dbConnectionSingleton == null) {
      dbConnectionSingleton = new DBConnectionSingleton();
    }
    return dbConnectionSingleton;
  }

  public Connection getConnection() {
    return connection;
  }

  public void openConnection() {
    if (connection == null) {
      try {
        connection = DriverManager.getConnection(dbUrl, user, password);
      } catch (SQLException e) {
        throw new ConnectionOpeningFailedException(e);
      }
    }
  }

  public void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
        connection = null;
      } catch (SQLException e) {
        throw new ClosingConnectionFailedException(e);
      }
    }
  }

  public void executeSQLFile(String filePath) {
    try {
      Path path = Paths.get(Objects.requireNonNull(
          DBConnectionSingleton.class.getClassLoader().getResource(filePath)).toURI());
      String sql = Files.readString(path);
      try (Statement statement = connection.createStatement()) {
        statement.execute(sql);
      }
    } catch (Exception e) {
      throw new FileExecutionFailedException(filePath, e);
    }
  }

  private Properties loadProperties() {
    String propertiesPath = Objects.requireNonNull(
        DBConnectionSingleton.class.getClassLoader()
            .getResource(DB_PROPERTIES_FILE)).getPath();

    try (FileInputStream fs = new FileInputStream(propertiesPath)) {
      Properties properties = new Properties();
      properties.load(fs);

      properties.replaceAll((key, value) -> {
        if (value instanceof String str && str.startsWith("${") && str.endsWith("}")) {
          String envKey = str.substring(2, str.length() - 1);
          return System.getenv().getOrDefault(envKey, str);
        }
        return value;
      });

      return properties;
    } catch (IOException e) {
      throw new ConfigurationLoadingFailedException(e);
    }
  }
}
