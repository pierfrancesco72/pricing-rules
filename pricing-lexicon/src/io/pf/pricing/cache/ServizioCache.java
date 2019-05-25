package io.pf.pricing.cache;

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
			
			
			
			
			cache.put(codiceServizio, idServizio);
		}
		return idServizio;
	}
	
}
