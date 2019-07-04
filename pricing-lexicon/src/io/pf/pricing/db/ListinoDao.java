package io.pf.pricing.db;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.configuration2.Configuration;

import io.pf.pricing.db.dto.ListinoDto;
import io.pf.pricing.utils.ConfigUtils;


public class ListinoDao {
	
	private static Configuration conf = ConfigUtils.getProperties();

	private ListinoDao() {
		
	}
	
	public static List<ListinoDto> getListini() throws SQLException {
		
		return DataSourceCondizioni.getJdbc()
				.query(conf.getString("db.listino.combinazioni"), new ListinoDto());
	}
	
	/**
	 * Questo metodo non dovrebbe essere mai chiamato, e comunque non funzionerebbe perché la query ancora non è
	 * settata correttamente 
	 * @param strListinoOrdinata
	 * @return
	 * @throws SQLException
	 */
	public static ListinoDto getListino(String strListinoOrdinata) throws SQLException {
		
		String[] pars = strListinoOrdinata.split("\\,");
		
		return DataSourceCondizioni.getJdbc()
				.queryForObject(conf.getString("db.listino.combinazione"), new ListinoDto(), (Object[])pars);
	}

}
