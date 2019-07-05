package io.pf.pricing.cache;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import io.pf.pricing.db.ValoreCondizioneDao;
import io.pf.pricing.db.dto.ValoreDto;
import io.pf.pricing.model.CondizionePuntatore;
import io.pf.pricing.model.IdComponente;

public class ValoreCondizioneCache {
	
	private static Cache<CondizionePuntatore, Object> cache;

	private ValoreCondizioneCache() {
		
	}
	
	private static Cache<CondizionePuntatore, Object> getCache() {
		if (cache==null) {
			cache = Cache2kBuilder.of(CondizionePuntatore.class, Object.class)
					.expireAfterWrite(2, TimeUnit.HOURS)
			        .build();
		}
		return cache;
	}
	
	/**
	 * 
	 * @param codiceServizio ovvero CC, CA, PD, FA ecc.
	 * @return
	 * @throws SQLException 
	 */
	public static Object getValore(CondizionePuntatore cdz, IdComponente componente) throws SQLException {
		Object val = ValoreCondizioneCache.getCache().peek(cdz);
		if (val==null) {
			
			
			ValoreDto dto = null;
			
			if (cdz.getIdOggettoRapporto()>0) {
				dto = ValoreCondizioneDao.getValoreOggetto(cdz);
			} else if (cdz.getIdConvenzione()>0) {
				dto = ValoreCondizioneDao.getValoreConvenzione(cdz);
			} else if (cdz.getIdCombinazioneListino()>0) {
				dto = ValoreCondizioneDao.getValoreListino(cdz);
			} else {
				dto = ValoreCondizioneDao.getValoreIstituto(cdz);
			}
			dto.setComponente(componente);
			val = dto.toCache();
			cache.put(cdz, val);
		}
		return val;
	}
	
}
