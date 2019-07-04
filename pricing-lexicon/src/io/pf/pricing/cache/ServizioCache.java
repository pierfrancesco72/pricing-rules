package io.pf.pricing.cache;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.configuration2.Configuration;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import io.pf.pricing.db.ServizioDao;
import io.pf.pricing.db.dto.ServizioDto;
import io.pf.pricing.utils.ConfigUtils;

public class ServizioCache {
	
	private static final Logger log = Logger.getLogger(ServizioCache.class.getName());
	private static Cache<String, Integer> cache;
	private static Configuration conf = ConfigUtils.getProperties();

	private ServizioCache() {
		
	}
	
	private static Cache<String, Integer> getCache() throws SQLException {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, Integer.class)
					.eternal(true)
			        .build();
			
			if (conf.getBoolean("db.servizi.precaricamento", true)) {
				log.info("Precaricamento della Lista dei Servizi:");
				List<ServizioDto> servizi = ServizioDao.getServizi();
				
				
				for (ServizioDto s : servizi) {
					cache.put(s.getCodiceServizio(), s.getIdServizio());
				}
				
				log.info("Caricati in cache N."+servizi.size()+" codici servizio!");
			}
		}
		return cache;
	}
	
	/**
	 * 
	 * @param codiceServizio ovvero CC, CA, PD, FA ecc.
	 * @return
	 */
	public static Integer getId(String codiceServizio) throws SQLException {
		Integer idServizio = ServizioCache.getCache().peek(codiceServizio);
		if (idServizio==null) {
			
			ServizioDto dto = ServizioDao.getServizio(codiceServizio);
			cache.put(dto.getCodiceServizio(), dto.getIdServizio());
			
			idServizio = dto.getIdServizio();
			
			// per ora simulo un codice finto
			//idServizio = ThreadLocalRandom.current().nextInt(1, 11);
			//cache.put(codiceServizio, idServizio);
		}
		return idServizio;
	}
	
}
