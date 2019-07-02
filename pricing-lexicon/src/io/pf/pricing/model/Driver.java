package io.pf.pricing.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Driver {
	
	private static final Logger log = Logger.getLogger(Driver.class.getName());
	
	public enum TipoDriver {NUMERICO, STRINGA, BOOLEANO}
	private TipoDriver tipo;
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
		tipo=TipoDriver.NUMERICO;
		valore = BigDecimal.ZERO;
		valoreBooleano = false;
		int idxPrefisso = codice.indexOf(':') + 1;
		this.codice = codice.substring(idxPrefisso);
	}
	
	public Driver (String codice, Object valore) {
		this(codice);
		setValore(valore);
	}
	
	
	/**
	 * Costruttore solo per i valori assoluti
	 * @param numero
	 */
	public Driver(Number numero) {
		this("dr:VALORE_NUMERICO");
		setValore(numero);
		
	}
	
	public Driver(Boolean bool) {
		this("dr:VALORE_BOOELANO");
		setValore(bool);
	}
	

	
	/**
	 * Costruttore per tipologia cdz:
	 * @param tipologia
	 * @param codiceCondizione
	 * @param codiceComponente
	 */
	public Driver (String codiceCondizione, String codiceComponente) throws SQLException {
		this.codice = codiceCondizione + ":" + codiceComponente;
		condizione = new Condizione(codiceCondizione, codiceComponente);
	}

	/**
	 * Costruttore per tipologia cdz: con qualificazione
	 * @param codiceCondizione
	 * @param codiceComponente
	 * @param qualificazione
	 */
	public Driver (String codiceCondizione, String codiceComponente, String strQualificazione) throws SQLException  {
		this(codiceCondizione, codiceComponente);
		Qualificazione q = new Qualificazione(strQualificazione);
		condizione.setQualificazione(q);
	}
	
	public void setValore(Object valore) {
		if (valore instanceof Number) {
			tipo = TipoDriver.NUMERICO;
			this.valore = new BigDecimal(((Number)valore).floatValue());
		} else if (valore instanceof Boolean) {
			tipo = TipoDriver.BOOLEANO;
			if (((Boolean)valore).booleanValue()) { 
				this.valore = new BigDecimal(1);
				valoreBooleano = true;
			} else { 
				this.valore = new BigDecimal(0);
				valoreBooleano = false;
			}
		} else if (valore instanceof String) {
			tipo = TipoDriver.STRINGA;
			this.valoreStringa = valore.toString();
		}
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


	public Condizione getCondizione() {
		return condizione;
	}

	public Boolean getValoreBooleano() {
		return valoreBooleano;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Driver [");
		builder.append(codice);
		if (tipo==TipoDriver.NUMERICO && valore!=null) {
			builder.append("=");
			builder.append(valore.setScale(5, RoundingMode.HALF_DOWN).toPlainString());
		}
		if (tipo==TipoDriver.STRINGA && valoreStringa!=null) {
			builder.append("=");
			builder.append(valoreStringa);
		}
		if (tipo==TipoDriver.BOOLEANO && valoreBooleano!=null) {
			builder.append("=");
			builder.append(valoreBooleano);
		}
		if (condizione!=null) {
			builder.append(", condizione=");
			builder.append(condizione);
		}
		builder.append("]");
		return builder.toString();
	}

	public String getValoreFormattato() {
		switch (tipo) {
		case NUMERICO:
			return valore.setScale(2, RoundingMode.HALF_DOWN).toPlainString();
		case BOOLEANO:
			return valoreBooleano.toString();
		default:
			return valoreStringa;
		}
	}

	public TipoDriver getTipo() {
		return tipo;
	}
	
	@Override
	public boolean equals(Object obj) {
		

		if (!(obj instanceof Driver)) 
			return false;
			
		Driver d2 = (Driver)obj;
		
		if (d2.getTipo()==TipoDriver.NUMERICO) {
			return getValore().equals(d2.getValore());
		} else if (d2.getTipo()==TipoDriver.BOOLEANO) {
			return getValoreBooleano().equals(d2.getValoreBooleano());
		} else if (d2.getTipo()==TipoDriver.STRINGA) {
			return getValoreStringa().equals(d2.getValoreStringa());
		} 
		return false;
	}
	

	
	public static void main(String[] args) {
		Driver op = new Driver("dr:PROVA_DRIVER",15.95);
		op.getValore().setScale(2, RoundingMode.HALF_UP);
		System.out.println(op.getValore().toPlainString());
		System.out.println(op.getValore().toString());
		
		op.setValore(new Boolean(true));
		System.out.println(op);
		op.setValore(new Boolean(false));
		System.out.println(op);
		op.setValore(new Integer(35264));
		System.out.println(op);
		op.setValore(new Float(35264.656598));
		System.out.println(op);
		op.setValore(new Double(-35264.656598));
		System.out.println(op);
		op.setValore(new String("CIAO"));
		System.out.println(op);
		
		
	}


}
