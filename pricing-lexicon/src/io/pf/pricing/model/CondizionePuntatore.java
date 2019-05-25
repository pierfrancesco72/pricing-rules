package io.pf.pricing.model;

import io.pf.pricing.cache.ComponenteCache;
import io.pf.pricing.cache.CondizioneCache;

public class CondizionePuntatore {

	private Integer idServizio;
	private Integer idCondizione;
	private Integer idComponente;
	private Integer idQualificazione;
	private Integer idCombinazioneListino;
	private Integer idConvenzione;
	private Integer idOggettoRapporto;
	
	
	public CondizionePuntatore(String codiceCondizione) {
		idCondizione = CondizioneCache.getId(codiceCondizione);
	}
	
	public CondizionePuntatore(String codiceCondizione, String codiceComponente) {
		this(codiceCondizione);
		idComponente = ComponenteCache.getId(codiceComponente);
	}


	public Integer getIdServizio() {
		return idServizio;
	}


	public void setIdServizio(Integer idServizio) {
		this.idServizio = idServizio;
	}


	public Integer getIdCondizione() {
		return idCondizione;
	}


	public void setIdCondizione(Integer idCondizione) {
		this.idCondizione = idCondizione;
	}


	public Integer getIdComponente() {
		return idComponente;
	}


	public void setIdComponente(Integer idComponente) {
		this.idComponente = idComponente;
	}


	public Integer getIdQualificazione() {
		return idQualificazione;
	}


	public void setIdQualificazione(Integer idQualificazione) {
		this.idQualificazione = idQualificazione;
	}


	public Integer getIdCombinazioneListino() {
		return idCombinazioneListino;
	}


	public void setIdCombinazioneListino(Integer idCombinazioneListino) {
		this.idCombinazioneListino = idCombinazioneListino;
	}


	public Integer getIdConvenzione() {
		return idConvenzione;
	}


	public void setIdConvenzione(Integer idConvenzione) {
		this.idConvenzione = idConvenzione;
	}


	public Integer getIdOggettoRapporto() {
		return idOggettoRapporto;
	}


	public void setIdOggettoRapporto(Integer idOggettoRapporto) {
		this.idOggettoRapporto = idOggettoRapporto;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CondizionePuntatore [idServizio=");
		builder.append(idServizio);
		builder.append(", idCondizione=");
		builder.append(idCondizione);
		if (idComponente!=null) {
			builder.append(", idComponente=");
			builder.append(idComponente);
		}
		if (idQualificazione!=null) {
			builder.append(", idQualificazione=");
			builder.append(idQualificazione);
		}
		if (idCombinazioneListino!=null) {
			builder.append(", idCombinazioneListino=");
			builder.append(idCombinazioneListino);
		}
		if (idConvenzione!=null) {
			builder.append(", idConvenzione=");
			builder.append(idConvenzione);
		}
		if (idOggettoRapporto!=null) {
			builder.append(", idOggettoRapporto=");
			builder.append(idOggettoRapporto);
		}
		builder.append("]");
		return builder.toString();
	}
	
	

}