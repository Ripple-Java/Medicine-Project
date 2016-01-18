package com.rippletec.medicine.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rippletec.medicine.bean.Result;

@Repository(FileUtil.NAME)
public class FileUtil {
    
    
    public static final String NAME = "FileUtil";
    
    
    public static String pptHtml;
    public static String shareHtml;
    
    public FileUtil() {
	 pptHtml = "";
	 shareHtml = "";
	 String classPath = this.getClass().getClassLoader().getResource("/").getPath();
	 String rootPath = classPath.substring(0, classPath.indexOf("WEB-INF/classes"));
	 File pptHtmlFile = new File(rootPath+File.separator+"pptshow"+File.separator+"showpptIndex.html");
	 File shareHtmlFile = new File(rootPath+File.separator+"SharePage"+File.separator+"shareMedicine.html");
	 if(!pptHtmlFile.exists() || !shareHtmlFile.exists()){
	     Logger.getLogger(FileUtil.class).error(ErrorCode.FILE_NOT_EXISTED_ERROR+" info；分享页面或分享PPT页面不存在");
	 }
	 BufferedReader PPTReader = null;
	 BufferedReader ShareReader = null;
	 
	try {
	    
	    PPTReader = new BufferedReader(new FileReader(pptHtmlFile));
	    String data = null;
	    data = PPTReader.readLine();
	    while (data != null) {
		pptHtml += data;
		pptHtml += "\n";
		data = PPTReader.readLine();
	    }
	    ShareReader = new BufferedReader(new FileReader(shareHtmlFile));
	    String sharedata = null;
	    sharedata = ShareReader.readLine();
	    while (sharedata != null) {
		shareHtml += sharedata;
		shareHtml += "\n";
		sharedata = ShareReader.readLine();
	    }
	} catch (IOException e) {
	    Logger.getLogger(FileUtil.class).error(ErrorCode.FILE_REDA_ERROR+" info: 读取分享页面失败");
	}finally{
	    if(PPTReader != null){
		try {
		    PPTReader.close();
		} catch (IOException e) {
		    Logger.getLogger(FileUtil.class).error(ErrorCode.FILE_CLOSE_ERROR+" info: 关闭PPTReader失败");
		    e.printStackTrace();
		}
	    }
	    if(ShareReader != null){
		try {
		    ShareReader.close();
		} catch (IOException e) {
		    Logger.getLogger(FileUtil.class).error(ErrorCode.FILE_CLOSE_ERROR+" info: 关闭ShareReader失败");
		    e.printStackTrace();
		}
	    }
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
    /**
     * 获取文件名不带格式
     * @param filename
     * @return
     */
    public static String getSimpleFilename(String filename) {

   	return filename.substring(0,filename.lastIndexOf("."));

       }
    
    /**
     * 获取项目根目录
     * @return
     */
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
	    Logger.getLogger(FileUtil.class).error(ErrorCode.FILE_WRITE_ERROR+" info: "+fileName+"写入失败！");
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
