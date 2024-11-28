package org.andersen.homework.config;

import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "org.andersen.homework")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class ApplicationContextConfig {

  private static final String TICKET_AND_USER_TABLES_CREATION_SQL_FILE = "db/sql/ticket_and_user_tables_creation.sql";

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String dbUser;

  @Value("${spring.datasource.password}")
  private String dbPassword;

  @Bean
  public DataSource dataSource() {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setUser(dbUser);
    dataSource.setPassword(dbPassword);
    dataSource.setURL(dbUrl);
    return dataSource;
  }

  @Bean
  public boolean initializeDatabase() {
    new ResourceDatabasePopulator(new ClassPathResource(TICKET_AND_USER_TABLES_CREATION_SQL_FILE)).execute(dataSource());
    return Boolean.TRUE; // dummy value
  }
}
