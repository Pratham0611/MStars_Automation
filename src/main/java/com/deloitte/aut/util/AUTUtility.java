/**
 * 
 */
package com.deloitte.aut.util;

import java.util.MissingResourceException;


/**
 * @author pshanmukham
 *
 */
public class AUTUtility {
	
	private static java.util.ResourceBundle apiBundle = null;
	private static final String BUNDLE_NAME="AUT_ErrorMessages";

	/**
	 * Get message from bundle
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getDynamicMessage(String key, Object[] params){
		String pattern = null;
		if(key != null && key.trim().length() > 0) {
			try {
				apiBundle = java.util.ResourceBundle.getBundle(BUNDLE_NAME);
				pattern = apiBundle.getString(key);
			} catch(MissingResourceException mre) {
				pattern = "Unable to Find " + key;
			}
			
			String message = null ;
			if(params!=null)
				message = java.text.MessageFormat.format(pattern, params);
			else
				message = pattern;
			return message ;
		} else {
			return "";
		}
	}
	

}
