package org.andersen.homework.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

public class ValidationUtil {

  private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
  private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

  public static <T> void validate(T object) {
    Set<ConstraintViolation<T>> violations = VALIDATOR.validate(object);
    if (!violations.isEmpty()) {
      throw new IllegalArgumentException("Validation failed: " + violations.toString());
    }
  }
}
