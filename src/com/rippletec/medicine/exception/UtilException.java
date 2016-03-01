package com.rippletec.medicine.exception;

public class UtilException extends Exception {


    private static final long serialVersionUID = -6939800810481885250L;
   

    private int errorCode;
    
    private String info;
    
    public UtilException(int errorCode) {
	this.errorCode = errorCode;
    }
    
    public UtilException(int errorCode, String info) {
	this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    

}
