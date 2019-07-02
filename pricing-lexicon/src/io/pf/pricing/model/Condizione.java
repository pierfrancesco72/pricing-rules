package io.pf.pricing.model;

import java.sql.SQLException;
import java.util.logging.Logger;

import io.pf.pricing.cache.ConvenzioneCache;
import io.pf.pricing.cache.OggettoRapportoCache;
import io.pf.pricing.cache.ServizioCache;
import io.pf.pricing.cache.ValoreCondizioneCache;

public class Condizione {
	
	private static final Logger log = Logger.getLogger(Condizione.class.getName());
	
	private String servizio;
	private String codiceCondizione;
	private String codiceComponente;
	private Qualificazione qualificazione;
	private Listino listino;
	private String convenzione;
	private String rapporto;
	private CondizionePuntatore puntatore;
	
	
	
	public Condizione(String codice, String componente) throws SQLException  {
		log.fine("Condizione da cercare: "+codice+", compoente: "+componente);
		puntatore = new CondizionePuntatore(codice, componente);
		this.codiceCondizione = codice;
		this.codiceComponente = componente;
	}
	
	public Condizione(String codice, String componente, String strQualificazione) throws SQLException  {
		this(codice, componente);
		setQualificazione(new Qualificazione(strQualificazione));
	}

	
	public Object caricaValore () {
		return ValoreCondizioneCache.getValore(puntatore);
	}
	
	public String getCodiceCondizione() {
		return codiceCondizione;
	}

	public String getCodiceComponente() {
		return codiceComponente;
	}

	public Qualificazione getQualificazione() {
		return qualificazione;
	}

	public void setQualificazione(Qualificazione qualificazione) {
		this.qualificazione = qualificazione;
		puntatore.setIdQualificazione(qualificazione.getIdQUalificazione());
	}

	public Listino getListino() {
		return listino;
	}

	public void setListino(Listino listino) {
		this.listino = listino;
		puntatore.setIdCombinazioneListino(listino.getIdListino());
	}

	public String getConvenzione() {
		return convenzione;
	}

	public void setConvenzione(String convenzione) {
		this.convenzione = convenzione;
		puntatore.setIdConvenzione(ConvenzioneCache.getId(convenzione));
	}

	public String getRapporto() {
		return rapporto;
	}

	public void setRapporto(String rapporto) {
		this.rapporto = rapporto;
		puntatore.setIdOggettoRapporto(OggettoRapportoCache.getId(rapporto));
	}

	public String getServizio() {
		return servizio;
	}

	public void setServizio(String codiceServizio) throws SQLException {
		this.servizio = codiceServizio;
		puntatore.setIdServizio(ServizioCache.getId(codiceServizio));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Condizione [");
		if (servizio != null) {
			builder.append("servizio=");
			builder.append(servizio);
			builder.append(", ");
		}
		if (codiceCondizione != null) {
			builder.append("codiceCondizione=");
			builder.append(codiceCondizione);
			builder.append(", ");
		}
		if (codiceComponente != null) {
			builder.append("codiceComponente=");
			builder.append(codiceComponente);
			builder.append(", ");
		}
		if (qualificazione != null) {
			builder.append("qualificazione=");
			builder.append(qualificazione);
			builder.append(", ");
		}
		if (listino != null) {
			builder.append("listino=");
			builder.append(listino);
			builder.append(", ");
		}
		if (convenzione != null) {
			builder.append("convenzione=");
			builder.append(convenzione);
			builder.append(", ");
		}
		if (rapporto != null) {
			builder.append("rapporto=");
			builder.append(rapporto);
			builder.append(", ");
		}
		if (puntatore != null) {
			builder.append("puntatore=");
			builder.append(puntatore);
		}
		builder.append("]");
		return builder.toString();
	}
	

}
