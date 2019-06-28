package io.pf.pricing.db;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.configuration2.Configuration;

import io.pf.pricing.db.dto.ServizioDto;
import io.pf.pricing.utils.ConfigUtils;


public class ServizioDao {

	private static Configuration conf = ConfigUtils.getProperties();
	
	private ServizioDao() {
		
	}
	
	public static List<ServizioDto> getServizi() throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.query(conf.getString("db.servizi"), new ServizioDto());
	}
	
	
	public static ServizioDto getServizio(String codiceServizio) throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject(conf.getString("db.servizio"), ServizioDto.class, codiceServizio);
	}

}
