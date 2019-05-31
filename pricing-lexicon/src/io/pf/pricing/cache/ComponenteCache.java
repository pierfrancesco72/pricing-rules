package io.pf.pricing.cache;

import java.util.concurrent.ThreadLocalRandom;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

public class ComponenteCache {
	
	private static Cache<String, Integer> cache;

	private ComponenteCache() {
		
	}
	
	private static Cache<String, Integer> getCache() {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, Integer.class)
					.eternal(true)
			        .build();
			//TODO riempire la cache dal DB con tutte le confizioni della tabella C6TBCZCO
		}
		return cache;
	}
	

	public static Integer getId(String codiceComponente) {
		Integer idComponente = ComponenteCache.getCache().peek(codiceComponente);
		if (idComponente==null) {
			//TODO prelevare da DB l'idCompoente
			
			//SELECT IDCOMPO FROM C6TBCZCO WHERE CDCOMPO = <codiceComponente>;
			
			// per ora simulo un codice finto
			idComponente = ThreadLocalRandom.current().nextInt(1, 2001);
			
			cache.put(codiceComponente, idComponente);
		}
		return idComponente;
	}
	
}
