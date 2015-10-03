package com.rippletec.medicine.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rippletec.medicine.SMS.SMS;
import com.rippletec.medicine.bean.DBLogEntity;
import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.DBLog;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.FeedBackMass;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineDocument;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.UserFavorite;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.DBLoger;
import com.rippletec.medicine.service.VideoManager;
import com.rippletec.medicine.utils.StringUtil;

/**
 * @author Liuyi
 *
 */
@Controller
@RequestMapping("/App")
public class AppController extends BaseController {

    /**
     * form表单提交 Date类型数据绑定 <功能详细描述>
     * 
     * @param binder
     * @see [类、类#方法、类#成员]
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	dateFormat.setLenient(false);
	binder.registerCustomEditor(Date.class, new CustomDateEditor(
		dateFormat, true));
    }

    @RequestMapping(value = "/user/addFavorite", method = RequestMethod.POST)
    @ResponseBody
    public String user_addUserFavorite(HttpServletRequest request,UserFavorite userFavorite){
    	String account= (String) request.getSession().getAttribute(User.ACCOUNT);
    	if(userFavoriteManager.addUserFavorite(account, userFavorite))
    		return jsonUtil.setResultSuccess().toJsonString();
    	else {
    		return jsonUtil.setResultFail().setTip("用户未登录").toJsonString();
		}
    }
    
    @RequestMapping(value = "/user/getFavorite", method = RequestMethod.GET)
    @ResponseBody
    public String user_addUserFavorite(HttpSession httpSession){
    	if(userManager.isLogined(httpSession)){
    	    String account = getAccount(httpSession);
    	    return jsonUtil.setModelList(userFavoriteManager.findByAccount(account)).toJsonString();
    	}
    	return jsonUtil.setResultFail().setTip("用户未登录").toJsonString();
    }
    
    
    @RequestMapping(value = "/user/addFeedBack", method = RequestMethod.POST)
    @ResponseBody
    public String user_addFeedBackMass(HttpServletRequest request,FeedBackMass feedBackMass){
    	String account= (String) request.getSession().getAttribute(User.ACCOUNT);
    	if(feedBackMassManager.addFeedBackMass(feedBackMass, account))
    		return jsonUtil.setResultSuccess().toJsonString();
    	else {
    		return jsonUtil.setResultFail().setTip("用户未登录").toJsonString();
		}
    }
    
    @RequestMapping(value = "/user/getAllSearch", method = RequestMethod.GET)
    @ResponseBody
    public String user_getAllSearch(
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
	if (keyword.length() > 0) {
	    jsonUtil.setModelList(westMedicineManager.search(WestMedicine.NAME, keyword))
		    .setModelList(chineseMedicineManager.search(ChineseMedicine.NAME,keyword))
		    .setModelList(enterChineseMedicineManager.search(EnterChineseMedicine.NAME, keyword))
		    .setModelList(enterWestMedicineManager.search(EnterWestMedicine.NAME, keyword))
		    .setModelList(medicineTypeManager.search(MedicineType.NAME,keyword))
		    .setModelList(enterpriseManager.search(Enterprise.NAME, keyword))
		    .setModelList(meetingManager.search(Meeting.NAME, keyword))
		    .setModelList(videoManager.search(Video.NAME,keyword))
		    .setModelList(medicineDocumentManager.search(MedicineDocument.TITLE, keyword));
	    return jsonUtil.setResultSuccess().toJsonString(
		    "/user/getAllSearch");
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }

    @RequestMapping(value = "/user/getEnterprise", method = RequestMethod.GET)
    @ResponseBody
    public String user_getEnterprise(
	    @RequestParam(value = "type", required = false, defaultValue = "0") int type,
	    @RequestParam(value = "size", required = false, defaultValue = "0") int size,
	    @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
	if (type > 0 && size > 0 && currentPage > 0) {
	    List<Enterprise> enterprises = enterpriseManager.getEnterprise(
		    size, type, currentPage);
	    return jsonUtil.setModelList(enterprises).toJsonString(
		    "/user/getEnterprise");
	}
	return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/user/getEnterMedicine", method = RequestMethod.GET)
    @ResponseBody
    public String user_getEnterpriseMedicine(
	    @RequestParam(value = "id", required = false, defaultValue = "-1") int id,
	    @RequestParam(value = "type", required = false, defaultValue = "-1") int type) {
	if (type >= 0) {
	    Object jsonObject = null;
	    if(type == Medicine.ENTER_CHINESE)
		jsonObject = enterChineseMedicineManager.find(id);
	    else if (type == Medicine.ENTER_WEST)
		jsonObject = enterWestMedicineManager.find(id);
	    return jsonUtil.setResultSuccess().setJsonObject("medicine", jsonObject).toJsonString("/user/getEnterMedicine");
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }

    @RequestMapping(value = "/user/getEnterMedicineType", method = RequestMethod.GET)
    @ResponseBody
    public String user_getEnterpriseMedicineType(
	    @RequestParam(value = "id", required = false, defaultValue = "-1") int id) {
	if (id >= 0) {
	    List<EnterpriseMedicineType> enterpriseMedicineTypes = enterpriseMedicineTypeManager
		    .getTypesByEnterpriseId(id);
	    return jsonUtil.setModelList(enterpriseMedicineTypes).toJsonString(
		    "/user/getEnterMedicineType");
	}
	return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/user/getMedicine", method = RequestMethod.GET)
    @ResponseBody
    public String user_getMedicine(
	    @RequestParam(value = "typeId", required = false, defaultValue = "0") int typeId,
	    @RequestParam(value = "size", required = false, defaultValue = "0") int size,
	    @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
	if (typeId >= 0) {
	    Map<String, Object> res = medicineTypeManager.getMedicineByTypeId(
		    typeId, new PageBean(currentPage, 0, size));
	    if (res == null)
		return "{}";
	    return jsonUtil.setModelList((List) res.get("medicines"))
		    .setJsonObject("type", res.get("type"))
		    .toJsonString("/user/getMedicine");
	}
	return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/user/getMedicineDom", method = RequestMethod.GET)
    @ResponseBody
    public String user_getMedicineDom(
	    @RequestParam(value = "medicineId", required = false, defaultValue = "0") int medicineId,
	    @RequestParam(value = "type", required = false, defaultValue = "0") int type) {
	if (type > 0 && medicineId > 0) {
	    List<MedicineDocument> medicineDocuments = medicineDocumentManager
		    .getDocument(medicineId, type);
	    return jsonUtil.setModelList(medicineDocuments).toJsonString(
		    "/user/getMedicineDom");
	}
	return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/user/getMedicineType", method = RequestMethod.GET)
    @ResponseBody
    public String user_getMedicineType(
	    @RequestParam(value = "parentId", required = false, defaultValue = "-1") int parentId) {
	if (parentId >= -1) {
	    List<MedicineType> medicineTypes = medicineTypeManager
		    .getTypeByParentId(parentId);
	    return jsonUtil.setModelList(medicineTypes).toJsonString(
		    "/user/getMedicineType");
	}
	return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/user/getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public String user_getUserInfo(
	    HttpSession httpSession) {
	    if (userManager.isLogined(httpSession))
		return jsonUtil.setJsonObject("User",
			userManager.findByAccount((String) httpSession.getAttribute(User.ACCOUNT))).toJsonString(
			"/user/getUserInfo");
	    return jsonUtil.setResultFail().setTip("用户未登录").toJsonString();
    }

    @RequestMapping(value = "/user/getVerificationCode", method = RequestMethod.GET)
    @ResponseBody
    public String user_getVerificationCode(
	    HttpSession httpSession,
	    @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
	    @RequestParam(value = "type", required = false, defaultValue = "0") int type) {
	if (StringUtil.isMobile(phoneNumber)) {
	    if (type<=1 && userManager.isExist(phoneNumber))
		return jsonUtil.setResultFail().setJsonObject("tip", "此账号以存在")
			.toJsonString();
	    String VerificationCode = StringUtil.generateCode(6);
	    String timeLimit = "1";
	    SMS sms = new SMS();
	    String res = "";
	    switch (type) {
	    case 0:
		res = sms.send(phoneNumber, VerificationCode, timeLimit);
		break;
	    // 待定，可用多种模板
	    case 1:
		res = sms.send(phoneNumber, VerificationCode, timeLimit);
		break;
		
	    case 2:
		res = sms.send(phoneNumber, VerificationCode, timeLimit, "1");
		break;
	    default:
		res = sms.send(phoneNumber, VerificationCode, timeLimit);
		break;
	    }
	    if (res.equals("success")) {
		httpSession.setAttribute("phoneNumber", phoneNumber);
		httpSession.setAttribute("code", VerificationCode);
		httpSession.setAttribute("type", type);
		httpSession.setAttribute("verify", false);
		httpSession
			.setMaxInactiveInterval((new Integer(timeLimit)) * 60);
		jsonUtil.setResultSuccess()
			.setJsonObject("time", timeLimit)
			.setJsonObject("sessionid", httpSession.getId());
	    } else
		jsonUtil.setResultFail().setTip(res);
	} else
	    jsonUtil.setResultFail().setJsonObject("tip", "手机号码格式错误");
	return jsonUtil.toJsonString();
    }

    @RequestMapping(value = "/user/setDocInfo", method = RequestMethod.GET)
    @ResponseBody
    public String user_setDocInfo(
	    HttpSession httpSession,
	    @RequestParam(value = "name", required = false, defaultValue = "") String name,
	    @RequestParam(value = "hospital", required = false, defaultValue = "") String hospital,
	    @RequestParam(value = "office", required = false, defaultValue = "") String office,
	    @RequestParam(value = "officePhone", required = false, defaultValue = "") String officePhone,
	    @RequestParam(value = "profession", required = false, defaultValue = "") String profession) {
	if (StringUtil.hasText(new String[] { name, hospital, office,officePhone, profession })) {
	    if (userManager.isLogined(httpSession)) {
		if (doctorManager.setInfo((String) httpSession.getAttribute(User.ACCOUNT), name, hospital, office,officePhone, profession))
		    jsonUtil.setResultSuccess();
		else
		    jsonUtil.setResultFail();
		return jsonUtil.toJsonString();
	    } else
		return jsonUtil.setResultFail().setTip("用户未登录").toJsonString();
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public String user_setLogin(
	    HttpSession httpSession,
	    @RequestParam(value = "account", required = false, defaultValue = "") String account,
	    @RequestParam(value = "password", required = false, defaultValue = "") String password,
	    @RequestParam(value = "device", required=false, defaultValue="-1") int device) {
	if (StringUtil.isMobile(account) && StringUtil.hasText(password) && device>-1) {
	    if (userManager.isLogined(httpSession))
		return jsonUtil.setResultFail().setTip("账号已登录，请勿重复登陆").toJsonString();
	    if (userManager.userLogin(account, password, device ,httpSession))
		jsonUtil.setResultSuccess().setJsonObject("sessionid", httpSession.getId());
	    else
		jsonUtil.setResultFail().setTip("用户名或密码错误");
	} else
	    jsonUtil.setResultFail().setTip("参数错误");
	return jsonUtil.toJsonString();
    }
    
    @RequestMapping(value = "/user/loginOut", method = RequestMethod.GET)
    @ResponseBody
    public String user_setLoginOut(
	    HttpSession httpSession) {
	    if (userManager.isLogined(httpSession)){
		if(userManager.loginOut((String) httpSession.getAttribute(User.ACCOUNT),httpSession))
		    return jsonUtil.setResultSuccess().toJsonString();
		else return jsonUtil.setResultFail().toJsonString();
	    }
	    else
		jsonUtil.setResultFail().setTip("用户未登录");
	return jsonUtil.toJsonString();
    }

    @RequestMapping(value = "/user/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public String user_verifyCode(
	    HttpSession httpSession,
	    @RequestParam(value = "code", required = false, defaultValue = "") String code) {
	Object typeAttr = httpSession.getAttribute("type"); 
	if (typeAttr == null)
	     return jsonUtil.setResultFail().setTip("非法验证").toJsonString();
	int type = (int) typeAttr;
	if (StringUtil.hasText(code)) {
	    String tempCode = (String) httpSession.getAttribute("code");
	    if (tempCode!=null && code.equals(tempCode)){
		httpSession.setAttribute("verify", true);
		httpSession
		.setMaxInactiveInterval(10 * 60);
		jsonUtil.setResultSuccess();
	    }
	    else
		jsonUtil.setResultFail().setJsonObject("tip", "验证码错误或已过期");
	} else
	    jsonUtil.setResultFail().setJsonObject("tip", "参数错误");
	return jsonUtil.toJsonString();
    }
    
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public String user_setRegister(
	    HttpSession httpSession,
	    @RequestParam(value = "password", required = false, defaultValue = "") String password,
	    @RequestParam(value = "device", required = false, defaultValue = "0") int device) {
	if (StringUtil.hasText(password) && device>=User.DRVICE_OTHER && device<User.DRVICE_IPHONE) {
	    Object phoneAttr = httpSession.getAttribute("phoneNumber");
	    Object flagAttr = httpSession.getAttribute("verify");
	    if (phoneAttr == null || flagAttr == null)
		return jsonUtil.setResultFail().setTip("非法注册").toJsonString();
	    String tempPhone = (String) phoneAttr;
	    boolean verifyFlag = (boolean) flagAttr;
	    if (tempPhone != null && verifyFlag) {
		if (userManager.register(tempPhone, password)){
		    userManager.userLogin(tempPhone, password, device, httpSession);
		    httpSession
			.setMaxInactiveInterval(6 * 60 * 60);
		    jsonUtil.setResultSuccess().setJsonObject("account", tempPhone);
		}    
		else
		    jsonUtil.setResultFail().setTip("注册失败");
	    } else
		jsonUtil.setResultFail().setJsonObject("tip", "此账号未验证");
	} else
	    jsonUtil.setResultFail().setJsonObject("tip", "参数错误");
	return jsonUtil.toJsonString();
    }
    
    

    @RequestMapping(value = "/user/setStuInfo", method = RequestMethod.GET)
    @ResponseBody
    public String user_setStuInfo(
	    HttpSession httpSession,
	    @RequestParam(value = "name", required = false, defaultValue = "") String name,
	    @RequestParam(value = "school", required = false, defaultValue = "") String school,
	    @RequestParam(value = "degree", required = false, defaultValue = "-1") int degree,
	    @RequestParam(value = "major", required = false, defaultValue = "") String major) {
	if (StringUtil.hasText(new String[] { name, school, major })
		&& degree > -1) {
	    if (userManager.isLogined(httpSession)) {
		if (studentManager.setStuInfo(getAccount(httpSession), name, school, degree,major))
		    jsonUtil.setResultSuccess();
		else
		    jsonUtil.setResultFail();
		return jsonUtil.toJsonString();
	    } else
		jsonUtil.setResultFail().setTip("用户未登录");
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }

    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public String user_updatePassword(
	    HttpSession httpSession,
	    @RequestParam(value = "oldPassword", required = false, defaultValue = "") String oldPassword,
	    @RequestParam(value = "newPassword", required = false, defaultValue = "") String newPassword) {
	if (StringUtil.hasText(oldPassword,newPassword)) {
	    if (userManager.isLogined(httpSession)) {
		if(!userManager.verifyPassword(getAccount(httpSession),oldPassword))
		    return jsonUtil.setResultFail().setTip("旧密码错误").toJsonString();
		if (userManager.updatePassword(getAccount(httpSession), oldPassword, newPassword))
		    jsonUtil.setResultSuccess();
		else
		    jsonUtil.setResultFail();
		return jsonUtil.toJsonString();
	    } else
		jsonUtil.setResultFail().setTip("用户未登录");
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }
    
    @RequestMapping(value = "/user/getBackPassword", method = RequestMethod.POST)
    @ResponseBody
    public String user_getBackPassword(
	    HttpSession httpSession,
	    @RequestParam(value = "newPassword", required = false, defaultValue = "") String newPassword) {
	if (StringUtil.hasText(newPassword)) {
	    Object accountAttr = httpSession.getAttribute("phoneNumber");
	    Object flagAttr = httpSession.getAttribute("verify");
	    Object typeAttr = httpSession.getAttribute("type");
	    if(accountAttr ==null || flagAttr==null || typeAttr==null)
		return jsonUtil.setResultFail().setTip("此账号未通过验证").toJsonString();
	    String account = (String) accountAttr;
	    boolean verifyFlag = (boolean) flagAttr;
	    int type = (int) typeAttr;
	    
	    if (account != null && verifyFlag && type==2) {
		if(userManager.getBackPassword(account, newPassword))
		    return jsonUtil.setResultSuccess().toJsonString();
		else
		    return jsonUtil.setResultFail().setTip("修改失败或此用户未注册").toJsonString();
	    }
		
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }
    

    @RequestMapping(value = "/user/updateUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public String user_updateUserInfo(
	    HttpSession httpSession,
	    @RequestParam(value = "sex", required = false, defaultValue = "0") int sex,
	    @RequestParam(value = "birthday", required = false, defaultValue = "") Date birthday,
	    @RequestParam(value = "degree", required = false, defaultValue = "0") int degree,
	    @RequestParam(value = "email", required = false, defaultValue = "null") String email) {
	if (StringUtil.hasText(email)
		&& degree > -1 && sex > -1) {
	    if (userManager.isLogined(httpSession)) {
		userManager.updateUserInfo(getAccount(httpSession), sex, birthday, degree,
			email);
		return jsonUtil.setResultSuccess().toJsonString();
	    }
	    return jsonUtil.setResultFail().setTip("用户未登录").toJsonString();
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }
    
    
    @RequestMapping(value = "/user/updateDBversion", method = RequestMethod.GET)
    @ResponseBody
    public String user_updateDBversion(
	    @RequestParam(value = "version", required = false, defaultValue = (DBLoger.DEFAULT_VERSION-1)+"") int version) {
	int serverVersion;
	try {
		serverVersion =  dbLoger.getVersion();
	} catch (IOException e) {
		return jsonUtil.setResultFail().setTip("获取服务器版本失败").toJsonString();
	}
	if (version < DBLoger.DEFAULT_VERSION && version > serverVersion)
	    return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
	List<DBLogEntity> updates = dbLoger.getUpdates(version, serverVersion);
	List<DBLogEntity> deletes = dbLoger.getDeletes(version, serverVersion);
	List<DBLogEntity> saves = dbLoger.getSaves(version, serverVersion);
	return jsonUtil.setResultSuccess().setJsonObject("updates", updates).setJsonObject("deletes", deletes).setJsonObject("saves",saves).toJsonString("/user/updateDBversion",true);
    }
    
    private String getAccount(HttpSession httpSession) {
	Object accountAttr = httpSession.getAttribute(User.ACCOUNT);
	return accountAttr == null ? null : (String) accountAttr;
    }
    
    

}
