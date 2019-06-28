package io.pf.pricing.db;

import javax.sql.DataSource;

import org.apache.commons.configuration2.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import io.pf.pricing.utils.ConfigUtils;

public class DataSourceCondizioni {
	
	private static DriverManagerDataSource ds;
	private static JdbcTemplate jdbc;
	private static Configuration conf = ConfigUtils.getProperties();
	
	public static DataSource getDataSource() {
		if (ds==null) {
			ds = new DriverManagerDataSource();
			ds.setDriverClassName(conf.getString("datasource.driverClassName"));
			ds.setUrl(conf.getString("datasource.url"));
			ds.setUsername(conf.getString("datasource.username"));
			ds.setPassword(conf.getString("datasource.password"));
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
