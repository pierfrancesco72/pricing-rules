package io.pf.pricing.cache;

import java.util.concurrent.ThreadLocalRandom;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import io.pf.pricing.model.IdCondizione;

public class CondizioneCache {
	
	private static Cache<String, IdCondizione> cache;

	private CondizioneCache() {
		
	}
	
	private static Cache<String, IdCondizione> getCache() {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, IdCondizione.class)
					.eternal(true)
			        .build();
			//TODO riempire la cache dal DB con tutte le confizioni della tabella C6TBCDZI
		}
		return cache;
	}
	

	public static IdCondizione getId(String codiceCondizione) {
		IdCondizione idCondizione = CondizioneCache.getCache().peek(codiceCondizione);
		if (idCondizione==null) {
			//TODO prelevare da DB l'idCondizione
			//SELECT IDCDZ FROM C6TBCDZI WHERE DESCRBR = <codiceCondzione>;
			
			// per ora simulo un codice finto
			Integer idCdz = ThreadLocalRandom.current().nextInt(1, 3001);
			Integer cdserint = 1;
			
			idCondizione = new IdCondizione(idCdz, cdserint);
			
			cache.put(codiceCondizione, idCondizione);
		}
		return idCondizione;
	}
	
}
