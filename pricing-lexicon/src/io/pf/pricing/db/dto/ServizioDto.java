package io.pf.pricing.db.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ServizioDto implements RowMapper<ServizioDto> {

	private String codiceServizio;
	private Integer idServizio;
	
	public ServizioDto() {}
	
	public ServizioDto(String codice, Integer id) {
		codiceServizio = codice;
		idServizio = id;
	}

	@Override
	public ServizioDto mapRow(ResultSet rs, int arg1) throws SQLException {
		ServizioDto servizio = new ServizioDto();
		servizio.setCodiceServizio(rs.getString(1));
		servizio.setIdServizio(rs.getInt(2));
		return servizio;
	}

	public String getCodiceServizio() {
		return codiceServizio;
	}

	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}

	public Integer getIdServizio() {
		return idServizio;
	}

	public void setIdServizio(Integer idServizio) {
		this.idServizio = idServizio;
	}
	
	

}
