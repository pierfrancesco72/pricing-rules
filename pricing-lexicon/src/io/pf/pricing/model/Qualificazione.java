package io.pf.pricing.model;

import java.sql.SQLException;
import java.util.Arrays;

import io.pf.pricing.cache.QualificazioneCache;

public class Qualificazione {
	
	private Integer idQUalificazione;
	private String strQualificazioneOrdinata;

	/**
	 * 
	 * @param qualificazione CANALE=WEB,CARTA=MASTERCARD (fino a 10 qualificatori)
	 */
	public Qualificazione(String strQualificazione) {
		
		try {
			strQualificazione=strQualificazione.substring(strQualificazione.indexOf("(")+1, strQualificazione.length()-1);
			String[] qq = strQualificazione.split("\\,");
			Arrays.sort(qq);
			strQualificazioneOrdinata = String.join(",", qq);
			
		} catch (Exception e) {
			throw new RuntimeException("Errore nella decodificazione della Qualificazione: "+strQualificazione);
		}
	}
	

	
	public Integer getIdQualificazione(Integer idServizio) throws SQLException {
		if (idQUalificazione==null)
			idQUalificazione = QualificazioneCache.getId(strQualificazioneOrdinata, idServizio);
		return idQUalificazione;
	}

	@Override
	public String toString() {
		return strQualificazioneOrdinata;
	}
	
	

}
