package gov.usgs.wma.waterdata;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

@TestConfiguration
public class DBTestConfig {

	@Bean
	@Primary
	@ConfigurationProperties(prefix="spring.datasource-transform")
	public DataSourceProperties dataSourcePropertiesTransform() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix="spring.datasource-transform")
	public DataSource dataSourceTransform() {
		return dataSourcePropertiesTransform().initializeDataSourceBuilder().build();
	}

	@Bean
	@ConfigurationProperties(prefix="spring.datasource-observation")
	public DataSourceProperties dataSourcePropertiesObservation() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties(prefix="spring.datasource-observation")
	public DataSource dataSourceObservation() {
		return dataSourcePropertiesObservation().initializeDataSourceBuilder().build();
	}

	@Bean
	public DatabaseConfigBean dbUnitDatabaseConfig() {
		DatabaseConfigBean dbUnitDbConfig = new DatabaseConfigBean();
		dbUnitDbConfig.setDatatypeFactory(new TSDataTypeFactory());
		return dbUnitDbConfig;
	}

	@Bean
	public DatabaseDataSourceConnectionFactoryBean wqp() throws SQLException {
		DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection = new DatabaseDataSourceConnectionFactoryBean();
		dbUnitDatabaseConnection.setDatabaseConfig(dbUnitDatabaseConfig());
		dbUnitDatabaseConnection.setDataSource(dataSourceTransform());
		// TODO Pull this in from the config file
		dbUnitDatabaseConnection.setSchema("schema_name");
		return dbUnitDatabaseConnection;
	}

	@Bean
	public DatabaseDataSourceConnectionFactoryBean observation() throws SQLException {
		DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection = new DatabaseDataSourceConnectionFactoryBean();
		dbUnitDatabaseConnection.setDatabaseConfig(dbUnitDatabaseConfig());
		dbUnitDatabaseConnection.setDataSource(dataSourceObservation());
		// TODO Pull this in from the config file
		dbUnitDatabaseConnection.setSchema("nwis");
		return dbUnitDatabaseConnection;
	}
}
