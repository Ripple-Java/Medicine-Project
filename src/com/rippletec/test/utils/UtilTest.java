package com.rippletec.test.utils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.rippletec.medicine.SMS.SMS;
import com.rippletec.medicine.model.BaseModel;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.utils.DateUtil;
import com.rippletec.medicine.utils.JsonUtil;
import com.rippletec.medicine.utils.MD5Util;
import com.rippletec.medicine.utils.StringUtil;

public class UtilTest {
    
    @Test
    public void testGetNumber() throws Exception {
	for (int i = 0; i < 20; i++) {
	    System.out.println(StringUtil.generateCode(6));
	    System.out.println(StringUtil.getAccount());
	}
    }
    
    @Test
    public void testJsonUtil() throws Exception {
	String url = "/enterprise";
	String tip = "success";
	Enterprise enterprise = new Enterprise();
	JsonUtil jsonUtil = new JsonUtil();
	List<Object> list = new LinkedList<Object>();
	list.add(enterprise);
	list.add(tip);
	System.out.println(jsonUtil.toJsonString(url, list));
	list.remove(1);
	jsonUtil.clear();
	System.out.println(jsonUtil.setModelList(list).setResultSuccess().toJsonString(url));
	
    }
    
    @Test
    public void testReflect() throws Exception {
	BaseModel model = new Enterprise();
	Class class1 = model.getClass();
	Field field = class1.getDeclaredField("DJFLSKD");
	if(field != null)
	    System.out.println(field.get(model));
    }
    
    @Test
    public void testSMS() throws Exception {
	SMS sms = new SMS();
	sms.send("15622739759", "123456","1");
    }
    
    @Test
    public void testStringUttil() throws Exception {
	System.out.println(StringUtil.getSelectHql(Enterprise.CLASS_NAME));
	System.out.println(StringUtil.getSelectHql(Enterprise.CLASS_NAME, new String[]{}));
	System.out.println(StringUtil.getSelectHql(Enterprise.CLASS_NAME, new String[]{Enterprise.TYPE}));
	System.out.println(StringUtil.getSelectHql(Enterprise.CLASS_NAME, new String[]{Enterprise.TYPE,Enterprise.PHONE}));
    }
    
    @Test
    public void testMD5() throws Exception {
	System.out.println(MD5Util.validPasswd("aabbcc12345", "AE484EF236FB6B6F904E11AB91C0932C65C1C678282CC8FFF0DD477D"));
    }
    
    @Test
    public void testDate() throws Exception {
	Date date = DateUtil.getYearMonthDate(new Date());
	System.out.println(DateUtil.getDateTime(date));
    }

}
