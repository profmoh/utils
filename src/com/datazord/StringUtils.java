package com.datazord;

public class StringUtils {

	/**
	 * Accepts any String & converts in into Camel Case String
	 * 
	 * @param text
	 * @return
	 */
	public static String convertToCamelCase(String text) {
		if (text==null)
	        return null;

	    final StringBuilder ret = new StringBuilder(text.length());

	    for (final String word : text.split(" ")) {
	        if (!word.isEmpty()) {
	            ret.append(word.substring(0, 1).toUpperCase());
	            ret.append(word.substring(1).toLowerCase());
	        }
	    }

	    String camelCase = ret.toString();
	    return Character.toLowerCase(camelCase.charAt(0)) + camelCase.substring(1);
	}

}
