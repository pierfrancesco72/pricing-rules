package io.pf.pricing.db.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.pf.pricing.model.IdCondizione;

public class CondizioneDto extends IdCondizione implements RowMapper<CondizioneDto> {

	private String codice;

	
	public CondizioneDto() {}
	
	public CondizioneDto(String codice, Integer id, Integer idServizio) {
		this.codice = codice;
		setIdServizioDefault(idServizio);
		setId(id);
	}

	@Override
	public CondizioneDto mapRow(ResultSet rs, int arg1) throws SQLException {
		CondizioneDto dto = new CondizioneDto();
		dto.setCodice(rs.getString(1).trim());
		dto.setId(rs.getInt(2));
		dto.setIdServizioDefault(rs.getInt(3));
		return dto;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}


	public IdCondizione toCache() {
		return (IdCondizione)this;
	}

	

}
