package org.andersen.homework.model.dao.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.andersen.homework.exception.ticket.*;
import org.andersen.homework.model.dao.Dao;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.entity.user.Client;
import org.andersen.homework.model.enums.BusTicketClass;
import org.andersen.homework.model.enums.BusTicketDuration;
import org.andersen.homework.model.enums.StadiumSector;
import org.andersen.homework.model.enums.TicketType;
import org.andersen.homework.model.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketDaoJdbc implements Dao<Ticket, UUID> {

  private static final String INSERT_QUERY = "INSERT INTO ticket(" +
      "id, type, price_in_usd, concert_hall_name, event_code, " +
      "backpack_weight_in_kg, stadium_sector, time, is_promo, " +
      "ticket_class, duration, start_date, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String UPDATE_QUERY = "UPDATE ticket SET " +
      "type = ?, price_in_usd = ?, concert_hall_name = ?, event_code = ?, " +
      "backpack_weight_in_kg = ?, stadium_sector = ?, time = ?, is_promo = ?, " +
      "ticket_class = ?, duration = ?, start_date = ?, user_id = ? WHERE id = ?";

  private static final String DELETE_QUERY = "DELETE FROM ticket WHERE id = ?";
  private static final String SELECT_BY_ID_QUERY = "SELECT * FROM ticket WHERE id = ?";
  private static final String SELECT_ALL_QUERY = "SELECT * FROM ticket";
  private static final String SELECT_BY_USER_ID = "SELECT * from ticket WHERE user_id = ?";

  private final DataSource dataSource;

  @Override
  public Ticket save(Ticket ticket) {
    UUID ticketId = UUID.randomUUID();
    ticket.setId(ticketId);

    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
      int lastIndex = setCommonFields(preparedStatement, ticket);

      if (ticket instanceof BusTicket busTicket) {
        lastIndex = setBusTicketFields(preparedStatement, busTicket, lastIndex);
      } else if (ticket instanceof ConcertTicket concertTicket) {
        lastIndex = setConcertTicketFields(preparedStatement, concertTicket, lastIndex);
      }

      if (ticket.getClient() != null) {
        preparedStatement.setObject(++lastIndex, ticket.getClient().getId());
      } else {
        preparedStatement.setNull(++lastIndex, Types.OTHER);
      }

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new TicketSavingErrorException(e);
    }

    return ticket;
  }

  @Override
  public void update(UUID id, Ticket ticket) {
    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
      int lastIndex = setCommonFieldsWithoutId(preparedStatement, ticket);

      if (ticket instanceof BusTicket busTicket) {
        lastIndex = setBusTicketFields(preparedStatement, busTicket, lastIndex);
      } else if (ticket instanceof ConcertTicket concertTicket) {
        lastIndex = setConcertTicketFields(preparedStatement, concertTicket, lastIndex);
      }

      if (ticket.getClient() != null) {
        preparedStatement.setObject(++lastIndex, ticket.getClient().getId());
      } else {
        preparedStatement.setNull(++lastIndex, Types.OTHER);
      }

      preparedStatement.setObject(++lastIndex, id);

      int rowsUpdated = preparedStatement.executeUpdate();
      if (rowsUpdated == 0) {
        throw new RuntimeException("Update failed: no ticket found with ID " + id);
      }
    } catch (SQLException e) {
      throw new TicketUpdatingErrorException(e);
    }
  }

  @Override
  public void delete(UUID id) {
    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
      preparedStatement.setObject(1, id);
      preparedStatement.execute();
    } catch (SQLException e) {
      throw new TicketDeletingErrorException(e);
    }
  }

  @Override
  public Ticket getById(UUID id) {
    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
      preparedStatement.setObject(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        return createTicketFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      throw new TicketReceivingByIdErrorException(e);
    }
    return null;
  }

  public Ticket getByUserId(UUID userId) {
    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID);
      preparedStatement.setObject(1, userId);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        return createTicketFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public List<Ticket> getAll() {
    List<Ticket> tickets = new ArrayList<>();
    try (Connection connection = dataSource.getConnection()) {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);

      while (resultSet.next()) {
        tickets.add(createTicketFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      throw new AllTicketsReceivingErrorException(e);
    }
    return tickets;
  }

  private int setCommonFields(PreparedStatement preparedStatement, Ticket ticket) throws SQLException {
    preparedStatement.setObject(1, ticket.getId());
    preparedStatement.setString(2, ticket.getType().name());
    preparedStatement.setBigDecimal(3, ticket.getPriceInUsd());
    return 3;
  }

  private int setCommonFieldsWithoutId(PreparedStatement preparedStatement, Ticket ticket) throws SQLException {
    preparedStatement.setString(1, ticket.getType().name());
    preparedStatement.setBigDecimal(2, ticket.getPriceInUsd());
    return 2;
  }

  private int setBusTicketFields(PreparedStatement preparedStatement, BusTicket busTicket, int lastIndex) throws SQLException {
    preparedStatement.setNull(++lastIndex, Types.VARCHAR); // concert_hall_name
    preparedStatement.setNull(++lastIndex, Types.SMALLINT); // event_code
    preparedStatement.setNull(++lastIndex, Types.FLOAT); // backpack_weight_in_kg
    preparedStatement.setNull(++lastIndex, Types.VARCHAR); // stadium_sector
    preparedStatement.setNull(++lastIndex, Types.TIMESTAMP); // time
    preparedStatement.setNull(++lastIndex, Types.BOOLEAN); // is_promo

    preparedStatement.setString(++lastIndex, busTicket.getTicketClass().name());
    preparedStatement.setString(++lastIndex, busTicket.getDuration().name());
    preparedStatement.setDate(++lastIndex, Date.valueOf(busTicket.getStartDate()));
    return lastIndex;
  }

  private int setConcertTicketFields(PreparedStatement preparedStatement, ConcertTicket concertTicket, int lastIndex) throws SQLException {
    preparedStatement.setString(++lastIndex, concertTicket.getConcertHallName());
    preparedStatement.setShort(++lastIndex, concertTicket.getEventCode());
    preparedStatement.setFloat(++lastIndex, concertTicket.getBackpackWeightInKg());
    preparedStatement.setString(++lastIndex, concertTicket.getStadiumSector().name());
    preparedStatement.setTimestamp(++lastIndex, Timestamp.valueOf(concertTicket.getTime()));
    preparedStatement.setBoolean(++lastIndex, concertTicket.getIsPromo());

    preparedStatement.setNull(++lastIndex, Types.VARCHAR); // ticket_class
    preparedStatement.setNull(++lastIndex, Types.VARCHAR); // duration
    preparedStatement.setNull(++lastIndex, Types.DATE); // start_date
    return lastIndex;
  }

  private Ticket createTicketFromResultSet(ResultSet resultSet) throws SQLException {
    TicketType type = TicketType.valueOf(resultSet.getString("type"));

    Ticket ticket = (type == TicketType.BUS)
        ? createBusTicketFromResultSet(resultSet)
        : createConcertTicketFromResultSet(resultSet);

    UUID clientId = (UUID) resultSet.getObject("user_id");
    if (clientId != null) {
      Client client = fetchClientById(clientId);
      ticket.setClient(client);
    }
    return ticket;
  }

  private BusTicket createBusTicketFromResultSet(ResultSet resultSet) throws SQLException {
    BusTicket busTicket = new BusTicket();
    busTicket.setId(UUID.fromString(resultSet.getString("id")));
    busTicket.setType(TicketType.BUS);
    busTicket.setPriceInUsd(resultSet.getBigDecimal("price_in_usd"));
    busTicket.setTicketClass(BusTicketClass.valueOf(resultSet.getString("ticket_class")));
    busTicket.setDuration(BusTicketDuration.valueOf(resultSet.getString("duration")));
    busTicket.setStartDate(resultSet.getDate("start_date").toLocalDate());
    return busTicket;
  }

  private ConcertTicket createConcertTicketFromResultSet(ResultSet resultSet) throws SQLException {
    ConcertTicket concertTicket = new ConcertTicket();
    concertTicket.setId(UUID.fromString(resultSet.getString("id")));
    concertTicket.setType(TicketType.CONCERT);
    concertTicket.setPriceInUsd(resultSet.getBigDecimal("price_in_usd"));
    concertTicket.setConcertHallName(resultSet.getString("concert_hall_name"));
    concertTicket.setEventCode(resultSet.getShort("event_code"));
    concertTicket.setBackpackWeightInKg(resultSet.getFloat("backpack_weight_in_kg"));
    concertTicket.setStadiumSector(StadiumSector.valueOf(resultSet.getString("stadium_sector")));
    concertTicket.setTime(resultSet.getTimestamp("time").toLocalDateTime());
    concertTicket.setIsPromo(resultSet.getBoolean("is_promo"));
    return concertTicket;
  }

  private Client fetchClientById(UUID clientId) throws SQLException {
    String query = "SELECT role FROM \"user\" WHERE id = ?";
    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setObject(1, clientId);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        if (Objects.equals(resultSet.getString("role"), UserRole.CLIENT.name())) {
          return new Client(clientId, UserRole.CLIENT);
        }
      }
    }
    return null;
  }
}
