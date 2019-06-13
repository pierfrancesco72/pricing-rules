package io.pf.pricing.db;

import java.sql.SQLException;
import java.util.List;

import io.pf.pricing.db.dto.CondizioneDto;


public class ComponenteDao {

	private ComponenteDao() {
		
	}
	
	public static List<CondizioneDto> getComponenti() throws SQLException {
		
		return DataSourceCondizioni.getJdbc().query("select ?? from C6TBCZCO C1 inner join C6TBCOMP C2", new CondizioneDto());
	}
	
	
	public static CondizioneDto getComponentee(String codiceComponente) throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject("select C1.dscdzbr, C1.idcompo, C2.interi, C2.decimali from C6TBCZCO C1 inner join C6TBCOMP C2 on (C1.tipocompo=C2.tipocomp) where dsccompo = :codiceComponente", 
						CondizioneDto.class, codiceComponente);
	}

}
