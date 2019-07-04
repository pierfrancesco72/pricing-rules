package io.pf.pricing.db.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.pf.pricing.model.ParametriList;

public class QualificazioneDto implements RowMapper<QualificazioneDto> {

	private Integer idServizio;
	private Integer idQualificazione;
	private ParametriList pars;
	
	public QualificazioneDto() {}
	

	@Override
	public QualificazioneDto mapRow(ResultSet rs, int arg1) throws SQLException {
		QualificazioneDto dto = new QualificazioneDto();
		ParametriList pars = new ParametriList();
		
		dto.setIdQualificazione(rs.getInt(1));
		dto.setIdServizio(rs.getInt(2));
		dto.setPars(pars);
		
		for (int k=3; k<13; k++) {
			String par = rs.getString(k);
			if (par != null)
				pars.add(par.trim());
		}
		return dto;
	}


	public Integer getIdServizio() {
		return idServizio;
	}


	public void setIdServizio(Integer idServizio) {
		this.idServizio = idServizio;
	}


	public Integer getIdQualificazione() {
		return idQualificazione;
	}


	public void setIdQualificazione(Integer idQualificazione) {
		this.idQualificazione = idQualificazione;
	}

	public ParametriList toCache() {
		return pars;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QualificazioneDto [idServizio=");
		builder.append(idServizio);
		builder.append(", idQualificazione=");
		builder.append(idQualificazione);
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
