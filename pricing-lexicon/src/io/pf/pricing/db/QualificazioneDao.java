package io.pf.pricing.db;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.configuration2.Configuration;

import io.pf.pricing.db.dto.QualificazioneDto;
import io.pf.pricing.utils.ConfigUtils;


public class QualificazioneDao {
	
	private static Configuration conf = ConfigUtils.getProperties();

	private QualificazioneDao() {
		
	}
	
	public static List<QualificazioneDto> getQualificazioni() throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.query(conf.getString("db.qualificazione.combinazioni"), new QualificazioneDto());
	}
	
	/**
	 * Da rivedere
	 * 
	 * 
	 * @param strQualificazione
	 * @param idServizio
	 * @return
	 * @throws SQLException
	 */
	public static QualificazioneDto getQualificazione(String strQualificazione, Integer idServizio) throws SQLException {
		
		//String[] pars = strQualificazione.split("\\,");
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject(conf.getString("db.qualificazione.combinazione"), 
						new QualificazioneDto(), strQualificazione, idServizio);
	}

}
