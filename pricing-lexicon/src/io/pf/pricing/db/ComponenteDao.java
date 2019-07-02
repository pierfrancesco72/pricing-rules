package io.pf.pricing.db;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.configuration2.Configuration;

import io.pf.pricing.db.dto.ComponenteDto;
import io.pf.pricing.utils.ConfigUtils;


public class ComponenteDao {
	
	private static Configuration conf = ConfigUtils.getProperties();

	private ComponenteDao() {
		
	}
	
	public static List<ComponenteDto> getComponenti() throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.query(conf.getString("db.componenti"), new ComponenteDto());
	}
	
	
	public static ComponenteDto getComponente(String codiceComponente, Integer idCondizione) throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject(conf.getString("db.componente"), 
						new ComponenteDto(), idCondizione, codiceComponente);
	}

}
