package com.rippletec.medicine.bean;

public class Result {
    
    private boolean result;
    
    private String message;

    public Result(boolean result, String message) {
	super();
	this.result = result;
	this.message = message;
    }

    public boolean isSuccess() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public Result setResult(boolean result) {
        this.result = result;
        return this;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }
    
    

}
