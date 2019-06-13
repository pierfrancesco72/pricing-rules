package io.pf.pricing.db.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CondizioneDto implements RowMapper<CondizioneDto> {

	private String codice;
	private Integer id;
	private Integer idServizio;
	
	public CondizioneDto() {}
	
	public CondizioneDto(String codice, Integer id, Integer idServizio) {
		this.idServizio = idServizio;
		this.codice = codice;
		this.id = id;
	}

	@Override
	public CondizioneDto mapRow(ResultSet rs, int arg1) throws SQLException {
		CondizioneDto dto = new CondizioneDto();
		dto.setCodice(rs.getString(1));
		dto.setId(rs.getInt(2));
		dto.setIdServizio(rs.getInt(3));
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

	public Integer getIdServizio() {
		return idServizio;
	}

	public void setIdServizio(Integer idServizio) {
		this.idServizio = idServizio;
	}


	

}
