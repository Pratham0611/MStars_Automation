/**
 * 
 */
package com.deloitte.exception;

import java.util.logging.Level;

/**
 * @author pshanmukham
 *
 */
public class AUTException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String errorCode;
    String errorMessage;
    Throwable exception;
    Object[] object;
    Level level=Level.SEVERE;
    boolean flag = false;
    
    /**
	 * @return Returns the errorMessage.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage The errorMessage to set.
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * No argument constructor.
	 */
	public AUTException() 
    {
		
    }
	/**
	 * @param errorCode error code
	 */
	public AUTException(String errorCode) 
    {
		this.errorCode = errorCode;
    }    
	public AUTException(String errorCode, String errorMessage) 
    {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
    }
	public AUTException(String errorCode, Object[] object) 
    {
		this.errorCode = errorCode;
		this.object = object;
    } 
    /**
     * Constructor takes Error code and actual ex as arguments.
     * @param code Error code
     * @param ex Actual Exception occured.
     */
    public AUTException(String code,Throwable ex)
    {
        this.errorCode = code;
        this.exception = ex;
    }
    
    public AUTException(String code,Throwable ex, Object[] object, boolean flag)
    {
        this.errorCode = code;
        this.exception = ex;
        this.object = object; 
        this.flag = flag;
    }
    
    public AUTException(String code, Object[] object, Level level, Throwable ex)
    {
        this.errorCode = code;
        this.exception = ex;
        this.object = object; 
        this.level = level;
    }
    
    public AUTException(String code,String locale, Object obj)
    {
        this.errorCode = code;
        //this.locale = locale;
    }
    /**
     * Returns the error code associated with the exception.
     * @return
     */
    public String getErrorCode() 
    {
        return this.errorCode;
    }
		   
    
    /**
     * @return Returns the object.
     */
    public Object[] getObject() {
        return object;
    }
    /**
     * @return Returns the exception.
     */
    public Throwable getException() {
        return exception;
    }
    /**
     * @return Returns the level.
     */
    public Level getLevel() {
        return level;
    }
    /**
     * @param level The level to set.
     */
    public void setLevel(Level level) {
        this.level = level;
    }


}
