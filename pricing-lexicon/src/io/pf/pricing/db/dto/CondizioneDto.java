package io.pf.pricing.db.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CondizioneDto implements RowMapper<CondizioneDto> {

	private String codice;
	private Integer id;
	
	public CondizioneDto() {}
	
	public CondizioneDto(String codice, Integer id) {
		this.codice = codice;
		this.id = id;
	}

	@Override
	public CondizioneDto mapRow(ResultSet rs, int arg1) throws SQLException {
		CondizioneDto dto = new CondizioneDto();
		dto.setCodice(rs.getString(1));
		dto.setId(rs.getInt(2));
		return dto;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	

}
