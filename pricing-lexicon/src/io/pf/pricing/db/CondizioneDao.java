package io.pf.pricing.db;

import java.sql.SQLException;
import java.util.List;

import io.pf.pricing.db.dto.CondizioneDto;


public class CondizioneDao {

	private CondizioneDao() {
		
	}
	
	public static List<CondizioneDto> getCondizioni() throws SQLException {
		
		return DataSourceCondizioni.getJdbc().query("select dscdzbr, idcdz, cdserint from DCLDM0C.C6TBCDZI", new CondizioneDto());
	}
	
	
	public static CondizioneDto getCondizione(String codiceCondizione) throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject("select dscdzbr, idcdz, cdresint  from DCLDM0C.C6TBCDZI where dscdzbr = :codiceCondizione", 
						CondizioneDto.class, codiceCondizione);
	}

}
