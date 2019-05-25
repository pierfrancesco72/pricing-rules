package io.pf.pricing.model;

import java.util.Arrays;

import io.pf.pricing.cache.ListinoCache;

public class Listino {
	
	//ivate Map<String,String> parametri;
	private Integer idListino;
	private String strListinoOrdinata;

	/**
	 * 
	 * @param combinazione PRODOTTO=CONTOPIU,PROMOZIONE=BASE,OPZIONE=BASE (fino a 5 parametri)
	 */
	public Listino(String strListino) {
		try {
			
			String[] qq = strListino.split("\\,");
			Arrays.sort(qq);
			strListinoOrdinata = Arrays.toString(qq);
			
			/*
			parametri = new HashMap<>();
			String[] qq = strListino.split("\\,");
			for (String q : qq) {
				parametri.put(StringUtils.getKey(q), StringUtils.getValue(q));
			}
			
			StringBuffer sb = new StringBuffer();
			
			parametri.entrySet().stream()
		    	.sorted(Map.Entry.comparingByKey())
		    	.forEachOrdered(par -> sb
		    			.append(StringUtils.getKeyValue(par.getKey(), par.getValue()))
		    			.append(','));
			
			strListinoOrdinata = sb.toString();
			*/
			
			//TODO set mappa listino
			
		} catch (Exception e) {
			throw new RuntimeException("Errore nella decodificazione dei parametri di Listino: "+strListino);
		}
	}
	

	
	public Integer getIdListino() {
		if (idListino==null)
			idListino = ListinoCache.getId(strListinoOrdinata);
		return idListino;
	}

	@Override
	public String toString() {
		return strListinoOrdinata;
	}
	
	

}
