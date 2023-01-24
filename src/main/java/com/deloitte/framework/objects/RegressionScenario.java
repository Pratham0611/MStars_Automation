/**
 * 
 */
package com.deloitte.framework.objects;

import java.io.Serializable;

/**
 * @author skandacharam
 *
 */
public class RegressionScenario implements Serializable{


	private static final long serialVersionUID = -8430265319165666475L;
	
	private String type;
	private String name;
	private String description;
	
	/**
	 * @param type
	 * @param name
	 */
	public RegressionScenario(String type, String name, String description) {
		super();
		this.type = type;
		this.name = name;
		this.description = description;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	
	}
