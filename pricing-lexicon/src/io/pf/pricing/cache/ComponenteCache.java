package io.pf.pricing.cache;

import java.util.concurrent.ThreadLocalRandom;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import io.pf.pricing.model.IdComponente;

public class ComponenteCache {
	
	private static Cache<String, IdComponente> cache;

	private ComponenteCache() {
		
	}
	
	private static Cache<String, IdComponente> getCache() {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, IdComponente.class)
					.eternal(true)
			        .build();
			//TODO riempire la cache dal DB con tutte le confizioni della tabella C6TBCZCO
		}
		return cache;
	}
	

	public static IdComponente getId(String codiceComponente) {
		IdComponente idComponente = ComponenteCache.getCache().peek(codiceComponente);
		if (idComponente==null) {
			//TODO prelevare da DB l'idCompoente
			
			//SELECT IDCOMPO FROM C6TBCZCO WHERE CDCOMPO = <codiceComponente>;
			
			// per ora simulo un codice finto
			Integer idCompo = ThreadLocalRandom.current().nextInt(1, 2001);
			
			idComponente = new IdComponente(idCompo, 15, 2);
			
			cache.put(codiceComponente, idComponente);
		}
		return idComponente;
	}
	
}
