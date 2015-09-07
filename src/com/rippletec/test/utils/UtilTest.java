package com.rippletec.test.utils;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.rippletec.medicine.model.BaseModel;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.utils.JsonUtil;
import com.rippletec.medicine.utils.StringUtil;

public class UtilTest {
    
    @Test
    public void testStringUttil() throws Exception {
	System.out.println(StringUtil.getSelectHql(Enterprise.CLASS_NAME));
	System.out.println(StringUtil.getSelectHql(Enterprise.CLASS_NAME, new String[]{}));
	System.out.println(StringUtil.getSelectHql(Enterprise.CLASS_NAME, new String[]{Enterprise.TYPE}));
	System.out.println(StringUtil.getSelectHql(Enterprise.CLASS_NAME, new String[]{Enterprise.TYPE,Enterprise.PHONE}));
    }
    
    @Test
    public void testJsonUtil() throws Exception {
	String url = "/enterprise";
	String tip = "success";
	Enterprise enterprise = new Enterprise(Enterprise.FOREIGN, "jsonUtiltest", "logo", "prhont", "email");
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

}
