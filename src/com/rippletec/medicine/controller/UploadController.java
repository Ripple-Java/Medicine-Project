package com.rippletec.medicine.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rippletec.medicine.utils.DateUtil;
import com.rippletec.medicine.utils.StringUtil;

@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController{
    
    public static final String IMG_SAVEPATH = "/upload/images";
    
    @RequestMapping("/image")
    @ResponseBody
    public String user_uploadPicture(@RequestParam("pic") CommonsMultipartFile pic) {
	String rootPath = UploadController.class.getClassLoader().getResource("/").getPath();
	File saveFileDir = new File(rootPath.substring(0, rootPath.indexOf("WEB-INF/classes"))+IMG_SAVEPATH);
	if (!saveFileDir.exists()) {
	    saveFileDir.mkdirs();
	}
	String fileName = DateUtil.getSimpleDateTime(new Date()) + StringUtil.getSuffixByFilename(pic.getOriginalFilename());
	File saveImg = new File(saveFileDir, fileName);
	try {
	    pic.transferTo(saveImg);
	} catch (IllegalStateException | IOException e) {
	    e.printStackTrace();
	    return jsonUtil.setResultFail().setTip("写入文件失败").toJsonString();
	}
	return jsonUtil.setResultSuccess().setJsonObject("imgUrl",fileName).toJsonString();
    }
    

}
