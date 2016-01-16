package com.rippletec.medicine.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.WestMedicine;

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
    
  //从当前系统中获取换行符，默认是"\n"  
  public static final  String lineSeparator = "\n";
    
    

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

    public static String getCountSql(String tableName) {
	return getCountSql(tableName, new String[]{});
    }
    
    public static String getCountSql(String tableName, String param){
	return getCountSql(tableName, new String[]{param});
    }
    
    public static String getCountSql(String tableName, String[] param){
	if(param.length == 0)
	    return "select count(*) from " + tableName;
	String sql = "select count(*) from " + tableName + "  where ";
	for (int i = 0; i < param.length; i++) {
	    sql += param[i]+"=? and ";
	}
	return sql.substring(0,sql.length()-4);
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
    
    public static String getSearchSql(String className, String field, String param) {
  	return getSearchSql(className, new String[] { field }, new String[] { param } );
      }

    
    public static String getSearchSql(String tableName, String[] fields, String[] params) {
   	String hql = "select * from " + tableName;
   	if (fields == null || fields.length == 0)
   	    return hql;
   	hql += " where ";
   	for (int i = 0; i < fields.length; i++) {
   	    String fieldtemp = fields[i];
   	    hql += fieldtemp + " like ? and ";
   	}
   	for (int i = 0; i < params.length; i++) {
   	    String param = params[i];

   	    hql += param + " =? and ";
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
	String hql = "from " + Name +" q";
	if (params == null || params.length == 0)
	    return getOrderBy(Name, hql);
	hql += " where ";
	for (int i = 0; i < params.length; i++) {
	    String param = params[i];
	    hql += "q." + param + "=:" + param + " and ";
	}
	hql = hql.substring(0, hql.length() - 4);
	return getOrderBy(Name, hql);
    }

    public static String getSelectSql(String tableName, String param) {
	return getSelectSql(tableName, new String[] { param });
    }

    public static String getSelectSql(String tableName, String[] params) {
	String hql = "select * from " + tableName;
	if (params == null || params.length == 0)
	    return getOrderBy(tableName, hql);
	hql += " where ";
	for (int i = 0; i < params.length; i++) {
	    String param = params[i];
	    hql += param + "=? and ";
	}
	hql = hql.substring(0, hql.length() - 4);
	return getOrderBy(tableName,hql);
    }


    public static boolean hasText(String str, String ...strs) {
	if(str == null || str.length()==0)
	    return false;
	if(strs != null && strs.length > 0)
	    return hasText(strs);
	return true;
    }
    
    public static boolean isPositive(int ...strs) {
  	if(strs.length < 1)
  	    return false;
  	for (int i : strs) {
	    if(i < 1)
		return false;
	}
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
    
    public static String getOrderBy(String name, String hql) {
	if(name.equals(ChineseMedicine.CLASS_NAME) || name.equals(WestMedicine.CLASS_NAME) || name.equals(EnterChineseMedicine.CLASS_NAME) || name.equals(EnterWestMedicine.CLASS_NAME))
	    hql += " ORDER BY q.sortKey asc";
	else if(name.equals(ChineseMedicine.TABLE_NAME) || name.equals(WestMedicine.TABLE_NAME) || name.equals(EnterChineseMedicine.CLASS_NAME) || name.equals(EnterWestMedicine.TABLE_NAME))
	    hql += " ORDER BY sortKey asc";
	System.out.println(hql);
	return hql;
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
    
    
    public static String formatData(String data){
	data = data.trim();
	String res_1 = matcherData(data, 1);
	String res_2 = matcherData(res_1, 2);
	String res_3 = matcherData(res_2, 3);
	String res_4 = matcherData(res_3, 4);
	return res_4;
    }
    
    public static String matcherData(String data , int type) {
	String firstReg = "[0-9][\\.][^\\d]";
	String secondReg = "[(][0-9][)]";
	String thirdReg = "贮法：[\u4e00-\u9fa5，\\d℃]+。";
	String forthReg = "\\[国外用法用量参考\\]";
	String resStr = "";
	Matcher matcher;
	if(type == 1)
	    matcher = Pattern.compile(firstReg).matcher(data);
	else if(type == 2){
	    matcher = Pattern.compile(secondReg).matcher(data);
	}
	else if(type == 3){
	    matcher = Pattern.compile(thirdReg).matcher(data);
	}
	else {
	    matcher = Pattern.compile(forthReg).matcher(data);
	}
	int lastIndex = 0;
	while(matcher.find()){
	    if(type == 3){		
		resStr = resStr+data.substring(lastIndex,matcher.start()) + lineSeparator + data.substring(matcher.start(), matcher.end()) + lineSeparator;
		lastIndex = matcher.end();
	    }
	    else {
		resStr = resStr +data.substring(lastIndex,matcher.start()) + lineSeparator;	
		lastIndex = matcher.start();
	    }
	    
	}
	resStr += data.substring(lastIndex,data.length());
	resStr = resStr.trim();
	if(resStr.startsWith(lineSeparator)){
	    resStr = resStr.substring(1);	    
	}
	if(resStr.endsWith(lineSeparator)){
	    resStr = resStr.substring(0,resStr.length());	    
	}
	return resStr;
    }
    

    public static String toPinYin(String words) {
	HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	format.setVCharType(HanyuPinyinVCharType.WITH_V);

	char[] inputs = words.trim().toCharArray();
	String outPut = "";
	try {
	    for (char c : inputs) {
		if (Character.toString(c).matches(REGEX_CHINESE))
		{
		    String[] tamp = PinyinHelper.toHanyuPinyinStringArray(c,
			    format);
			outPut += tamp[0];

		}
		else {
		    outPut += Character.toString(c);
		}
	    }
	} catch (BadHanyuPinyinOutputFormatCombination e) {
	    e.printStackTrace();
	}
	return outPut;
    }

    public static boolean isEmpty(List<?> list) {
	if(list == null || list.size() < 1)
	    return true;
	return false;
    }


    public static String getCountInSql(String tableName, String whereParam, String inParam, Object[] inValue) {
	String sql = "select count(*) from " + tableName + "  where "+inParam+" in (";
	for (int i = 0; i < inValue.length; i++) {
	    sql += "?,";
	}
	sql = sql.substring(0,sql.length()-1)+")";
	if(whereParam != null)
	    sql += " and "+whereParam+" = ?";
	return sql;
    }

}
