package io.pf.pricing.cache;

import java.util.concurrent.ThreadLocalRandom;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

public class CondizioneCache {
	
	private static Cache<String, Integer> cache;

	private CondizioneCache() {
		
	}
	
	private static Cache<String, Integer> getCache() {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, Integer.class)
					.eternal(true)
			        .build();
			//TODO riempire la cache dal DB con tutte le confizioni della tabella C6TBCDZI
		}
		return cache;
	}
	

	public static Integer getId(String codiceCondizione) {
		Integer idCondizione = CondizioneCache.getCache().peek(codiceCondizione);
		if (idCondizione==null) {
			//TODO prelevare da DB l'idCondizione
			//SELECT IDCDZ FROM C6TBCDZI WHERE DESCRBR = <codiceCondzione>;
			
			// per ora simulo un codice finto
			idCondizione = ThreadLocalRandom.current().nextInt(1, 3001);
			
			cache.put(codiceCondizione, idCondizione);
		}
		return idCondizione;
	}
	
}
