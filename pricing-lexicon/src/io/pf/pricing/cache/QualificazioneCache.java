package io.pf.pricing.cache;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

public class QualificazioneCache {
	
	private static Cache<String, Integer> cache;

	private QualificazioneCache() {
		
	}
	
	private static Cache<String, Integer> getCache() {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, Integer.class)
					.eternal(true)
			        .build();
			//TODO riempire la cache dal DB
		}
		return cache;
	}
	

	public static Integer getId(String strQualificazioneOrdinata) {
		Integer idQualificazione = QualificazioneCache.getCache().peek(strQualificazioneOrdinata);
		if (idQualificazione==null) {
			//TODO prelevare da DB l'idQualif
			cache.put(strQualificazioneOrdinata, idQualificazione);
		}
		return idQualificazione;
	}
	
}
