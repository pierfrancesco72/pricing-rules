package io.pf.pricing.db.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.pf.pricing.model.IdComponente;

public class ComponenteDto extends IdComponente implements RowMapper<ComponenteDto> {

	
	private String codice;
	
	
	@Override
	public ComponenteDto mapRow(ResultSet rs, int arg1) throws SQLException {
		ComponenteDto dto = new ComponenteDto();
		dto.setCodice(rs.getString(1).trim());
		dto.setId(rs.getInt(2));
		dto.setTipo(TipoComponente.daDB(rs.getString(3).trim()));
		dto.setLunghezza(rs.getInt(4));
		dto.setDecimali(rs.getInt(5));
		return dto;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public IdComponente toCache() {
		return (IdComponente)this;
	}
	

}
