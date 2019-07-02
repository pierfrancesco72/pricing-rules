package io.pf.pricing.cache;

import java.sql.SQLException;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import io.pf.pricing.db.ComponenteDao;
import io.pf.pricing.db.dto.ComponenteDto;
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
		}
		return cache;
	}
	

	public static IdComponente getId(String codiceComponente, Integer idCondizione) throws SQLException {
		String key = idCondizione+":"+codiceComponente;
		IdComponente idComponente = ComponenteCache.getCache().peek(key);
		if (idComponente==null) {
			
			ComponenteDto dto = ComponenteDao.getComponente(codiceComponente, idCondizione);
			
			// per ora simulo un codice finto
			//Integer idCompo = ThreadLocalRandom.current().nextInt(1, 2001);
			
			idComponente = dto.toCache();
			
			cache.put(key, idComponente);
		}
		return idComponente;
	}
	
}
