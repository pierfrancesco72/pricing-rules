package io.pf.pricing.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Operando {
	
	private enum Tipologia {DRIVER, CONDIZIONE, OUTPUT}
	private Tipologia tipologia;
	private String codice;
	private String valoreStringa;
	private BigDecimal valore;
		
	private Condizione condizione;
	
	/**
	 * Costruttore solo per i valori assoluti
	 * @param numero
	 */
	public Operando(Number numero) {
		this("dr:VALORE_ASSOLUTO");
		valore = new BigDecimal(numero.doubleValue());
	}
	
	/**
	 * Construttore per le tipologie dr: e out:
	 * @param tipologia
	 * @param codice
	 */
	public Operando (String codice) {
		
		String tipologia = codice.substring(0, codice.indexOf(':'));
		
		if (tipologia.equals("dr"))
			this.tipologia = Tipologia.DRIVER;
		else if (tipologia.equals("cdz"))
			this.tipologia = Tipologia.CONDIZIONE;
		else if (tipologia.equals("out"))
			this.tipologia = Tipologia.OUTPUT;
		else
			throw new RuntimeException("Tipologia Operatore non riconosciuta");
		
		this.codice = codice;
			
	}
	
	/**
	 * Costruttore per tipologia cdz:
	 * @param tipologia
	 * @param codiceCondizione
	 * @param codiceComponente
	 */
	public Operando (String codiceCondizione, String codiceComponente) {
		
		this.codice = codiceCondizione + ":" + codiceComponente;
		
		if (this.tipologia==Tipologia.CONDIZIONE) {
			condizione = new Condizione(codiceCondizione, codiceComponente);
		}
	}

	/**
	 * Costruttore per tipologia cdz: con qualificazione
	 * @param codiceCondizione
	 * @param codiceComponente
	 * @param qualificazione
	 */
	public Operando (String codiceCondizione, String codiceComponente, String strQualificazione) {
		this(codiceCondizione, codiceComponente);
		Qualificazione q = new Qualificazione(strQualificazione);
		condizione.setQualificazione(q);
	}
	
	
	public Tipologia getTipologia() {
		return tipologia;
	}


	public String getCodice() {
		return codice;
	}


	public String getValoreStringa() {
		return valoreStringa;
	}

	public void setValoreStringa(String valoreStringa) {
		this.valoreStringa = valoreStringa;
	}

	public BigDecimal getValore() {
		return valore;
	}

	public void setValore(BigDecimal valore) {
		this.valore = valore;
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


	public Condizione getCondizione() {
		return condizione;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Operando [tipologia=");
		builder.append(tipologia);
		builder.append(", codice=");
		builder.append(codice);
		builder.append(", valoreStringa=");
		builder.append(valoreStringa);
		builder.append(", valore=");
		builder.append(valore);
		builder.append("]");
		return builder.toString();
	}


	
	public static void main(String[] args) {
		Operando op = new Operando(15.95);
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
