package KeywordDriverUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import Model.KeywordDriven.KeywordModel;



public class DriverSession {
private static final DriverSession session = new DriverSession();
	
	private static final Logger logger = Logger.getLogger(DriverSession.class.getName());
	
	private static Map<String, Object> cache = new HashMap<String, Object>();
	
	public  DriverSession getInstance(){
		return session;
	}
	
	/*
	 * Store value to the session with the given key
	 */
	public void add(String key, Object value, KeywordModel keywordModel){
		cache.put((keywordModel.scenario+"_"+key), value);
		logger.info("Stored key:"+key+" value:"+value+" into session");
	}
	
	/*
	 * Read session data for the given key
	 */
	public Object get(String key, KeywordModel keywordModel){
		logger.fine("Reading key:"+(keywordModel.scenario+"_"+key)+" from session");
		return cache.get(keywordModel.scenario+"_"+key);
	}
	
	/*
	 * Remove the session data stored with the given key
	 */
	public void remove(String key){
		cache.remove(key);
		logger.fine("Removed key:"+key+" from session");
	}
	
	/*
	 * Clear the session data
	 */
	public void clear(){
		logger.fine("Clearing the session");
		cache.clear();
	}
	
	public Set<String> getKeys(){
		return cache.keySet();
	}
	

}
