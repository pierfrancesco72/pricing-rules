package io.pf.pricing.utils;

public class StringUtils {

	public StringUtils() {
		
	}
	
	
	public static String getKey(final String keyValue) {
		return keyValue.split("\\=")[0];
	}
	
	public static String getValue(final String keyValue) {
		return keyValue.split("\\=")[1];
	}
	
	public static String getKeyValue(final String key, final String value) {
		return key+"="+value;
	}

}
