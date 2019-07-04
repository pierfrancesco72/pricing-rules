package io.pf.pricing.cache;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.configuration2.Configuration;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import io.pf.pricing.db.ComponenteDao;
import io.pf.pricing.db.dto.ComponenteDto;
import io.pf.pricing.model.IdComponente;
import io.pf.pricing.utils.ConfigUtils;

public class ComponenteCache {
	
	private static final Logger log = Logger.getLogger(ComponenteCache.class.getName());
	private static Cache<String, IdComponente> cache;
	private static Configuration conf = ConfigUtils.getProperties();

	private ComponenteCache() {
		
	}
	
	private static Cache<String, IdComponente> getCache() throws SQLException {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, IdComponente.class)
					.eternal(true)
			        .build();
			
			if (conf.getBoolean("db.componenti.precaricamento", false)) {
				log.info("Precaricamento della Lista dei Componenti:");
				List<ComponenteDto> componenti = ComponenteDao.getComponenti();
				
				for (ComponenteDto cmp : componenti) {
					cache.put(cmp.getCodice(), cmp.toCache());
				}
				log.info("Caricati in cache N."+componenti.size()+" codici componente!");
			}
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
