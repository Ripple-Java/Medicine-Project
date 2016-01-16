package com.rippletec.medicine.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.utils.DateUtil;
import com.rippletec.medicine.utils.FileUtil;
import com.rippletec.medicine.utils.StringUtil;

@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController{
    
    public static final String USERIMG_SAVEPATH = "/portrait/images";
    public static final String ENTERPRISELOGO_SAVEPATH = "/enterprise/logo";
    public static final String ENTERPRISEIMG_SAVEPATH = "/enterprise/checkImages";
    public static final String ENTERPRISE_PPT_SAVEPATH = "/enterprise/ppt";
    public static final String SEVER_TEMP = "/temp";
    
    @RequestMapping(value = "/image/portrait", method = RequestMethod.POST ,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_uploadPortrait(HttpSession httpSession,
	    @RequestParam("img") CommonsMultipartFile img) {
	if(!userManager.isLogined(httpSession))
	    return jsonUtil.setResultFail().setTip("用户未登录").toJsonString();
	if(!FileUtil.isAllowImg(img.getOriginalFilename()))
	    return jsonUtil.setResultFail().setTip("不允许上传这种图片格式").toJsonString();
	String userAccount = (String) httpSession.getAttribute(User.ACCOUNT);
	User user =  userManager.findByAccount(userAccount);
	String fileName = userAccount + "_" + user.getId() +FileUtil.getSuffixByFilename(img.getOriginalFilename());
	Result res = FileUtil.saveFile(USERIMG_SAVEPATH, img, fileName);
	if(!res.isSuccess())   
	    return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
	user.setCertificateImg((String)res.getMessage());
	userManager.update(user);
	return jsonUtil.setResultSuccess().setJsonObject("imgUrl",res.getMessage()).toJsonString();
    }
    
    @RequestMapping(value = "/image/enterCheckImg", method = RequestMethod.POST ,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_uploadEnterCheckImg(HttpSession httpSession,
	    @RequestParam("img") CommonsMultipartFile img) {
	if(!FileUtil.isAllowImg(img.getOriginalFilename()))
	    return jsonUtil.setResultFail().setTip("不允许上传这种图片格式").toJsonString();
	String fileName =DateUtil.getSimpleDateTime(new Date()) + StringUtil.generateCode(6) + FileUtil.getSuffixByFilename(img.getOriginalFilename());
	Result res = FileUtil.saveFile(ENTERPRISEIMG_SAVEPATH, img, fileName);
	if(!res.isSuccess()){
	    return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
	}
	return jsonUtil.setResultSuccess().setJsonObject("imgUrl",res.getMessage()).toJsonString();
    }
    
    @RequestMapping(value = "/PPTfile", method = RequestMethod.POST ,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_uploadPPT(HttpSession httpSession,
	    @RequestParam("ppt") CommonsMultipartFile ppt) {
	if(!FileUtil.isAllowPPT(ppt.getOriginalFilename()))
	    return jsonUtil.setResultFail().setTip("不允许上传此种格式ppt").toJsonString();
	String fileName =DateUtil.getSimpleDateTime(new Date()) + StringUtil.generateCode(6) + FileUtil.getSuffixByFilename(ppt.getOriginalFilename());
	Result res = FileUtil.saveFile(ENTERPRISE_PPT_SAVEPATH, ppt, fileName);
	if(!res.isSuccess()){
	    return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
	}
	return jsonUtil.setResultSuccess().setJsonObject("pptUrl",res.getMessage()).toJsonString();
    }
    
    @RequestMapping(value = "/image/enterprise/logo", method = RequestMethod.POST ,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_uploadLogo(HttpSession httpSession,
	    @RequestParam("img") CommonsMultipartFile img) throws DaoException {
	if(!userManager.isLogined(httpSession))
	    return jsonUtil.setResultFail().setTip("用户未登录").toJsonString();
	if(!FileUtil.isAllowImg(img.getOriginalFilename()))
	    return jsonUtil.setResultFail().setTip("不允许上传这种图片格式").toJsonString();
	String userAccount = getAccount(httpSession);
	User user =  userManager.findByAccount(userAccount);
	if(user.getType() != User.TYPE_ENTER)
	    return jsonUtil.setResultFail("非企业用户不可上传logo").toJsonString();
	
	String fileName = userAccount + "_" + user.getId() +FileUtil.getSuffixByFilename(img.getOriginalFilename());
	Result res = FileUtil.saveFile(ENTERPRISELOGO_SAVEPATH, img, fileName);
	if(!res.isSuccess()){
	    return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
	}
	int enterpriesId = getEnterpriseId(httpSession);
	Enterprise enterprise = enterpriseManager.find(enterpriesId);
	enterprise.setLogo((String)res.getMessage());
	enterpriseManager.update(enterprise);
	return jsonUtil.setResultSuccess().setJsonObject("imgUrl",res.getMessage()).toJsonString();
    }
    
}
