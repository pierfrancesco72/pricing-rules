package io.pf.pricing.db.dto;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import io.pf.pricing.model.IdComponente;
import io.pf.pricing.model.IdComponente.TipoComponente;

public class ValoreDto implements RowMapper<ValoreDto> {

	
	private IdComponente componente;
	private BigDecimal valore;
	private String valoreStr;
	
	
	@Override
	public ValoreDto mapRow(ResultSet rs, int arg1) throws SQLException {
		ValoreDto dto = new ValoreDto();
		dto.setValore(rs.getBigDecimal(1));
		dto.setValoreStr(rs.getString(2));
		return dto;
	}


	public IdComponente getComponente() {
		return componente;
	}


	public void setComponente(IdComponente componente) {
		this.componente = componente;
	}


	public BigDecimal getValore() {
		return valore;
	}


	public void setValore(BigDecimal valore) {
		this.valore = valore;
	}


	public String getValoreStr() {
		return valoreStr;
	}


	public void setValoreStr(String valoreStr) {
		this.valoreStr = valoreStr;
	}

	public Object toCache() {
		Object val = null;
		if (componente.getTipo()==TipoComponente.NUMERICA) {
			valore = valore.divide(BigDecimal.valueOf(Math.pow(10, componente.getDecimali())), 5, BigDecimal.ROUND_HALF_UP);
			val = valore;
		} else if (componente.getTipo()==TipoComponente.STRINGA) {
			val = valoreStr;
		}
		return val;
	}


	

}
