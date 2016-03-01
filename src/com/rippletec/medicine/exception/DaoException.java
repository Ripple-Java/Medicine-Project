package com.rippletec.medicine.exception;

import com.rippletec.medicine.utils.ErrorCode;

public class DaoException extends Exception{

    private static final long serialVersionUID = 1L;

    private int errorCode;
    
    private String info;
    
    public DaoException() {
    }

    public DaoException(int errorCode) {
	this.errorCode = errorCode;
    }

    public DaoException(int errorCode, String info) {
	this.errorCode = errorCode;
	this.info = info;
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
