package com.rippletec.medicine.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rippletec.medicine.bean.Result;

public class FileUtil {
    
    private enum allowImg{
	JPEG, JPG, PNG, BMP, SVG, GIF;
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
    
    public static String getRootPath() {
	String path = FileUtil.class.getClassLoader().getResource("/").getPath();
	return path.substring(0,path.indexOf("WEB-INF/classes"));
    }

    public static Result saveFile(String savePath, CommonsMultipartFile file) {
	String rootPath = getRootPath();
	File saveFileDir = new File(rootPath + savePath);
	if (!saveFileDir.exists()) {
	    saveFileDir.mkdirs();
	}
	String fileName = DateUtil.getSimpleDateTime(new Date())
		+ getSuffixByFilename(file.getOriginalFilename());
	File saveImg = new File(saveFileDir, fileName);
	try {
	    file.transferTo(saveImg);
	} catch (IllegalStateException | IOException e) {
	    e.printStackTrace();
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

}
