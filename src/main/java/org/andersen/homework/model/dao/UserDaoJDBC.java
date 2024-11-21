package org.andersen.homework.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.andersen.homework.exception.user.AllUsersReceivingErrorException;
import org.andersen.homework.exception.user.TicketAssignmentToUserErrorException;
import org.andersen.homework.exception.user.UserDeletingErrorException;
import org.andersen.homework.exception.user.UserReceivingByIdErrorException;
import org.andersen.homework.exception.user.UserSavingErrorException;
import org.andersen.homework.exception.user.UserUpdatingErrorException;
import org.andersen.homework.exception.user.UnknownUserRoleException;
import org.andersen.homework.model.entity.user.Admin;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.andersen.homework.model.enums.UserRole;

public class UserDaoJDBC implements Dao<User, UUID> {

  private static final String INSERT_QUERY = "INSERT INTO \"user\" (id, role, ticket_id) VALUES (?, ?, ?)";
  private static final String UPDATE_QUERY = "UPDATE \"user\" SET ticket_id = ? WHERE id = ?";
  private static final String DELETE_QUERY = "DELETE FROM \"user\" WHERE id = ?";
  private static final String SELECT_ALL_QUERY = "SELECT * FROM \"user\" WHERE id = ?";
  private static final String SELECT_USERS_COUNT_BY_TICKET_ID = "SELECT COUNT(*) FROM \"user\" WHERE ticket_id = ?";


  private final Connection connection;
  private final TicketDaoJDBC ticketDao;

  public UserDaoJDBC(Connection connection) {
    this.connection = connection;
    ticketDao = new TicketDaoJDBC(connection);
  }

  @Override
  public User save(User user) {
    UUID userId = UUID.randomUUID();
    user.setId(userId);

    try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
      preparedStatement.setObject(1, user.getId());
      preparedStatement.setString(2, user.getRole().name());

      if (user instanceof Client client) {
        UUID ticketId = client.getTicket() != null ? client.getTicket().getId() : null;

        if (ticketId != null) {
          if (isTicketAssigned(ticketId)) {
            throw new RuntimeException("The ticket is already assigned to another user.");
          }
          preparedStatement.setObject(3, ticketDao.get(ticketId) != null ? ticketId : null);
        } else {
          preparedStatement.setNull(3, Types.OTHER);
        }
      } else {
        preparedStatement.setNull(3, Types.OTHER);
      }

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new UserSavingErrorException(e);
    }
    return user;
  }

  @Override
  public void update(UUID id, User user) {
    try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {

      if (user instanceof Client client) {
        if (client.getTicket() != null && client.getTicket().getId() != null) {
          preparedStatement.setObject(1, client.getTicket().getId());
        } else {
          preparedStatement.setNull(1, Types.OTHER);
        }
        preparedStatement.setObject(2, id);

        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      throw new UserUpdatingErrorException(e);
    }
  }

  @Override
  public void delete(UUID id) {
    try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
      preparedStatement.setObject(1, id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new UserDeletingErrorException(e);
    }
  }

  @Override
  public User get(UUID id) {
    String sql = "SELECT * FROM \"user\" WHERE id = ?";
    User user = null;

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setObject(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        user = createUserFromResultSet(resultSet);
      }

    } catch (SQLException e) {
      throw new UserReceivingByIdErrorException(e);
    }

    return user;
  }

  @Override
  public List<User> getAll() {
    List<User> users = new ArrayList<>();

    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);

      while (resultSet.next()) {
        users.add(createUserFromResultSet(resultSet));
      }

    } catch (SQLException e) {
      throw new AllUsersReceivingErrorException(e);
    }

    return users;
  }

  private boolean isTicketAssigned(UUID ticketId) {
    try (PreparedStatement checkStatement = connection.prepareStatement(SELECT_USERS_COUNT_BY_TICKET_ID)) {
      checkStatement.setObject(1, ticketId);
      ResultSet resultSet = checkStatement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getInt(1) > 0;
      }
    } catch (SQLException e) {
      throw new TicketAssignmentToUserErrorException(e);
    }
    return false;
  }

  private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
    UUID id = UUID.fromString(resultSet.getString("id"));
    UserRole role = UserRole.valueOf(resultSet.getString("role"));
    UUID ticketId = resultSet.getObject("ticket_id", UUID.class);

    if (role == UserRole.CLIENT) {
      Client client = new Client();
      client.setId(id);
      if (ticketId != null) {
        client.setTicket(ticketDao.get(ticketId));
      }
      return client;
    } else if (role == UserRole.ADMIN) {
      Admin admin = new Admin();
      admin.setId(id);
      return admin;
    }

    throw new UnknownUserRoleException(role);
  }
}
