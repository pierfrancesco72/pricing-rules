package io.pf.pricing.cache;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

public class ListinoCache {
	
	private static Cache<String, Integer> cache;

	private ListinoCache() {
		
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
	

	public static Integer getId(String strListinoOrdinata) {
		Integer idCombinazione = ListinoCache.getCache().peek(strListinoOrdinata);
		if (idCombinazione==null) {
			//TODO prelevare da DB l'idComb
			
			
			
			
			cache.put(strListinoOrdinata, idCombinazione);
		}
		return idCombinazione;
	}
	
}
