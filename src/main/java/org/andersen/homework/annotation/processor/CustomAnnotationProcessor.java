package org.andersen.homework.annotation.processor;

import java.lang.reflect.Field;

public interface CustomAnnotationProcessor {

  void process(Object object, Field field) throws IllegalAccessException;
}
