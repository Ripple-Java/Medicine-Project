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

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cloopen.rest.sdk.utils.LoggerUtil;
import com.rippletec.medicine.exception.UtilException;

/**
 * Json格式化生成工具
 * 
 * @author Liuyi
 *
 */
@Repository(JsonUtil.NAME)
public class JsonUtil {
    public static final String NAME = "JsonUtil";
    
    private Hashtable<String, List<String>> jsonMap = new Hashtable<String, List<String>>();
    
    //注意线程安全
    private ThreadLocal<HashMap<String, Object>>  jsonObjectMapTL =  new ThreadLocal<HashMap<String, Object>>();
    private ThreadLocal<Object>  jsonObjecTL =  new ThreadLocal<Object>();
    private int nameNumber = 0;
    public static final String LISTNAME_PREFIX = "List_";
    
   
    
    private ErrorCode errorCode = new ErrorCode();
    
    public JsonUtil() throws UtilException{
	Properties jsonProperties = new Properties();
	try {
	    jsonProperties.load(JsonUtil.class.getClassLoader().getResourceAsStream("jsonMapping.properties"));
	} catch (IOException e) {
	    Logger.getLogger(JsonUtil.class).error(StringUtil.getLoggerInfo(ErrorCode.FILE_REDA_ERROR, "jsomMapping 配置文件读取失败"));
	    throw new UtilException(ErrorCode.INTENAL_ERROR);
	}
	for (Object keyObject : jsonProperties.keySet()) {
	    String key = (String) keyObject;
	    String value = jsonProperties.getProperty(key);
	    jsonMap.put(key, Arrays.asList(value.split(",")));
	    JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
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
    
    public boolean isListConllection(Object object){
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
    
    public JsonUtil setObject(String name, Object object) {
	initJsonMap();
	jsonObjectMapTL.get().put(name, object);
	return this;
    }
    
    public JsonUtil setModels(List objects) {
	if(objects != null && objects.size() >0)
	{
	    initJsonMap();
	    String name = getModelName(objects.get(0));
	    if (name != null) {
		jsonObjectMapTL.get().put(name+"s", objects);
	    }
	    else{
		jsonObjectMapTL.get().put(LISTNAME_PREFIX+nameNumber, objects);
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
    
    public JsonUtil setFailRes(String tip) {
	initJsonMap();
	jsonObjectMapTL.get().put("result", "fail");
	setTip(tip);
	return this;
    }
    
    public JsonUtil setFailRes(int code) {
	initJsonMap();
	jsonObjectMapTL.get().put("result", "fail");
	jsonObjectMapTL.get().put("errorCode", code);
	jsonObjectMapTL.get().put("info", errorCode.getInfo(code));
	return this;
    }
    
    public JsonUtil setSuccessRes() {
	initJsonMap();
	jsonObjectMapTL.get().put("result", "success");
	return this;
    }
   
    
    public JsonUtil setTip(String tip) {
	initJsonMap();
	jsonObjectMapTL.get().put("tip", tip);
	return this;
    }
    
    public String toJson() {
	initJsonMap();
	String jssonString =  JSON.toJSONString(jsonObjectMapTL.get(),SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
	clear();
	return jssonString;
    }
    
    
    
    public String toJson(final String url) throws UtilException{
	return toJson(url, jsonObjectMapTL.get());
    }
  
    public String toJson(final String url, boolean ignored) throws UtilException{
   	return toJsonIgnoredString(url, jsonObjectMapTL.get());
    }
    
    public String toJson(final String url, Object jsonObject) throws UtilException{
	if (!jsonMap.containsKey(url))
	    throw new UtilException(ErrorCode.JSONUTIL_NOT_MAPPING_ERROR);
	jsonObjecTL.set(jsonObject);
	PropertyPreFilter prepPropertyPreFilter = new PropertyPreFilter() {
	    @Override
	    public boolean apply(JSONSerializer arg0, Object object, String name) {
		if(isListConllection(object))
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

    public String toJsonIgnoredString(final String url, Object jsonObject) throws UtilException{
	if (!jsonMap.containsKey(url))
	    throw new UtilException(ErrorCode.JSONUTIL_NOT_MAPPING_ERROR);
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
