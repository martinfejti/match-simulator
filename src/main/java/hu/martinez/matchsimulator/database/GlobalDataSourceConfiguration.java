package hu.martinez.matchsimulator.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class GlobalDataSourceConfiguration {

    @Bean
    public DriverManagerDataSource dataSource() {

        var driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.sqlite.JDBC");
        driverManagerDataSource.setUrl("jdbc:sqlite:src/main/resources/database/selector/selector.db");

        return driverManagerDataSource;
    }

}
