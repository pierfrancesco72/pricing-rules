package io.pf.pricing.db.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class QualificazioneDto implements RowMapper<QualificazioneDto> {

	private Integer idServizio;
	private String strQualificazione;
	private Integer idQualificazione;
	
	public QualificazioneDto() {}
	

	@Override
	public QualificazioneDto mapRow(ResultSet rs, int arg1) throws SQLException {
		QualificazioneDto dto = new QualificazioneDto();
		dto.setStrQualificazione(rs.getString(1));
		dto.setIdServizio(rs.getInt(2));
		dto.setIdQualificazione(rs.getInt(3));
		return dto;
	}


	public Integer getIdServizio() {
		return idServizio;
	}


	public void setIdServizio(Integer idServizio) {
		this.idServizio = idServizio;
	}


	public String getStrQualificazione() {
		return strQualificazione;
	}


	public void setStrQualificazione(String strQualificazione) {
		this.strQualificazione = strQualificazione;
	}


	public Integer getIdQualificazione() {
		return idQualificazione;
	}


	public void setIdQualificazione(Integer idQualificazione) {
		this.idQualificazione = idQualificazione;
	}


	

}
