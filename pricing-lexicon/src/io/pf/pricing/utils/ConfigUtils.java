package io.pf.pricing.utils;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ConfigUtils {
	
	private static final Logger log = Logger.getLogger(ConfigUtils.class.getName());
	private static Configurations configurazioni;
	private static Configuration config;
	
	private ConfigUtils() {
		
	}
	
	public static Configuration getProperties() {
		if (configurazioni==null) {
			configurazioni = new Configurations();
		}
		if (config == null) {
			try {
				File prop = new File(Configuration.class.getResource("/application.properties").getPath());
			    config = configurazioni.properties(prop);
			} catch (ConfigurationException cex) {
			    log.log(Level.SEVERE, "Errore durante il caricamento del file application.properties", cex);
			}
		}
		
		return config;
	}
	
	public static void reset() {
		
		configurazioni = null;
		config = null;
	}

}
 