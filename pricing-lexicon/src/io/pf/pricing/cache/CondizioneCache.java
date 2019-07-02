package io.pf.pricing.cache;

import java.sql.SQLException;
import java.util.List;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import io.pf.pricing.db.CondizioneDao;
import io.pf.pricing.db.dto.CondizioneDto;
import io.pf.pricing.model.IdCondizione;

public class CondizioneCache {
	
	private static Cache<String, IdCondizione> cache;

	private CondizioneCache() {
		
	}
	
	private static Cache<String, IdCondizione> getCache() throws SQLException {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, IdCondizione.class)
					.eternal(true)
			        .build();
			
			List<CondizioneDto> condizioni = CondizioneDao.getCondizioni();
			
			for (CondizioneDto cdz : condizioni) {
				cache.put(cdz.getCodice(), cdz.toCache());
			}
			
		}
		return cache;
	}
	

	public static IdCondizione getId(String codiceCondizione) throws SQLException {
		IdCondizione idCondizione = CondizioneCache.getCache().peek(codiceCondizione);
		if (idCondizione==null) {
			CondizioneDto cdz = CondizioneDao.getCondizione(codiceCondizione);
			
			// per ora simulo un codice finto
			//Integer idCdz = ThreadLocalRandom.current().nextInt(1, 3001);
			//Integer cdserint = 1;
			
			idCondizione = cdz.toCache();
			
			cache.put(codiceCondizione, idCondizione);
		}
		return idCondizione;
	}
	
}
