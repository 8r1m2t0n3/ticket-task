package org.andersen.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TickerService {

  private final List<Ticket> TICKETS_LIST = new ArrayList<>();

  public Ticket createLimitedTicket(String concertHallName, Short eventCode) {
    concertHallNameValidation(concertHallName);
    eventCodeValidation(eventCode);
    return new Ticket(concertHallName, eventCode);
  }

  public Ticket createFullTicket(Short id, Float priceInUsd, String concertHallName,
      Short eventCode, Boolean isPromo, Character stadiumSector, Float backpackWeightInKg) {
    idValidation(id);
    concertHallNameValidation(concertHallName);
    eventCodeValidation(eventCode);
    stadiumSectorValidation(stadiumSector);
    return new Ticket(id, priceInUsd, concertHallName, eventCode, isPromo, stadiumSector,
        backpackWeightInKg);
  }

  public Optional<Ticket> findById(Short id) {
    return TICKETS_LIST.stream().filter(o -> o.getId().equals(id)).findFirst();
  }

  public List<Ticket> findByStadiumSector(Character stadiumSector) {
    return TICKETS_LIST.stream()
        .filter(o -> o.getStadiumSector().equals(stadiumSector))
        .collect(Collectors.toList());
  }

  public void fillTicketsList() {
    IntStream.range(0, 10)
        .forEach(
            n -> TICKETS_LIST.add(
                createFullTicket(
                    (short) n,
                    ((float) Randomizer.getRandomInt(10, 1000)) / 100,
                    Randomizer.getRandomString(Randomizer.getRandomInt(1, 10)),
                    (short) Randomizer.getRandomInt(0, 999),
                    Randomizer.getRandomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE,
                    (char) (Randomizer.getRandomInt(0, 2) + 'A'),
                    ((float) Randomizer.getRandomInt(10, 1000)) / 100)));
  }

  private void idValidation(Short id) {
    if (id > 9999) {
      throw new IllegalArgumentException("Id value consists of more than 4 digits.");
    }
  }

  private void stadiumSectorValidation(Character stadiumSector) {
    if (stadiumSector < 'A' || stadiumSector > 'C') {
      throw new IllegalArgumentException("Stadium Sector value is not 'A', 'B' or 'C'.");
    }
  }

  private void concertHallNameValidation(String concertHallName) {
    if (concertHallName.length() > 10) {
      throw new IllegalArgumentException("Concert Hall Name consists of more than 10 characters.");
    }
  }

  private void eventCodeValidation(Short eventCode) {
    if (eventCode > 999) {
      throw new IllegalArgumentException("Event Code consists of more than 3 digits.");
    }
  }
}
