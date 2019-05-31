package io.pf.pricing.cache;

import java.util.concurrent.ThreadLocalRandom;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

public class ListinoCache {
	
	private static Cache<String, Integer> cache;

	private ListinoCache() {
		
	}
	
	private static Cache<String, Integer> getCache() {
		if (cache==null) {
			cache = Cache2kBuilder.of(String.class, Integer.class)
					.eternal(true)
			        .build();
			//TODO riempire la cache dal DB
		}
		return cache;
	}
	

	public static Integer getId(String strListinoOrdinata) {
		Integer idCombinazioneListino = ListinoCache.getCache().peek(strListinoOrdinata);
		if (idCombinazioneListino==null) {
			//TODO prelevare da DB l'idComb
			

			//SELECT IDCOMB FROM C6VI???? WHERE par1||par2||par3||par4||par5 = <strListinoOrdinata>;
			
			// per ora simulo un codice finto
			idCombinazioneListino = ThreadLocalRandom.current().nextInt(1, 501);
			
			
			
			cache.put(strListinoOrdinata, idCombinazioneListino);
		}
		return idCombinazioneListino;
	}
	
}
