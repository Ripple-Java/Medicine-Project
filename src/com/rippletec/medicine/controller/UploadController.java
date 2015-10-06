package com.rippletec.medicine.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.utils.DateUtil;
import com.rippletec.medicine.utils.FileUtil;
import com.rippletec.medicine.utils.StringUtil;

@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController{
    
    public static final String USERIMG_SAVEPATH = "/portrait/images";
    public static final String ENTERPRISEIMG_SAVEPATH = "/enterprise/checkImages";
    
    @RequestMapping(value = "/image/portrait", method = RequestMethod.POST)
    @ResponseBody
    public String user_uploadPortrait(HttpSession httpSession,
	    @RequestParam("pic") CommonsMultipartFile pic) {
	if(!userManager.isLogined(httpSession))
	    return jsonUtil.setResultFail().setTip("用户未登录").toJsonString();
	if(!FileUtil.isAllowImg(pic.getOriginalFilename()))
	    return jsonUtil.setResultFail().setTip("不允许上传这种图片格式").toJsonString();
	String userAccount = (String) httpSession.getAttribute(User.ACCOUNT);
	User user =  userManager.findByAccount(userAccount);
	String fileName = userAccount + "_" + user.getId() +FileUtil.getSuffixByFilename(pic.getOriginalFilename());
	Result res = FileUtil.saveFile(USERIMG_SAVEPATH, pic, fileName);
	if(!res.isSuccess()){
	    user.setCertificateImg(res.getMessage());
	    userManager.update(user);
	    return jsonUtil.setResultFail().setTip(res.getMessage()).toJsonString();
	}
	return jsonUtil.setResultSuccess().setJsonObject("imgUrl",res.getMessage()).toJsonString();
    }
    
    @RequestMapping(value = "/image/enterCheckImg", method = RequestMethod.POST)
    @ResponseBody
    public String user_uploadEnterCheckImg(HttpSession httpSession,
	    @RequestParam("pic") CommonsMultipartFile pic) {
	if(!FileUtil.isAllowImg(pic.getOriginalFilename()))
	    return jsonUtil.setResultFail().setTip("不允许上传这种图片格式").toJsonString();
	String fileName =StringUtil.generateCode(6) + DateUtil.getDateTime(new Date()) +FileUtil.getSuffixByFilename(pic.getOriginalFilename());
	Result res = FileUtil.saveFile(ENTERPRISEIMG_SAVEPATH, pic, fileName);
	if(!res.isSuccess()){
	    return jsonUtil.setResultFail().setTip(res.getMessage()).toJsonString();
	}
	return jsonUtil.setResultSuccess().setJsonObject("imgUrl",res.getMessage()).toJsonString();
    }
    

}
