package com.rippletec.medicine.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

/**
 * @author Liuyi
 *
 */
@Repository(JsonUtil.NAME)
public class JsonUtil {
    public static final String NAME = "JsonUtil";
    
    private Map<String, List<String>> jsonMap = new HashMap<String, List<String>>();
    private Map<String, Object> jsonObjectMap = new HashMap<String, Object>();
    private int nameNumber = 0;
    public static final String LISTNAME_PREFIX = "List_";
    
    public JsonUtil() {
	try {
	    Properties jsonProperties = new Properties();
	    jsonProperties.load(JsonUtil.class.getClassLoader().getResourceAsStream("jsonMapping.properties"));
	    for (Object keyObject : jsonProperties.keySet()) {
		String key = (String) keyObject;
		String value = jsonProperties.getProperty(key);
		jsonMap.put(key, Arrays.asList(value.split(",")));
	    }
	} catch (IOException e) {
	    throw new RuntimeException("读取jsonMapping失败");
	}
    }
    
    public void clear() {
	jsonObjectMap = new HashMap<String, Object>();
	nameNumber  = 0;
    }
    
    public String getModelName(Object object) {
	String name = null;
	if(isModel(object)){
	    try {
		  Class objectClass = object.getClass();
		  Field modelClassName = objectClass.getDeclaredField("CLASS_NAME");
		  name = (String) modelClassName.get(object);
	    } catch (Exception e) {
		return name;
	    }
	}
	return name;
    }
    
    public boolean isConllection(Object object){
	return (object instanceof List || object instanceof Map) ? true : false;
    }
    
    public boolean isModel(Object object) {
	boolean flag = false;
	try {
	    Class objectClass = object.getClass();
	    Field modelClassName = objectClass.getDeclaredField("CLASS_NAME");
	    flag  = true;
	} catch (Exception e) {
	    return flag;
	}
	return flag;
    }
    
    public JsonUtil setJsonObject(String name, Object object) {
	jsonObjectMap.put(name, object);
	return this;
    }
    
    public JsonUtil setModelList(List objects) {
	if(objects != null && objects.size() >0)
	{
	    String name = getModelName(objects.get(0));
	    if (name != null) {
		jsonObjectMap.put(name+"s", objects);
	    }
	    else{
		jsonObjectMap.put(LISTNAME_PREFIX+nameNumber, objects);
		nameNumber++;
	    }
	}
	return this;
    }
    
    public JsonUtil setResult(String res) {
	jsonObjectMap.put("result", res);
	return this;
    }
    
    public JsonUtil setResultFail() {
	jsonObjectMap.put("result", "fail");
	return this;
    }
    
    public JsonUtil setResultSuccess() {
	jsonObjectMap.put("result", "success");
	return this;
    }
   
    
    public JsonUtil setTip(String tip) {
	jsonObjectMap.put("tip", tip);
	return this;
    }
    
    public String toJsonString() {
	String jssonString =  JSON.toJSONString(jsonObjectMap);
	clear();
	return jssonString;
    }
    
    public String toJsonString(final String url){
	return toJsonString(url, jsonObjectMap);
    }
    
    public String toJsonString(final String url, Object jsonObject){
	if (!jsonMap.containsKey(url))
	    return "[]";
	String jsonString = JSON.toJSONString(jsonObject, new PropertyPreFilter() {
	    
	    @Override
	    public boolean apply(JSONSerializer arg0, Object object, String name) {
		if(isConllection(object))
		    return true;
		if (isModel(object))
		    return jsonMap.get(url).contains(name);
		else
		    return true;
	    }
	});
	clear();
	return jsonString;
    }

}
