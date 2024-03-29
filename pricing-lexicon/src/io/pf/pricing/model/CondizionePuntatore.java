package io.pf.pricing.model;

import java.sql.SQLException;

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
	
	
	private CondizionePuntatore() {
		idServizio = 0;
		idCondizione = 0;
		idComponente = 0;
		idQualificazione = 0;
		idCombinazioneListino = 0;
		idConvenzione = 0;
		idOggettoRapporto = 0;
	}
	
	public CondizionePuntatore(String codiceCondizione) throws SQLException {
		this();
		IdCondizione idCdz = CondizioneCache.getId(codiceCondizione);
		idCondizione = idCdz.getId();
		idServizio = idCdz.getIdServizioDefault();
	}
	
	public CondizionePuntatore(String codiceCondizione, String codiceComponente) throws SQLException  {
		this(codiceCondizione);
		//IdCondizione idCdz = CondizioneCache.getId(codiceCondizione);
		IdComponente idCmp = ComponenteCache.getId(codiceComponente, idCondizione);
		idComponente = idCmp.getId();
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
	public boolean equals(Object obj) {
		if (obj==null)
			return false;
		CondizionePuntatore p = (CondizionePuntatore)obj;
		
		return p.idServizio.equals(idServizio)
				&& p.getIdCondizione().equals(idCondizione)
				&& p.getIdComponente().equals(idComponente)
				&& p.getIdQualificazione().equals(idQualificazione)
				&& p.getIdCombinazioneListino().equals(idCombinazioneListino)
				&& p.getIdConvenzione().equals(idConvenzione)
				&& p.getIdOggettoRapporto().equals(idOggettoRapporto);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CondizionePuntatore [idServizio=");
		builder.append(idServizio);
		builder.append(", idCondizione=");
		builder.append(idCondizione);
		if (idComponente!=0) {
			builder.append(", idComponente=");
			builder.append(idComponente);
		}
		if (idQualificazione!=0) {
			builder.append(", idQualificazione=");
			builder.append(idQualificazione);
		}
		if (idCombinazioneListino!=0) {
			builder.append(", idCombinazioneListino=");
			builder.append(idCombinazioneListino);
		}
		if (idConvenzione!=0) {
			builder.append(", idConvenzione=");
			builder.append(idConvenzione);
		}
		if (idOggettoRapporto!=0) {
			builder.append(", idOggettoRapporto=");
			builder.append(idOggettoRapporto);
		}
		builder.append("]");
		return builder.toString();
	}
	
	

}
