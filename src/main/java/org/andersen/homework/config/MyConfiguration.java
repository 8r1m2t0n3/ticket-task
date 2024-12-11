package org.andersen.homework.config;

import org.andersen.homework.util.DummyBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

  @Bean
  @ConditionalOnProperty(name = "dummy.bean.creation.enabled", havingValue = "true")
  public DummyBean thisIsMyFirstConditionalBean() {
    return new DummyBean();
  }
}
