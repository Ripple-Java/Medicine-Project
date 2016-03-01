package com.rippletec.medicine.exception;

public class ControllerException extends Exception{
    
    private static final long serialVersionUID = -3724447562121082418L;

    private int errorCode;
    
    private String info;
    
    public ControllerException(int code, String info) {
	this.errorCode = code;
	this.info = info;
    }
    public ControllerException(int code) {
	this.errorCode = code;
    }
    public ControllerException() {
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
