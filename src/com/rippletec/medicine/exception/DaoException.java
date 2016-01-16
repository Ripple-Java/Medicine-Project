package com.rippletec.medicine.exception;

import com.rippletec.medicine.utils.ErrorCode;

public class DaoException extends Exception{

    private static final long serialVersionUID = 1L;

    private int errorCode;
    
    public DaoException() {
    }

    public DaoException(int errorCode) {
	this.errorCode = errorCode;
    }

    public int getErrorCode() {
	return errorCode;
    }

    public void setErrorCode(int errorCode) {
	this.errorCode = errorCode;
    }

}
