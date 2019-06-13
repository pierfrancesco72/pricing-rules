package io.pf.pricing.db;

import java.sql.SQLException;
import java.util.List;

import io.pf.pricing.db.dto.QualificazioneDto;


public class QualificazioneDao {

	private QualificazioneDao() {
		
	}
	
	public static List<QualificazioneDto> getQualificazioni() throws SQLException {
		
		return DataSourceCondizioni.getJdbc().query("select Q1||'='||V1, Q2||'='||V2, Q3||'='||V3, Q4||'='||V4, Q5||'='||V5, Q6||'='||V6, Q7||'='||V7, Q8||'='||V8, Q9||'='||V9, Q10||'='||V10 from DCLDM0C.C6VSQUAL", new QualificazioneDto());
	}
	
	
	public static QualificazioneDto getQualificazione(String strQualificazione) throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject("select Q1||'='||V1, Q2||'='||V2, Q3||'='||V3, Q4||'='||V4, Q5||'='||V5, Q6||'='||V6, Q7||'='||V7, Q8||'='||V8, Q9||'='||V9, Q10||'='||V10 '"
						+ "from DCLDM0C.C6VSQUAL where par1||par2||par3||par4||par5 = :strQualificazione", 
						QualificazioneDto.class, strQualificazione);
	}

}
