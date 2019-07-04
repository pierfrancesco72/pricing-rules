package io.pf.pricing.db.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.pf.pricing.model.ParametriList;

public class ListinoDto implements RowMapper<ListinoDto> {

	
	private Integer idCombinazione;
	private ParametriList pars;
	
	
	@Override
	public ListinoDto mapRow(ResultSet rs, int arg1) throws SQLException {
		ListinoDto dto = new ListinoDto();
		ParametriList pars = new ParametriList();
		dto.setIdCombinazione(rs.getInt(1));
		dto.setPars(pars);
		
		for (int k=2; k<7; k++) {
			String par = rs.getString(k);
			if (par != null)
				pars.add(par.trim());
		}
		return dto;
	}


	public ParametriList toCache() {
		return pars;
	}

	public Integer getIdCombinazione() {
		return idCombinazione;
	}

	public void setIdCombinazione(Integer idCombinazione) {
		this.idCombinazione = idCombinazione;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ListinoDto [idCombinazione=");
		builder.append(idCombinazione);
		builder.append(", pars=");
		builder.append(pars);
		builder.append("]");
		return builder.toString();
	}


	public ParametriList getPars() {
		return pars;
	}


	public void setPars(ParametriList pars) {
		this.pars = pars;
	}


	

}
