package io.pf.pricing.cache;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import io.pf.pricing.db.ServizioDao;
import io.pf.pricing.db.dto.ServizioDto;

public class ServizioCache {
	
	private static final Logger log = Logger.getLogger(ServizioCache.class.getName());
	private static Cache<String, Integer> cache;

	private ServizioCache() {
		
	}
	
	private static Cache<String, Integer> getCache() {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, Integer.class)
					.eternal(true)
			        .build();
			
			List<ServizioDto> servizi = null;
			try {
				servizi = ServizioDao.getServizi();
			} catch (SQLException e) {
				log.severe(ExceptionUtils.getStackTrace(e));
				cache.put("CC", 1);
				cache.put("CA", 2);
				cache.put("FA", 3);
				cache.put("PD", 4);
				cache.put("PP", 5);
			}
			
			for (ServizioDto s : servizi) {
				cache.put(s.getCodiceServizio(), s.getIdServizio());
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
			
			// per ora simulo un codice finto
			idServizio = ThreadLocalRandom.current().nextInt(1, 11);
			cache.put(codiceServizio, idServizio);
		}
		return idServizio;
	}
	
}
