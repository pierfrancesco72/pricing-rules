package io.pf.pricing.model;

public class Condizione extends CondizionePuntatore {
	
	private String servizio;
	private String codiceCondizione;
	private String codiceComponente;
	private Qualificazione qualificazione;
	private Listino listino;
	private String convenzione;
	private String rapporto;
	
	
	
	
	public Condizione(String codice, String componente) {
		super(codice, componente);
		this.codiceCondizione = codice;
		this.codiceComponente = componente;
	}
	
	public Condizione(String codice, String componente, String strQualificazione) {
		this(codice, componente);
		qualificazione = new Qualificazione(strQualificazione);
	}

	
	public Object caricaValore () {
		//TODO reperimento del valore dal DB2
		
		// metodo fake
		return 2.0;
		
	}
	
	public String getCodiceCondizione() {
		return codiceCondizione;
	}

	public void setCodiceCondizione(String codiceCondizione) {
		this.codiceCondizione = codiceCondizione;
	}

	public String getCodiceComponente() {
		return codiceComponente;
	}

	public void setCodiceComponente(String codiceComponente) {
		this.codiceComponente = codiceComponente;
	}

	public Qualificazione getQualificazione() {
		return qualificazione;
	}

	public void setQualificazione(Qualificazione qualificazione) {
		this.qualificazione = qualificazione;
	}

	public Listino getListino() {
		return listino;
	}

	public void setListino(Listino listino) {
		this.listino = listino;
	}

	public String getConvenzione() {
		return convenzione;
	}

	public void setConvenzione(String convenzione) {
		this.convenzione = convenzione;
	}

	public String getRapporto() {
		return rapporto;
	}

	public void setRapporto(String rapporto) {
		this.rapporto = rapporto;
	}

	public String getServizio() {
		return servizio;
	}

	public void setServizio(String servizio) {
		this.servizio = servizio;
	}
	

}
