package com.rippletec.medicine.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Json格式化生成工具
 * 
 * @author Liuyi
 *
 */
@Repository(JsonUtil.NAME)
public class JsonUtil {
    public static final String NAME = "JsonUtil";
    
    //使用ConcurrentHashMap，保证线程安全
    private Hashtable<String, List<String>> jsonMap = new Hashtable<String, List<String>>();
    private ThreadLocal<HashMap<String, Object>>  jsonObjectMapTL =  new ThreadLocal<HashMap<String, Object>>();
    private ThreadLocal<Object>  jsonObjecTL =  new ThreadLocal<Object>();
    private int nameNumber = 0;
    public static final String LISTNAME_PREFIX = "List_";
   
    
    private ErrorCode errorCode = new ErrorCode();
    
    public JsonUtil() {
	try {
	    Properties jsonProperties = new Properties();
	    jsonProperties.load(JsonUtil.class.getClassLoader().getResourceAsStream("jsonMapping.properties"));
	    for (Object keyObject : jsonProperties.keySet()) {
		String key = (String) keyObject;
		String value = jsonProperties.getProperty(key);
		jsonMap.put(key, Arrays.asList(value.split(",")));
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
	    }
	} catch (IOException e) {
	    throw new RuntimeException("读取jsonMapping失败");
	}
	
    }
    
    public void clear() {
	if(jsonObjectMapTL.get()!=null)
	    jsonObjectMapTL.get().clear();
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
	initJsonMap();
	jsonObjectMapTL.get().put(name, object);
	return this;
    }
    
    public JsonUtil setModelList(List objects) {
	List saveList = new CopyOnWriteArrayList(objects); 
	if(objects != null && objects.size() >0)
	{
	    initJsonMap();
	    String name = getModelName(objects.get(0));
	    if (name != null) {
		jsonObjectMapTL.get().put(name+"s", objects);
	    }
	    else{
		jsonObjectMapTL.get().put(LISTNAME_PREFIX+nameNumber, saveList);
		nameNumber++;
	    }
	}
	return this;
    }
    
    public JsonUtil setResult(String res) {
	initJsonMap();
	jsonObjectMapTL.get().put("result", res);
	return this;
    }
    
    public JsonUtil setResultFail() {
	initJsonMap();
	jsonObjectMapTL.get().put("result", "fail");
	return this;
    }
    public JsonUtil setResultFail(String tip) {
	initJsonMap();
	jsonObjectMapTL.get().put("result", "fail");
	setTip(tip);
	return this;
    }
    
    public JsonUtil setResultFail(int code) {
	initJsonMap();
	jsonObjectMapTL.get().put("result", "fail");
	jsonObjectMapTL.get().put("errorCode", code);
	jsonObjectMapTL.get().put("info", errorCode.getInfo(code));
	return this;
    }
    
    public JsonUtil setResultSuccess() {
	initJsonMap();
	jsonObjectMapTL.get().put("result", "success");
	return this;
    }
   
    
    public JsonUtil setTip(String tip) {
	initJsonMap();
	jsonObjectMapTL.get().put("tip", tip);
	return this;
    }
    
    public String toJsonString() {
	initJsonMap();
	String jssonString =  JSON.toJSONString(jsonObjectMapTL.get(),SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
	clear();
	return jssonString;
    }
    
    
    
    public String toJsonString(final String url){
	return toJsonString(url, jsonObjectMapTL.get());
    }
  
    public String toJsonString(final String url, boolean ignored){
   	return toJsonIgnoredString(url, jsonObjectMapTL.get());
    }
    
    public String toJsonString(final String url, Object jsonObject){
	if (!jsonMap.containsKey(url))
	    return "[]";
	jsonObjecTL.set(jsonObject);
	PropertyPreFilter prepPropertyPreFilter = new PropertyPreFilter() {
	    @Override
	    public boolean apply(JSONSerializer arg0, Object object, String name) {
		if(isConllection(object))
		    return true;
		if (isModel(object))
		    return jsonMap.get(url).contains(name);
		else
		    return true;
	    }
	};
	String jsonString = JSON.toJSONString(jsonObjecTL.get(), prepPropertyPreFilter,
		SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
	clear();
	return jsonString;
    }

    public String toJsonIgnoredString(final String url, Object jsonObject){
	if (!jsonMap.containsKey(url))
	    return "[]";
	jsonObjecTL.set(jsonObject);
	String jsonString = JSON.toJSONString(jsonObjectMapTL.get(), new PropertyPreFilter() {
	    
	    @Override
	    public boolean apply(JSONSerializer arg0, Object object, String name) {
		if (object instanceof Set || isModel(object))
		    return !jsonMap.get(url).contains(name);
		else
		    return true;
	    }
	},SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
	clear();
	return jsonString;
    }
    
    public void initJsonMap(){
	if(jsonObjectMapTL.get() == null){
	    jsonObjectMapTL.set(new HashMap<String, Object>());
	}
    }

}
