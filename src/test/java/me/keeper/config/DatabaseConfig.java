package me.keeper.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

	@Bean
	public SpringLiquibase springLiquibase(DataSource dataSource) throws SQLException {
		tryToCreateSchema(dataSource);
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDropFirst(true);
		liquibase.setDataSource(dataSource);
		liquibase.setDefaultSchema("test");
		liquibase.setChangeLog("classpath:/db/changelog/master.xml");
		return liquibase;
	}

	private void tryToCreateSchema(DataSource dataSource) throws SQLException {
		String CREATE_SCHEMA_QUERY = "CREATE SCHEMA IF NOT EXISTS test";
		dataSource.getConnection().createStatement().execute(CREATE_SCHEMA_QUERY);
	}
}
