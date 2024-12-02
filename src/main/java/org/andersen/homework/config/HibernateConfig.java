package org.andersen.homework.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "org.andersen.homework")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class HibernateConfig {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String dbUsername;

  @Value("${spring.datasource.password}")
  private String dbPassword;

  @Value("${spring.datasource.driver-class-name}")
  private String dbDriver;

  @Value("${hibernate.dialect}")
  private String hibernateDialect;

  @Value("${hibernate.show_sql}")
  private String hibernateShowSql;

  @Value("${hibernate.format_sql}")
  private String hibernateFormatSql;

  @Value("${hibernate.hbm2ddl.auto}")
  private String hibernateHbm2ddlAuto;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(dbDriver);
    dataSource.setUrl(dbUrl);
    dataSource.setUsername(dbUsername);
    dataSource.setPassword(dbPassword);
    return dataSource;
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());

    Properties hibernateProperties = new Properties();
    hibernateProperties.put("hibernate.dialect", hibernateDialect);
    hibernateProperties.put("hibernate.show_sql", hibernateShowSql);
    hibernateProperties.put("hibernate.format_sql", hibernateFormatSql);
    hibernateProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);

    sessionFactory.setPackagesToScan(
        "org.andersen.homework.model",
        "org.andersen.homework.model.entity.ticket",
        "org.andersen.homework.model.entity.user"
    );

    sessionFactory.setHibernateProperties(hibernateProperties);
    return sessionFactory;
  }

  @Bean
  public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
    return new HibernateTransactionManager(sessionFactory);
  }
}
