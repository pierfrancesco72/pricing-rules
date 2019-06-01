package io.pf.pricing.cache;

import java.util.concurrent.ThreadLocalRandom;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

public class OggettoRapportoCache {
	
	private static Cache<String, Integer> cache;

	private OggettoRapportoCache() {
		
	}
	
	private static Cache<String, Integer> getCache() {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, Integer.class)
					.eternal(true)
			        .build();
		}
		return cache;
	}
	

	public static Integer getId(String strOggettoRapporto) {
		Integer idOggettoRapporto = OggettoRapportoCache.getCache().peek(strOggettoRapporto);
		if (idOggettoRapporto==null) {
			//TODO prelevare da DB 
			

			//SELECT IDOGGINT FROM C6TBOGCZ WHERE ....;
			
			// per ora simulo un codice finto
			idOggettoRapporto = ThreadLocalRandom.current().nextInt(1, 9000001);
			
			cache.put(strOggettoRapporto, idOggettoRapporto);
		}
		return idOggettoRapporto;
	}
	
}
