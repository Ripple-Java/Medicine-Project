package com.rippletec.medicine.bean;

import javax.annotation.Resource;

import com.rippletec.medicine.utils.ErrorCode;

public class Result {
    
    private boolean result;
    
    private int errorCode;
    
    private Object message;

    
    public Result(boolean result) {
   	super();
   	this.result = result;
    }
    public Result(boolean result, Object message) {
   	super();
   	this.result = result;
   	this.message = message;
    }

    public Result(boolean result, int errorCode) {
	super();
	this.result = result;
	this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return result;
    }


    public Result setResult(boolean result) {
        this.result = result;
        return this;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    
    

}
