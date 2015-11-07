package com.rippletec.medicine.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rippletec.medicine.bean.Result;

public class FileUtil {
    
    
    public static final String NAME = "FileUtil";
    
    
    public static String pptHtml;
    
    public FileUtil() {
	 pptHtml = "";
	 String classPath = this.getClass().getClassLoader().getResource("/").getPath();
	 String rootPath = classPath.substring(0, classPath.indexOf("WEB-INF/classes"));
	 File pptHtmlFile = new File(rootPath+File.separator+"pptshow"+File.separator+"showpptIndex.html");
	try {
	    
	    BufferedReader reader = new BufferedReader(new FileReader(pptHtmlFile));
	    String data = null;
	    data = reader.readLine();
	    while (data != null) {
		pptHtml += data;
		pptHtml += "\n";
		data = reader.readLine();
	    }
	} catch (IOException e) {
	    LoggerUtil.UtilLogger.error("读取ppt页面失败");
	}
    }
    
    
    private enum allowImg{
	JPEG, JPG, PNG, BMP;
    }
    
    private enum allowPPT{
   	PPT,PPTX;
    }

    /**
     * 根据给定的文件名,获取其后缀信息
     * 
     * @param filename
     * @return
     */
    public static String getSuffixByFilename(String filename) {

	return filename.substring(filename.lastIndexOf(".")).toLowerCase();

    }
    public static String getSimpleFilename(String filename) {

   	return filename.substring(0,filename.lastIndexOf("."));

       }
    
    public static String getRootPath() {
	return System.getProperty("medicine.root");
    }

    public static Result saveFile(String savePath, CommonsMultipartFile file, String fileName) {
	String rootPath = getRootPath();
	File saveFileDir = new File(rootPath + savePath);
	if (!saveFileDir.exists()) {
	    saveFileDir.mkdirs();
	}
	File saveImg = new File(saveFileDir, fileName);
	try {
	    file.transferTo(saveImg);
	} catch (IllegalStateException | IOException e) {
	    LoggerUtil.UtilLogger.debug(FileUtil.NAME+":文件写入失败");
	    return new Result(false, "文件写入失败");
	}
	return new Result(true, savePath+"/"+fileName);

    }
    
    public static Boolean isAllowImg(String fileName) {
	String suffix = getSuffixByFilename(fileName);
	try {
	    allowImg.valueOf(suffix.substring(1).toUpperCase());	    
	} catch (Exception e) {
	   return false;
	}
	return true;
    }
    
    public static Boolean isAllowPPT(String fileName) {
	String suffix = getSuffixByFilename(fileName);
	try {
	    allowPPT.valueOf(suffix.substring(1).toUpperCase());	    
	} catch (Exception e) {
	   return false;
	}
	return true;
    }

}
