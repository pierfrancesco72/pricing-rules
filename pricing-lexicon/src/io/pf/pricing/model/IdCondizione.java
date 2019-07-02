package io.pf.pricing.model;

public abstract class IdCondizione {

	private Integer id;
	private Integer idServizioDefault;
	
	public IdCondizione() {
	}


	public Integer getIdServizioDefault() {
		return idServizioDefault;
	}

	public void setIdServizioDefault(Integer idServizioDefault) {
		this.idServizioDefault = idServizioDefault;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IdCondizione [id=");
		builder.append(id);
		builder.append(", idServizioDefault=");
		builder.append(idServizioDefault);
		builder.append("]");
		return builder.toString();
	}

}
