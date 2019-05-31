package io.pf.pricing.cache;

import java.util.concurrent.ThreadLocalRandom;

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
			
			//SELECT IDQUALIF FROM C7VI???? WHERE q1||q2||q3||q4||q5|q6||q7||q8||q9||q10 = <strQualificazioneOrdinata>;
			// and idServizio = <idServizio>
			
			// per ora simulo un codice finto
			idQualificazione = ThreadLocalRandom.current().nextInt(1, 501);
			
			
			
			cache.put(strQualificazioneOrdinata, idQualificazione);
		}
		return idQualificazione;
	}
	
}
