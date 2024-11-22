package org.andersen.homework.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.andersen.homework.exception.ticket.AllTicketsReceivingErrorException;
import org.andersen.homework.exception.ticket.TicketDeletingErrorException;
import org.andersen.homework.exception.ticket.TicketReceivingByIdErrorException;
import org.andersen.homework.exception.ticket.TicketSavingErrorException;
import org.andersen.homework.exception.ticket.TicketUpdatingErrorException;
import org.andersen.homework.model.dao.Dao;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.entity.ticket.ConcertTicket;
import org.andersen.homework.model.entity.ticket.Ticket;
import org.andersen.homework.model.enums.BusTicketClass;
import org.andersen.homework.model.enums.BusTicketDuration;
import org.andersen.homework.model.enums.StadiumSector;
import org.andersen.homework.model.enums.TicketType;

public class TicketDaoJdbc implements Dao<Ticket, UUID> {

  private static final String INSERT_QUERY = "INSERT INTO ticket(" +
      "id, type, price_in_usd, concert_hall_name, event_code, " +
      "backpack_weight_in_kg, stadium_sector, time, is_promo, " +
      "ticket_class, duration, start_date) " +
      "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String UPDATE_QUERY = "UPDATE ticket SET type = ?, price_in_usd = ?, " +
      "concert_hall_name = ?, event_code = ?, backpack_weight_in_kg = ?, " +
      "stadium_sector = ?, time = ?, is_promo = ?, ticket_class = ?, " +
      "duration = ?, start_date = ? WHERE id = ?";
  private static final String DELETE_QUERY = "DELETE FROM ticket WHERE id = ?";
  private static final String SELECT_BY_ID_QUERY = "SELECT * FROM ticket WHERE id = ?";
  private static final String SELECT_ALL_QUERY = "SELECT * FROM ticket";

  private final Connection connection;

  TicketDaoJdbc(Connection connection) {
    this.connection = connection;
  }

  @Override
  public Ticket save(Ticket ticket) {
    UUID ticketId = UUID.randomUUID();
    ticket.setId(ticketId);

    try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

      preparedStatement.setObject(1, ticket.getId());
      preparedStatement.setString(2, ticket.getType().name());
      preparedStatement.setBigDecimal(3, ticket.getPriceInUsd());

      if (ticket instanceof BusTicket busTicket) {
        preparedStatement.setNull(4, Types.VARCHAR); // concert_hall_name
        preparedStatement.setNull(5, Types.SMALLINT); // event_code
        preparedStatement.setNull(6, Types.FLOAT); // backpack_weight_in_kg
        preparedStatement.setNull(7, Types.VARCHAR); // stadium_sector
        preparedStatement.setNull(8, Types.TIMESTAMP); // time
        preparedStatement.setNull(9, Types.BOOLEAN); // is_promo

        preparedStatement.setString(10, busTicket.getTicketClass().name());
        preparedStatement.setString(11, busTicket.getDuration().name());
        preparedStatement.setDate(12, Date.valueOf(busTicket.getStartDate()));

      } else if (ticket instanceof ConcertTicket concertTicket) {
        preparedStatement.setString(4, concertTicket.getConcertHallName());
        preparedStatement.setShort(5, concertTicket.getEventCode());
        preparedStatement.setFloat(6, concertTicket.getBackpackWeightInKg());
        preparedStatement.setString(7, concertTicket.getStadiumSector().name());
        preparedStatement.setTimestamp(8, Timestamp.valueOf(concertTicket.getTime()));
        preparedStatement.setBoolean(9, concertTicket.getIsPromo());

        preparedStatement.setNull(10, Types.VARCHAR); // ticket_class
        preparedStatement.setNull(11, Types.VARCHAR); // duration
        preparedStatement.setNull(12, Types.DATE); // start_date
      }

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new TicketSavingErrorException(e);
    }
    return ticket;
  }

  @Override
  public void update(UUID id, Ticket ticket) {
    try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
      preparedStatement.setString(1, ticket.getType().name());
      preparedStatement.setBigDecimal(2, ticket.getPriceInUsd());

      if (ticket instanceof BusTicket busTicket) {
        preparedStatement.setNull(3, Types.VARCHAR); // concert_hall_name
        preparedStatement.setNull(4, Types.SMALLINT); // event_code
        preparedStatement.setNull(5, Types.FLOAT); // backpack_weight_in_kg
        preparedStatement.setNull(6, Types.VARCHAR); // stadium_sector
        preparedStatement.setNull(7, Types.TIMESTAMP); // time
        preparedStatement.setNull(8, Types.BOOLEAN); // is_promo

        preparedStatement.setString(9, busTicket.getTicketClass().name());
        preparedStatement.setString(10, busTicket.getDuration().name());
        preparedStatement.setDate(11, Date.valueOf(busTicket.getStartDate()));

      } else if (ticket instanceof ConcertTicket concertTicket) {
        preparedStatement.setString(3, concertTicket.getConcertHallName());
        preparedStatement.setShort(4, concertTicket.getEventCode());
        preparedStatement.setFloat(5, concertTicket.getBackpackWeightInKg());
        preparedStatement.setString(6, concertTicket.getStadiumSector().name());
        preparedStatement.setTimestamp(7, Timestamp.valueOf(concertTicket.getTime()));
        preparedStatement.setBoolean(8, concertTicket.getIsPromo());

        preparedStatement.setNull(9, Types.VARCHAR); // ticket_class
        preparedStatement.setNull(10, Types.VARCHAR); // duration
        preparedStatement.setNull(11, Types.DATE); // start_date
      }

      preparedStatement.setObject(12, id);

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
    try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
      preparedStatement.setObject(1, id);

      preparedStatement.execute();
    } catch (SQLException e) {
      throw new TicketDeletingErrorException(e);
    }
  }

  @Override
  public Ticket get(UUID id) {
    Ticket ticket = null;

    try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {

      preparedStatement.setObject(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        TicketType type = TicketType.valueOf(resultSet.getString("type"));
        ticket = createTicketFromResultSet(resultSet, type);
      }

    } catch (SQLException e) {
      throw new TicketReceivingByIdErrorException(e);
    }

    return ticket;
  }

  @Override
  public List<Ticket> getAll() {
    List<Ticket> tickets = new ArrayList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);

      while (resultSet.next()) {
        TicketType type = TicketType.valueOf(resultSet.getString("type"));
        Ticket ticket = createTicketFromResultSet(resultSet, type);
        tickets.add(ticket);
      }

    } catch (SQLException e) {
      throw new AllTicketsReceivingErrorException(e);
    }

    return tickets;
  }

  private Ticket createTicketFromResultSet(ResultSet resultSet, TicketType type) throws SQLException {
    if (type == TicketType.BUS) {
      BusTicket busTicket = new BusTicket();
      busTicket.setId(UUID.fromString(resultSet.getString("id")));
      busTicket.setType(type);
      busTicket.setPriceInUsd(resultSet.getBigDecimal("price_in_usd"));
      busTicket.setTicketClass(BusTicketClass.valueOf(resultSet.getString("ticket_class")));
      busTicket.setDuration(BusTicketDuration.valueOf(resultSet.getString("duration")));
      busTicket.setStartDate(resultSet.getDate("start_date").toLocalDate());
      return busTicket;
    } else if (type == TicketType.CONCERT) {
      ConcertTicket concertTicket = new ConcertTicket();
      concertTicket.setId(UUID.fromString(resultSet.getString("id")));
      concertTicket.setType(type);
      concertTicket.setPriceInUsd(resultSet.getBigDecimal("price_in_usd"));
      concertTicket.setConcertHallName(resultSet.getString("concert_hall_name"));
      concertTicket.setEventCode(resultSet.getShort("event_code"));
      concertTicket.setBackpackWeightInKg(resultSet.getFloat("backpack_weight_in_kg"));
      concertTicket.setStadiumSector(StadiumSector.valueOf(resultSet.getString("stadium_sector")));
      concertTicket.setTime(resultSet.getTimestamp("time").toLocalDateTime());
      concertTicket.setIsPromo(resultSet.getBoolean("is_promo"));
      return concertTicket;
    }

    return null;
  }
}
