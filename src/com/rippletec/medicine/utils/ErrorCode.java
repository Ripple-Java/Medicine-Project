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
    public static final int NOT_VALICATE_ACCESS_ERROR = 101002;
    
    public static final int DB_NO_ENITY_ERROR = 102001;
    public static final int DB_ID_ERROR = 102002;
    public static final int DB_PAGE_ERROR = 102003;
    public static final int DB_SAVE_ERROR = 102004;
    
    public static final int FILE_REDA_ERROR = 110001;
    public static final int FILE_WRITE_ERROR = 110002;
    public static final int FILE_NOT_EXISTED_ERROR = 110003;
    public static final int FILE_CLOSE_ERROR = 110004;
    public static final int FILE_FORMAT_ERROR = 110005;
 
    

    public static final int PARAM_ERROR = 100101;
    public static final int PARAM_FORMAT_ERROR = 100102;

    public static final int USER_EXISTED_ERROR = 100201;
    public static final int USER_NOT_EXISTED_ERROR = 100202;
    public static final int USER_DUPLICATE_LOGIN_ERROR = 100203;
    public static final int USER_PASSWORD_ERROR = 100204;
    public static final int USER_OLD_PASSWORD_ERROR = 100205;
    public static final int USER_NOT_LOGINED_ERROR = 100206;
    public static final int USER_GET_ACCOUNT_ERROR = 100207;
    public static final int USER_SEDN_EMAIL_ERROR = 100208;
    public static final int USER_REGISTER_DENIED_ERROR = 100209;
    

    public static final int VERIFYCODE_WRONG_ERROR = 100301;
    public static final int VERIFYCODE_EXPIRE_ERROR = 100302;
    public static final int VERIFYCODE_SEND_ERROR = 100303;
    public static final int VERIFYCODE_DENIED_ERROR = 100304;
    
    public static final int ENTERPRISE_GET_ID_ERROR = 100401;
    public static final int ENTERPRISE_VALICATE_ERROR = 100402;
    
    public static final int PERMISSION_DENIED_ERROR = 100501;
    
    public static final int CHECKCODE_ERROR = 100601;
    
    public static final int FAVORITE_EXIST_ERROR = 100701;
    
    
    public static final int JSONUTIL_NOT_MAPPING_ERROR = 101001;

    private Map<Integer, String> errorInfo = new HashMap<Integer, String>();

    public ErrorCode() {
	errorInfo.put(ILLEGAL_ACCESS_ERROR, "非法操作错误");
	errorInfo.put(BAD_ACCESS_ERROR, "操作失败错误");
	errorInfo.put(PARAM_ERROR, "非法参数错误");
	errorInfo.put(INTENAL_ERROR, "内部错误");
	errorInfo.put(NOT_VALICATE_ACCESS_ERROR, "未经验证操作错误");
	errorInfo.put(DB_ID_ERROR, "数据id错误");
	errorInfo.put(DB_NO_ENITY_ERROR, "数据不存在错误");
	errorInfo.put(DB_PAGE_ERROR, "数据分页参数错误");
	errorInfo.put(DB_SAVE_ERROR, "数据保存错误");
	errorInfo.put(FILE_CLOSE_ERROR, "文件关闭错误");
	errorInfo.put(FILE_FORMAT_ERROR, "文件格式错误");
	errorInfo.put(FILE_NOT_EXISTED_ERROR, "文件不存在错误");
	errorInfo.put(FILE_WRITE_ERROR, "文件写入失败错误");
	errorInfo.put(FILE_REDA_ERROR, "文件读取失败错误");
	errorInfo.put(PARAM_FORMAT_ERROR, "参数格式错误");
	errorInfo.put(USER_DUPLICATE_LOGIN_ERROR, "用户重复登录错误");
	errorInfo.put(USER_EXISTED_ERROR, "用户已存在错误");
	errorInfo.put(USER_GET_ACCOUNT_ERROR, "获取用户账户失败错误");
	errorInfo.put(USER_NOT_EXISTED_ERROR, "用户不存在错误");
	errorInfo.put(USER_NOT_LOGINED_ERROR, "用户未登录错误");
	errorInfo.put(USER_OLD_PASSWORD_ERROR, "用户原密码错误");
	errorInfo.put(USER_PASSWORD_ERROR, "用户密码错误");
	errorInfo.put(USER_REGISTER_DENIED_ERROR, "用户非法注册错误");
	errorInfo.put(USER_SEDN_EMAIL_ERROR, "发送用户注册验证邮件失败错误");
	errorInfo.put(VERIFYCODE_DENIED_ERROR, "验证码不正确错误");
	errorInfo.put(VERIFYCODE_EXPIRE_ERROR, "验证码过期错误");
	errorInfo.put(VERIFYCODE_SEND_ERROR, "验证码发送失败错误");
	errorInfo.put(VERIFYCODE_WRONG_ERROR, "验证码错误");
	errorInfo.put(ENTERPRISE_GET_ID_ERROR, "获取企业id失败错误");
	errorInfo.put(ENTERPRISE_VALICATE_ERROR, "企业验证失败错误");
	errorInfo.put(PERMISSION_DENIED_ERROR, "权限不足错误");
	errorInfo.put(CHECKCODE_ERROR, "App校验失败错误");
	errorInfo.put(JSONUTIL_NOT_MAPPING_ERROR, "JsonUtil工具配置参数错误");
	errorInfo.put(FAVORITE_EXIST_ERROR, "收藏已存在错误");
	

    }
    
    public String getInfo(int code) {
	return errorInfo.get(code);
    }
    
    public Map<Integer, String> getInfoMap(int code) {
   	return errorInfo;
    }
}
