package io.pf.pricing.db;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.configuration2.Configuration;

import io.pf.pricing.db.dto.CondizioneDto;
import io.pf.pricing.utils.ConfigUtils;


public class CondizioneDao {
	
	private static Configuration conf = ConfigUtils.getProperties();

	private CondizioneDao() {
		
	}
	
	public static List<CondizioneDto> getCondizioni() throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.query(conf.getString("db.condizioni"), new CondizioneDto());
	}
	
	
	public static CondizioneDto getCondizione(String codiceCondizione) throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject(conf.getString("db.condizione"), new CondizioneDto(), codiceCondizione);
	}

}
