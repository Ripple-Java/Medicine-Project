package com.rippletec.medicine.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.utils.DateUtil;
import com.rippletec.medicine.utils.FileUtil;
import com.rippletec.medicine.utils.StringUtil;

@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController{
    
    public static final String IMG_SAVEPATH = "/portrait/images";
    
    @RequestMapping("/image/portrait")
    @ResponseBody
    public String user_uploadPortrait(HttpSession httpSession,
	    @RequestParam("pic") CommonsMultipartFile pic) {
	if(!FileUtil.isAllowImg(pic.getOriginalFilename()))
	    return jsonUtil.setResultFail().setTip("不允许上传这种图片格式").toJsonString();
	Result res = FileUtil.saveFile(IMG_SAVEPATH, pic);
	if(!res.isSuccess())
	    return jsonUtil.setResultFail().setTip(res.getMessage()).toJsonString();
	return jsonUtil.setResultSuccess().setJsonObject("imgUrl",res.getMessage()).toJsonString();
    }
    

}
