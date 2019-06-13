package io.pf.pricing.db;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceCondizioni {
	
	private static DriverManagerDataSource ds;
	private static JdbcTemplate jdbc;
	
	public static DataSource getDataSource() {
		if (ds==null) {
			ds = new DriverManagerDataSource();
			ds.setDriverClassName("");
			ds.setUrl("");
			ds.setUsername("");
			ds.setPassword("");
		}
		return ds;
	}
	
	public static JdbcTemplate getJdbc() {
		if (jdbc == null ) {
			jdbc = new JdbcTemplate(DataSourceCondizioni.getDataSource());
		}
		return jdbc;
	}
	

}
