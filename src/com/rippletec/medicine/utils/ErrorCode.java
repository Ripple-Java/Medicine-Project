package com.rippletec.medicine.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository(ErrorCode.CLASS_NAME)
public class ErrorCode {
    
    public static final String CLASS_NAME = "ErrorCode";
    
    public static final int INTENAL_ERROR = 100000;
    
    public static final int ILLEGAL_ACCESS_ERROR = 101000;
    public static final int BAD_ACCESS_ERROR = 101001;
    
    public static final int DB_NO_ENITY_ERROR = 102001;
    
    public static final int FILE_REDA_ERROR = 110001;
    public static final int FILE_WRITE_ERROR = 110002;
    public static final int FILE_NOT_EXISTED_ERROR = 110003;
 
    

    public static final int PARAM_ERROR = 100101;

    public static final int USER_EXISTED_ERROR = 100201;
    public static final int USER_NOT_EXISTED_ERROR = 100202;
    public static final int USER_DUPLICATE_LOGIN_ERROR = 100203;
    public static final int USER_PASSWORD_ERROR = 100204;
    public static final int USER_NOT_LOGINED_ERROR = 100205;

    public static final int WRONG_VERIFYCODE_ERROR = 100301;
    public static final int EXPIRE_VERIFYCODE_ERROR = 100302;
    
    public static final int JSONUTIL_NOT_MAPPING_ERROR = 101001;

    private Map<Integer, String> errorInfo = new HashMap<Integer, String>();

    public ErrorCode() {
	errorInfo.put(ILLEGAL_ACCESS_ERROR, "非法操作错误");
	errorInfo.put(BAD_ACCESS_ERROR, "操作失败错误");
	errorInfo.put(PARAM_ERROR, "非法参数错误");
	errorInfo.put(INTENAL_ERROR, "内部错误");

    }
    
    public String getInfo(int code) {
	return errorInfo.get(code);
    }
}
