package com.rippletec.medicine.exception;

public class UtilException extends Exception {


    private static final long serialVersionUID = -6939800810481885250L;
   

    private int errorCode;
    
    public UtilException(int errorCode) {
	this.errorCode = errorCode;
    }
    
    public UtilException(String msg, int errorCode) {
	super(msg);
	this.errorCode = errorCode;
    }
    
    
    public UtilException() {
    }
    
    
    public int getErrorCode() {
	return errorCode;
    }

    public void setErrorCode(int errorCode) {
	this.errorCode = errorCode;
    }

}
