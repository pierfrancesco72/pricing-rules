package io.pf.pricing.model;

public class IdComponente {
	
	private Integer idComponente;
	private Integer interi;
	private Integer decimali;
	
	public IdComponente(Integer idCompo, Integer interi, Integer decimali) {
		idComponente = idCompo;
		this.interi = interi;
		this.decimali = decimali;
	}

	public Integer getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(Integer idComponente) {
		this.idComponente = idComponente;
	}

	public Integer getInteri() {
		return interi;
	}

	public void setInteri(Integer interi) {
		this.interi = interi;
	}

	public Integer getDecimali() {
		return decimali;
	}

	public void setDecimali(Integer decimali) {
		this.decimali = decimali;
	}
	
}
