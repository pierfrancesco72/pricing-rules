package io.pf.pricing.cache;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.configuration2.Configuration;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import io.pf.pricing.db.ListinoDao;
import io.pf.pricing.db.dto.ListinoDto;
import io.pf.pricing.model.ParametriList;
import io.pf.pricing.utils.ConfigUtils;

public class ListinoCache {
	
	private static final Logger log = Logger.getLogger(ListinoCache.class.getName());
	private static Cache<ParametriList, Integer> cache;
	private static Configuration conf = ConfigUtils.getProperties();

	private ListinoCache() {
		
	}
	
	private static Cache<ParametriList, Integer> getCache() throws SQLException {
		if (cache==null) {
			cache = Cache2kBuilder.of(ParametriList.class, Integer.class)
					.eternal(true)
			        .build();
			if (conf.getBoolean("db.listino.combinazioni.precaricamento", true)) {
				log.info("Precaricamento della Lista combinazioni di Listino:");
				
				List<ListinoDto> listini = ListinoDao.getListini();
				
				for (ListinoDto comb : listini) {
					cache.put(comb.toCache(), comb.getIdCombinazione());
					log.fine(comb.toString());
				}
				log.info("Caricate in cache N."+listini.size()+" codici condizione!");
			}
			
		}
		return cache;
	}
	

	public static Integer getId(String strListinoOrdinata) throws SQLException {
		ParametriList pars = new ParametriList(strListinoOrdinata);
		
		Integer idCombinazioneListino = ListinoCache.getCache().peek(pars);
		
		if (idCombinazioneListino==null) {
			
			ListinoDto dto = ListinoDao.getListino(strListinoOrdinata);
			idCombinazioneListino = dto.getIdCombinazione();
			
			cache.put(dto.toCache(), idCombinazioneListino);
		}
		return idCombinazioneListino;
	}
	
}
