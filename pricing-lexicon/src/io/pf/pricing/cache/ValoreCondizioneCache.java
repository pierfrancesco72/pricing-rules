package io.pf.pricing.cache;

import java.util.concurrent.TimeUnit;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import io.pf.pricing.model.CondizionePuntatore;

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
	 */
	public static Object getValore(CondizionePuntatore cdz) {
		Object val = ValoreCondizioneCache.getCache().peek(cdz);
		if (val==null) {
			
			Float nrval = null;
			String dsval = null;
			
			//TODO prelevare da DB 
			if (cdz.getIdOggettoRapporto()>0) {
				//SELECT DSVAL, NRVAL FROM C6TBVAOG WHERE IDCDZ=? and IDCOMPO=? and IDQUALIF=? and CDSERINT=?;
				dsval = "CIAO";
			} else if (cdz.getIdConvenzione()>0) {
				//SELECT DSVAL, NRVAL FROM C6TBVACN WHERE IDCDZ=? and IDCOMPO=? and IDQUALIF=? and CDSERINT=? and CDCONV=?;
			} else if (cdz.getIdCombinazioneListino()>0) {
				//SELECT DSVAL, NRVAL FROM C6TBVA?? WHERE IDCDZ=? and IDCOMPO=? and IDQUALIF=? and CDSERINT=? and CDCOMB=?;
				nrval = 15.65F;
			} else {
				//SELECT DSVAL, NRVAL FROM C6TBVA?? WHERE IDCDZ=? and IDCOMPO=? and IDQUALIF=? and CDSERINT=?;
				nrval = 18.50F;
			}
			
			if (nrval != null) {
				cache.put(cdz, nrval);
				val = nrval;
			} else if (dsval != null) {
				cache.put(cdz, dsval);
				val = dsval;
			} else
				throw new RuntimeException("Condizione non trovata, con i seguenti parametri: "+cdz.toString());
		}
		return val;
	}
	
}
