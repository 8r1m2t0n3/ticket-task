package org.andersen.homework.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.andersen.homework.exception.user.AllUsersReceivingErrorException;
import org.andersen.homework.exception.user.UserDeletingErrorException;
import org.andersen.homework.exception.user.UserReceivingByIdErrorException;
import org.andersen.homework.exception.user.UserSavingErrorException;
import org.andersen.homework.exception.user.UnknownUserRoleException;
import org.andersen.homework.exception.user.UserUpdatingErrorException;
import org.andersen.homework.model.dao.Dao;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.entity.user.Admin;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.entity.user.User;
import org.andersen.homework.model.enums.UserRole;
import org.andersen.homework.util.TransactionalConnectionManager;

public class UserDaoJdbc implements Dao<User, UUID> {

  private static final String INSERT_QUERY = "INSERT INTO \"user\" (id, role) VALUES (?, ?)";
  private static final String DELETE_QUERY = "DELETE FROM \"user\" WHERE id = ?";
  private static final String SELECT_ALL_QUERY = "SELECT * FROM \"user\"";
  private static final String SELECT_BY_ID_QUERY = "SELECT * FROM \"user\" WHERE id = ?";

  private final Connection connection;
  private final TransactionalConnectionManager transactionalManager;
  private final TicketDaoJdbc ticketDao;

  public UserDaoJdbc(Connection connection) {
    this.connection = connection;
    transactionalManager = new TransactionalConnectionManager();
    this.ticketDao = new TicketDaoJdbc(connection);
  }

  @Override
  public User save(User user) {
    boolean transactionalState = transactionalManager.getAutoCommit();
    transactionalManager.disableAutoCommit();

    UUID userId = user.getId() == null ? UUID.randomUUID() : user.getId();
    user.setId(userId);

    try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
      preparedStatement.setObject(1, userId);
      preparedStatement.setString(2, user.getRole().name());
      preparedStatement.executeUpdate();

      if (user instanceof Client client && client.getTicket() != null) {
        UUID ticketId = client.getTicket().getId();
        if (ticketId != null) {
          Ticket receivedTicket = ticketDao.getById(ticketId);
          if (receivedTicket != null) {
            receivedTicket.setClient(client);
            ticketDao.update(receivedTicket.getId(), receivedTicket);
          }
        }
      }

    } catch (SQLException e) {
      throw new UserSavingErrorException(e);
    } finally {
      if (transactionalState) {
        transactionalManager.enableAutoCommit();
      }
    }
    return user;
  }

  @Override
  public void update(UUID id, User user) {
    boolean transactionalState = transactionalManager.getAutoCommit();
    transactionalManager.disableAutoCommit();

    user.setId(id);

    try {
      User receivedUser = getById(id);

      if (receivedUser instanceof Client receivedClient) {
        Client updatedClient = (Client) user;
        Ticket owningTicket = receivedClient.getTicket();
        Ticket newTicket = Optional.ofNullable(updatedClient.getTicket())
            .map(Ticket::getId)
            .map(ticketDao::getById)
            .orElse(null);

        if (owningTicket != null) {
          if (newTicket == null) {

            owningTicket.setClient(null);
            ticketDao.update(owningTicket.getId(), owningTicket);

          } else if (newTicket.getId() != owningTicket.getId()) {

            owningTicket.setClient(null);
            ticketDao.update(owningTicket.getId(), owningTicket);

            newTicket.setClient(receivedClient);
            ticketDao.update(newTicket.getId(), newTicket);
          }
        }
      }
    } catch (Exception e) {
      throw new UserUpdatingErrorException(e);
    } finally {
      if (transactionalState) {
        transactionalManager.enableAutoCommit();
      }
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
  public User getById(UUID id) {
    try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
      preparedStatement.setObject(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        return createUserFromResultSet(resultSet);
      }

    } catch (SQLException e) {
      throw new UserReceivingByIdErrorException(e);
    }
    return null;
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

  private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
    UUID id = UUID.fromString(resultSet.getString("id"));
    UserRole role = UserRole.valueOf(resultSet.getString("role"));

    if (role == UserRole.CLIENT) {
      Client client = new Client();
      client.setId(id);
      client.setTicket(ticketDao.getByUserId(id));
      return client;
    } else if (role == UserRole.ADMIN) {
      Admin admin = new Admin();
      admin.setId(id);
      return admin;
    }

    throw new UnknownUserRoleException(role);
  }
}
