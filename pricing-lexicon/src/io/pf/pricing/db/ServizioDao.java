package io.pf.pricing.db;

import java.sql.SQLException;
import java.util.List;

import io.pf.pricing.db.dto.ServizioDto;


public class ServizioDao {

	private ServizioDao() {
		
	}
	
	public static List<ServizioDto> getServizi() throws SQLException {
		
		return DataSourceCondizioni.getJdbc().query("select cdserest, cdserint from DCLDM0C.C6TBSRBA", new ServizioDto());
	}
	
	
	public static ServizioDto getServizio(String codiceServizio) throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject("select cdserest, cdserint from DCLDM0C.C6TBSRBA where cdserest = :codiceServizio", 
						ServizioDto.class, codiceServizio);
	}

}
