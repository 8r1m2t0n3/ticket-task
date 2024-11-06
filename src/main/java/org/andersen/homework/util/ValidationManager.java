package org.andersen.homework.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.andersen.homework.annotation.processor.CustomAnnotationProcessor;
import org.andersen.homework.annotation.processor.NullableWarningProcessor;

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

    for (Field field : object.getClass().getDeclaredFields()) {
      for (CustomAnnotationProcessor processor : customProcessors) {
        try {
          processor.process(object, field);
        } catch (IllegalAccessException e) {
          throw new RuntimeException("Failed to access field: " + field.getName(), e);
        }
      }
    }
  }
}
