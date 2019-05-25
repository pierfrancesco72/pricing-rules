package io.pf.pricing.model;

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
			
			String[] qq = strQualificazione.split("\\,");
			Arrays.sort(qq);
			strQualificazioneOrdinata = Arrays.toString(qq);
			
		} catch (Exception e) {
			throw new RuntimeException("Errore nella decodificazione della Qualificazione: "+strQualificazione);
		}
	}
	

	
	public Integer getIdQUalificazione() {
		if (idQUalificazione==null)
			idQUalificazione = QualificazioneCache.getId(strQualificazioneOrdinata);
		return idQUalificazione;
	}

	@Override
	public String toString() {
		return strQualificazioneOrdinata;
	}
	
	

}
