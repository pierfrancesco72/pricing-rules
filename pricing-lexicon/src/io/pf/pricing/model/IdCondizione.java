package io.pf.pricing.model;

public class IdCondizione {

	private Integer idCondizione;
	private Integer idServizioDefault;
	
	public IdCondizione(Integer idCdz, Integer cdserint) {
		idCondizione = idCdz;
		idServizioDefault = cdserint;
	}

	public Integer getIdCondizione() {
		return idCondizione;
	}

	public void setIdCondizione(Integer idCondizione) {
		this.idCondizione = idCondizione;
	}

	public Integer getIdServizioDefault() {
		return idServizioDefault;
	}

	public void setIdServizioDefault(Integer idServizioDefault) {
		this.idServizioDefault = idServizioDefault;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IdCondizione [idCondizione=");
		builder.append(idCondizione);
		builder.append(", idServizioDefault=");
		builder.append(idServizioDefault);
		builder.append("]");
		return builder.toString();
	}


}
