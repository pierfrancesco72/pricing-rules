package io.pf.pricing.db;

import java.sql.SQLException;
import java.util.List;

import io.pf.pricing.db.dto.QualificazioneDto;


public class QualificazioneDao {

	private QualificazioneDao() {
		
	}
	
	public static List<QualificazioneDto> getQualificazioni() throws SQLException {
		
		return DataSourceCondizioni.getJdbc().query("select AAA, BBB, CCC from DCLDM0C.C6VSQUAL", new QualificazioneDto());
	}
	
	
	public static QualificazioneDto getQualificazione(String strQualificazione) throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject("select AAA, BBB, CCC from DCLDM0C.C6VSQUAL where par1||par2||par3||par4||par5 = :strQualificazione", 
						QualificazioneDto.class, strQualificazione);
	}

}
