package io.pf.pricing.cache;

import java.util.concurrent.ThreadLocalRandom;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

public class ServizioCache {
	
	private static Cache<String, Integer> cache;

	private ServizioCache() {
		
	}
	
	private static Cache<String, Integer> getCache() {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, Integer.class)
					.eternal(true)
			        .build();
			//TODO riempire la cache dal DB con tutte i servizi della tabella C6TBSRBA
			
			//SELECT CDSEREST, CDSERINT FROM C6TBSRBA;
			
			cache.put("CC", 1);
			cache.put("CA", 2);
			cache.put("FA", 3);
			cache.put("PD", 4);
			cache.put("PP", 5);
		}
		return cache;
	}
	
	/**
	 * 
	 * @param codiceServizio ovvero CC, CA, PD, FA ecc.
	 * @return
	 */
	public static Integer getId(String codiceServizio) {
		Integer idServizio = ServizioCache.getCache().peek(codiceServizio);
		if (idServizio==null) {
			//TODO prelevare da DB idServizio
			
			
			//SELECT CDSERINT FROM C6TBSRBA WHERE CDSEREST = <codiceServizio>;
			// and idServizio = <idServizio>
			
			// per ora simulo un codice finto
			idServizio = ThreadLocalRandom.current().nextInt(1, 11);
			
			cache.put(codiceServizio, idServizio);
		}
		return idServizio;
	}
	
}
