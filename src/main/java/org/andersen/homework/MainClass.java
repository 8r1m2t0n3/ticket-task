package org.andersen.homework;

import org.andersen.homework.config.ApplicationContextConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainClass {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
  }
}
