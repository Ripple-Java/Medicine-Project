package com.rippletec.medicine.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import com.rippletec.medicine.model.Enterprise;

/**
 * @author Liuyi
 *
 */
public class StringUtil {
    
    public static final String SERVER_URL = "112.74.131.194:8080";
    
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,6}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    
    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
    
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    
    /**
     * 正则表达式：验证数子
     */
    public static final String REGEX_NUMBER = "^[0-9]*$";
    
    

    public static String generateCode(int charCount) {
	String charValue = "";
	for (int i = 0; i < charCount; i++) {
	    char c = (char) (randomInt(0, 10) + '0');
	    charValue += String.valueOf(c);
	}
	return charValue;
    }
    
    public static String RegisterContent(String account, String password) {
	String securityUrl;
	try {
	    securityUrl = MD5Util.getEncryptedPwd(account + password);
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    e.printStackTrace();
	    return null;
	}
	///邮件的内容  
        StringBuffer sb=new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");  
        sb.append("<a href=\"http://"+SERVER_URL+"/MedicineProject/Web/enteruser/validate?action=1&account=");  
        sb.append(account);   
        sb.append("&code=");   
        sb.append(securityUrl);  
        sb.append("\">http://"+SERVER_URL+"/MedicineProject/Web/enteruser/validate?action=1&account=");   
        sb.append(account);  
        sb.append("&code=");  
        sb.append(securityUrl);  
        sb.append("</a>");  
	return sb.toString();
    }

    public static String getAccount() {
	return (System.currentTimeMillis() + "").substring(3);
    }

    public static String getCountSql(String className) {
	return "select count(*) from " + className + " as c";
    }

    public static String getSearchHql(String className, String param) {
	return getSearchHql(className, new String[] { param });
    }

    public static String getSearchHql(String className, String[] params) {
	String hql = "from " + className;
	if (params == null || params.length == 0)
	    return hql;
	hql += " q where ";
	for (int i = 0; i < params.length; i++) {
	    String param = params[i];
	    hql += "q." + param + " like:" + param + " and ";
	}
	hql = hql.substring(0, hql.length() - 4);
	return hql;
    }

    public static String getSelectHql(String Name) {
	return getSelectHql(Name, new String[] {});
    }

    public static String getSelectHql(String Name, String param) {
	return getSelectHql(Name, new String[] { param });
    }

    /**
     * 获取hql语句
     * 
     * @param Name
     *            PO类名
     * @param params
     *            参数名
     * @return 返回实例 from Enterprise q where q.type=:type and q.phone=:phone
     */
    public static String getSelectHql(String Name, String[] params) {
	String hql = "from " + Name;
	if (params == null || params.length == 0)
	    return hql;
	hql += " q where ";
	for (int i = 0; i < params.length; i++) {
	    String param = params[i];
	    hql += "q." + param + "=:" + param + " and ";
	}
	hql = hql.substring(0, hql.length() - 4);
	return hql;
    }

    public static String getSelectSql(String tableName, String param) {
	return getSelectSql(tableName, new String[] { param });
    }

    public static String getSelectSql(String tableName, String[] params) {
	String hql = "select * from " + tableName;
	if (params == null || params.length == 0)
	    return hql;
	hql += " where ";
	for (int i = 0; i < params.length; i++) {
	    String param = params[i];
	    hql += param + "=? and ";
	}
	hql = hql.substring(0, hql.length() - 4);
	return hql;
    }


    public static boolean hasText(String str, String ...strs) {
	if(str == null || str.length()==0)
	    return false;
	if(strs != null && strs.length > 0)
	    return hasText(strs);
	return true;
    }
    
    

    public static boolean hasText(String[] strs) {
	if(strs == null || strs.length == 0)
	    return false;
	for (String string : strs) {
	    if(!hasText(string))
		return false;
	}
	return true;
    }

    /**
     * 校验汉字
     * 
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
	return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验邮箱
     * 
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
	return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
	return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
	return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验密码
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
	return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验用户名
     * 
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
	return Pattern.matches(REGEX_USERNAME, username);
    }
    
    /**
     * 校验数字
     * 
     * @param number
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isNumber(String number) {
	return Pattern.matches(REGEX_NUMBER, number);
    }

    public static int randomInt(int from, int to) {
	Random r = new Random();
	return from + r.nextInt(to - from);
    }

    public static boolean isEnterpriseName(String name) {
	return true;
    }

    public static boolean isEnterpriseType(int type) {
	if(type == Enterprise.DOMESTIC || type == Enterprise.FOREIGN || type==Enterprise.JOINT)
	    return true;
	return false;
    }

    

}
