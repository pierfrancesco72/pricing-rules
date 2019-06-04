package io.pf.pricing.db.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ComponenteDto implements RowMapper<ComponenteDto> {

	private String codice;
	private Integer id;
	private Integer interi;
	private Integer decimali;
	
	public ComponenteDto() {}
	
	@Override
	public ComponenteDto mapRow(ResultSet rs, int arg1) throws SQLException {
		ComponenteDto dto = new ComponenteDto();
		dto.setCodice(rs.getString(1));
		dto.setId(rs.getInt(2));
		dto.setInteri(rs.getInt(3));
		dto.setDecimali(rs.getInt(4));
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
