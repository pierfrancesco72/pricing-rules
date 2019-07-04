package io.pf.pricing.cache;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.configuration2.Configuration;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import io.pf.pricing.db.QualificazioneDao;
import io.pf.pricing.db.dto.QualificazioneDto;
import io.pf.pricing.model.ParametriList;
import io.pf.pricing.utils.ConfigUtils;

public class QualificazioneCache {
	
	private static final Logger log = Logger.getLogger(QualificazioneCache.class.getName());
	private static Cache<ParametriList, Integer> cache;
	private static Configuration conf = ConfigUtils.getProperties();

	private QualificazioneCache() {
		
	}
	
	private static Cache<ParametriList, Integer> getCache() throws SQLException {
		if (cache==null) {
			cache = Cache2kBuilder.of(ParametriList.class, Integer.class)
					.eternal(true)
			        .build();
			
			if (conf.getBoolean("db.qualificazione.combinazioni.precaricamento", true)) {
				log.info("Precaricamento della Lista combinazioni dei Qualificatori:");
				
				List<QualificazioneDto> qualificazioni = QualificazioneDao.getQualificazioni();
				
				for (QualificazioneDto comb : qualificazioni) {
					cache.put(comb.toCache(), comb.getIdQualificazione());
					log.fine(comb.toString());
					System.out.println(comb);
				}
				log.info("Caricate in cache N."+qualificazioni.size()+" qualificazioni!");
			}
			
		}
		return cache;
	}
	

	public static Integer getId(String strQualificazioneOrdinata, Integer idServizio) throws SQLException {
		
		ParametriList pars = new ParametriList(strQualificazioneOrdinata);
		
		Integer idQualificazione = QualificazioneCache.getCache().peek(pars);
		
		if (idQualificazione==null) {
			
			QualificazioneDto dto = QualificazioneDao.getQualificazione(strQualificazioneOrdinata, idServizio);
			idQualificazione = dto.getIdQualificazione();
			
			cache.put(dto.toCache(), idQualificazione);
		}
		return idQualificazione;
	}
	
}
