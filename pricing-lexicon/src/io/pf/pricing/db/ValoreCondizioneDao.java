package io.pf.pricing.db;

import java.sql.SQLException;

import org.apache.commons.configuration2.Configuration;

import io.pf.pricing.db.dto.ValoreDto;
import io.pf.pricing.model.CondizionePuntatore;
import io.pf.pricing.utils.ConfigUtils;


public class ValoreCondizioneDao {
	
	private static Configuration conf = ConfigUtils.getProperties();

	
	public static ValoreDto getValoreIstituto(CondizionePuntatore pk) throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject(conf.getString("db.valore.istituto"), new ValoreDto(), 
						pk.getIdCondizione(), pk.getIdComponente(), pk.getIdQualificazione());
	}
	
	public static ValoreDto getValoreListino(CondizionePuntatore pk) throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject(conf.getString("db.valore.listino"), new ValoreDto(), 
						pk.getIdCombinazioneListino(), pk.getIdCondizione(), pk.getIdComponente(), pk.getIdQualificazione());
	}
	
	public static ValoreDto getValoreConvenzione(CondizionePuntatore pk) throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject(conf.getString("db.valore.convenzione"), new ValoreDto(), 
						pk.getIdConvenzione(), pk.getIdCondizione(), pk.getIdComponente(), pk.getIdQualificazione());
	}
	
	public static ValoreDto getValoreOggetto(CondizionePuntatore pk) throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject(conf.getString("db.valore.oggetto"), new ValoreDto(), 
						pk.getIdOggettoRapporto(), pk.getIdCondizione(), pk.getIdComponente(), pk.getIdQualificazione());
	}

}
