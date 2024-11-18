package org.andersen.homework.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.andersen.homework.annotation.processor.CustomAnnotationProcessor;
import org.andersen.homework.annotation.processor.NullableWarningProcessor;
import org.andersen.homework.exception.bus_ticket.BusTicketPriceIsNullException;
import org.andersen.homework.exception.bus_ticket.BusTicketPriceNotEvenException;
import org.andersen.homework.exception.bus_ticket.BusTicketStartDateIsInFutureException;
import org.andersen.homework.exception.bus_ticket.BusTicketStartDateIsNullWhenTicketTypeIsDayWeekOrYearException;
import org.andersen.homework.exception.bus_ticket.UndefinedBusTicketTypeException;
import org.andersen.homework.model.entity.ticket.BusTicket;
import org.andersen.homework.model.enums.BusTicketType;

public class ValidationManager {

  private final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
  private final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

  private final List<CustomAnnotationProcessor> customProcessors = new ArrayList<>();

  public ValidationManager() {
    customProcessors.add(new NullableWarningProcessor());
  }

  public <T> void validate(T object) {
    Set<ConstraintViolation<T>> violations = VALIDATOR.validate(object);
    if (!violations.isEmpty()) {
      throw new IllegalArgumentException("Validation failed: " + violations);
    }

    Class<?> clazz = object.getClass();
    while (clazz != null && clazz != Object.class) {
      for (Field field : clazz.getDeclaredFields()) {
        for (CustomAnnotationProcessor processor : customProcessors) {
          try {
            processor.process(object, field);
          } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access field: " + field.getName(), e);
          }
        }
      }
      clazz = clazz.getSuperclass();
    }
  }

  public void validateBusTicket(BusTicket busTicket) throws BusTicketPriceIsNullException,
      UndefinedBusTicketTypeException, BusTicketStartDateIsNullWhenTicketTypeIsDayWeekOrYearException,
      BusTicketStartDateIsInFutureException, BusTicketPriceNotEvenException {
    if (busTicket.getType() != null) {
      if (!Arrays.stream(BusTicketType.values())
          .map(BusTicketType::name).toList().contains(busTicket.getType())) {
        throw new UndefinedBusTicketTypeException();
      }
      if (!busTicket.getType().equals(BusTicketType.MONTH.name())
          && busTicket.getStartDate() == null) {
        throw new BusTicketStartDateIsNullWhenTicketTypeIsDayWeekOrYearException();
      }
    }
    if (busTicket.getPriceInUsd() == null) {
      throw new BusTicketPriceIsNullException();
    }
    if (busTicket.getStartDate() != null && busTicket.getStartDate().isAfter(LocalDate.now())) {
      throw new BusTicketStartDateIsInFutureException();
    }
    if (busTicket.getPriceInUsd() != null && busTicket.getPriceInUsd() < 0) {
      throw new BusTicketPriceNotEvenException();
    }
  }
}
