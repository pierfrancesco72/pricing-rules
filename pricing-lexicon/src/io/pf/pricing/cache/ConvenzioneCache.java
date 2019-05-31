package io.pf.pricing.cache;

import java.util.concurrent.ThreadLocalRandom;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

public class ConvenzioneCache {
	
	private static Cache<String, Integer> cache;

	private ConvenzioneCache() {
		
	}
	
	private static Cache<String, Integer> getCache() {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, Integer.class)
					.eternal(true)
			        .build();
			//TODO riempire la cache dal DB con tutti gli idConvenzione della tabella C6TBCONV ??? non ricordo bene
		}
		return cache;
	}
	
	/**
	 * 
	 * @param codiceConvenzione ovvero DIPENDENTI, ALMAVIVA, CARABINIERI ecc.
	 * @return
	 */
	public static Integer getId(String codiceConvenzione) {
		Integer idConvenzione = ConvenzioneCache.getCache().peek(codiceConvenzione);
		if (idConvenzione==null) {
			//TODO prelevare da DB idConvenzione
			
			//SELECT IDCONV FROM C6TBCONV WHERE CDCOMPO = <codiceCovenzione>;
			
			// per ora simulo un codice finto
			idConvenzione = ThreadLocalRandom.current().nextInt(1, 501);
			
			cache.put(codiceConvenzione, idConvenzione);
		}
		return idConvenzione;
	}
	
}
