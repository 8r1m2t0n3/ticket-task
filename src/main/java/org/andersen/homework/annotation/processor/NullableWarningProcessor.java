package org.andersen.homework.annotation.processor;

import java.lang.reflect.Field;
import org.andersen.homework.annotation.NullableWarning;

public class NullableWarningProcessor implements CustomAnnotationProcessor {

  @Override
  public void process(Object object, Field field) throws IllegalAccessException {
    if (field.isAnnotationPresent(NullableWarning.class)) {
      field.setAccessible(true);
      if (field.get(object) == null) {
        String message = String.format(
            field.getAnnotation(NullableWarning.class).message(),
            field.getName(),
            object.getClass().getSimpleName()
        );
        System.out.println(message);
      }
    }
  }
}
