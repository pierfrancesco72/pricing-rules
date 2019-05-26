package io.pf.pricing.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Driver {
	
	private String codice;
	private BigDecimal valore;
	private String valoreStringa;
	private Boolean valoreBooleano;
	private Condizione condizione;
	
	
	/**
	 * Construttore per le tipologie dr: cdz:
	 * @param tipologia
	 * @param codice
	 */
	public Driver (String codice) {
		valore = BigDecimal.ZERO;
		valoreBooleano = false;
		int idxPrefisso = codice.indexOf(':') + 1;
		String tipologia = codice.substring(0, idxPrefisso-1);
		
		if (tipologia.equals("dr"))
			this.codice = codice.substring(idxPrefisso);
		else if (tipologia.equals("cdz"))
			this.codice = codice.substring(idxPrefisso);
		else
			throw new RuntimeException("Tipologia Operando non riconosciuta");
		
	}
	
	public Driver (String codice, Object valore) {
		this(codice);
		if (valore instanceof Number) {
			this.valore  = new BigDecimal(((Number) valore).doubleValue());
		} else if (valore instanceof Boolean) {
			valoreBooleano = (Boolean) valore;
			if (valoreBooleano)
				this.valore = BigDecimal.ONE;
		} else if (valore instanceof String) {
			valoreStringa = (String) valore;
		}
	}
	
	
	/**
	 * Costruttore solo per i valori assoluti
	 * @param numero
	 */
	public Driver(Number numero) {
		this("dr:VALORE_NUMERICO");
		valore = new BigDecimal(numero.doubleValue());
	}
	
	public Driver(Boolean bool) {
		this("dr:VALORE_BOOELANO");
		valoreBooleano = bool;
		if (bool)
			valore = BigDecimal.ONE;
	}
	

	
	/**
	 * Costruttore per tipologia cdz:
	 * @param tipologia
	 * @param codiceCondizione
	 * @param codiceComponente
	 */
	public Driver (String codiceCondizione, String codiceComponente) {
		this.codice = codiceCondizione + ":" + codiceComponente;
		condizione = new Condizione(codiceCondizione, codiceComponente);
	}

	/**
	 * Costruttore per tipologia cdz: con qualificazione
	 * @param codiceCondizione
	 * @param codiceComponente
	 * @param qualificazione
	 */
	public Driver (String codiceCondizione, String codiceComponente, String strQualificazione) {
		this(codiceCondizione, codiceComponente);
		Qualificazione q = new Qualificazione(strQualificazione);
		condizione.setQualificazione(q);
	}
	
	public void setValore(Object valore) {
		if (valore instanceof Number)
			this.valore = new BigDecimal(((Number)valore).floatValue());
		else if (valore instanceof Boolean) {
			if (((Boolean)valore).booleanValue()) 
				this.valore = new BigDecimal(1); 
			else 
				this.valore = new BigDecimal(0);
		} else if (valore instanceof String)
			this.valoreStringa = valore.toString();
	}
	
	
	public String getCodice() {
		return codice;
	}


	public BigDecimal getValore() {
		return valore;
	}
	
	public String getValoreStringa() {
		return valoreStringa;
	}

	public void setValore(BigDecimal valore) {
		this.valore = valore;
	}
	

	public Condizione getCondizione() {
		return condizione;
	}

	public Boolean getValoreBooleano() {
		return valoreBooleano;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Driver [codice=");
		builder.append(codice);
		if (valore!=null) {
			builder.append(", valore=");
			builder.append(valore);
		}
		if (valoreStringa!=null) {
			builder.append(", valoreStringa=");
			builder.append(valoreStringa);
		}
		if (valoreBooleano!=null) {
			builder.append(", valoreBooleano=");
			builder.append(valoreBooleano);
		}
		if (condizione!=null) {
			builder.append(", condizione=");
			builder.append(condizione);
		}
		builder.append("]");
		return builder.toString();
	}


	
	public static void main(String[] args) {
		Driver op = new Driver(15.95);
		op.getValore().setScale(2, RoundingMode.HALF_UP);
		System.out.println(op.getValore().toPlainString());
		System.out.println(op.getValore().toString());
		
		op.setValore(new Boolean(true));
		System.out.println(op.getValore().toString());
		op.setValore(new Boolean(false));
		System.out.println(op.getValore().toString());
		op.setValore(new Integer(35264));
		System.out.println(op.getValore().toString());
		op.setValore(new Float(35264.656598));
		System.out.println(op.getValore().toString());
		op.setValore(new Double(-35264.656598));
		System.out.println(op.getValore().toString());
		op.setValore(new String("CIAO"));
		System.out.println(op.getValore().toString());
		System.out.println(op.getValoreStringa());
		
		
	}



}
