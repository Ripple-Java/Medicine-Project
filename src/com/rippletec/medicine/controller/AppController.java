package com.rippletec.medicine.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.launcher.resources.launcher;

import com.rippletec.medicine.SMS.SMS;
import com.rippletec.medicine.bean.DBLogEntity;
import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.bean.TypeBean;
import com.rippletec.medicine.exception.ControllerException;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.ServiceException;
import com.rippletec.medicine.exception.UtilException;
import com.rippletec.medicine.model.BackGroundMedicineType;
import com.rippletec.medicine.model.BaseModel;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.FeedBackMass;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineDocument;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.ProjectConfig;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.UserFavorite;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.DBLoger;
import com.rippletec.medicine.utils.ErrorCode;
import com.rippletec.medicine.utils.JsonUtil;
import com.rippletec.medicine.utils.ParamMap;
import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.app.EnterTypesVO;

/**
 * App端Controller
 * 根路径:/App/
 * @author Liuyi
 *
 */
@Controller
@RequestMapping("/App")
public class AppController extends BaseController {
    
    
    
    public static final String DOMAIN_PATH = "http://112.74.131.194:8080/MedicineProject";
    public static final String APP_DOWN_PATH = "/download/app/";
    public static final String IOS_DOWN_PATH = "/download/ios/";
    public static final String APP_FILE_NAME = "MedicineHub_V$.apk";
    public static final String IOS_FILE_NAME = "MedicineHub_V$.app";
    public static final String OUTPUT_NAME = "MedicineHub_V$";


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
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 添加用户收藏
     * @param request
     * @param userFavorite
     * @return
     * @throws DaoException 
     * @throws ControllerException 
     * @throws ServiceException 
     */
    @RequestMapping(value = "/user/addFavorite", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_addUserFavorite(HttpSession session,UserFavorite userFavorite) throws DaoException, ControllerException, ServiceException{
    	String account= getAccount(session);
    	userFavoriteManager.addUserFavorite(account, userFavorite);
    	return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/user/deleteFavorite", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_deleteFavorite(int id, HttpSession session) throws DaoException, ControllerException{
	UserFavorite userFavorite = userFavoriteManager.find(id);
	if(!userFavorite.getUser().getAccount().equals(getAccount(session)))
	    return jsonUtil.setFailRes(ErrorCode.ILLEGAL_ACCESS_ERROR).toJson();
    	userFavoriteManager.delete(id);
    	return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/user/getFavorite", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getUserFavorite(HttpSession httpSession) throws ControllerException, UtilException, DaoException{
    	    String account = getAccount(httpSession);
    	    return jsonUtil.setModels(userFavoriteManager.findByAccount(account)).toJson("/user/getFavorite");
    }
    
    
    @RequestMapping(value = "/user/addFeedBack", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_addFeedBackMass(HttpSession httpSession,FeedBackMass feedBackMass) throws DaoException, ControllerException{
    	String account = getAccount(httpSession);
    	feedBackMassManager.addFeedBackMass(feedBackMass, account);
    	return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/user/getAllSearch", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getAllSearch(String keyword) throws UtilException {
	if (!StringUtil.hasText(keyword)) {
	    return ParamError();	   
	}
	List<WestMedicine> westMedicines;
	try {
	    westMedicines = westMedicineManager.search(WestMedicine.NAME, keyword);
	} catch (DaoException e) {
	    westMedicines = null;
	}
	List<ChineseMedicine> chineseMedicines;
	try {
	    chineseMedicines = chineseMedicineManager.search(ChineseMedicine.NAME,keyword);
	} catch (DaoException e) {
	    chineseMedicines = null;
	}
	 List<EnterChineseMedicine> enterChineseMedicines;
	try {
	    enterChineseMedicines = enterChineseMedicineManager.search(EnterChineseMedicine.TABLE_NAME,EnterChineseMedicine.NAME, keyword, EnterChineseMedicine.STATUS, EnterChineseMedicine.ON_PUBLISTH);
	} catch (DaoException e) {
	    enterChineseMedicines = null;
	}
	List<EnterWestMedicine> enterWestMedicines;
	try {
	    enterWestMedicines = enterWestMedicineManager.search(EnterWestMedicine.TABLE_NAME, EnterWestMedicine.NAME, keyword, EnterWestMedicine.STATUS, EnterWestMedicine.ON_PUBLISTH);
	} catch (Exception e) {
	    enterWestMedicines = null;
	}
	List<Enterprise> enterprises;
	try {
	    enterprises = enterpriseManager.search(Enterprise.TABLE_NAME, Enterprise.NAME, keyword, Enterprise.STATUS ,Enterprise.ON_PUBLISTH);
	} catch (DaoException e) {
	    enterprises = null;
	}
	List<Meeting> meetings;
	try {
	    meetings = meetingManager.search(Meeting.TABLE_NAME, Meeting.NAME, keyword, Meeting.STATUS, Meeting.ON_PUBLISTH);
	} catch (DaoException e) {
	    meetings = null;
	}
	List<Video> videos;
	try {
	    videos = videoManager.search(Video.TABLE_NAME, Video.NAME,keyword, Video.STATUS, Video.ON_PUBLISTH);
	} catch (DaoException e) {
	    videos = null;
	}
	if(westMedicines == null && chineseMedicines == null && enterChineseMedicines==null && enterWestMedicines == null
		&& enterprises == null && meetings == null && videos == null){
	    return jsonUtil.setFailRes(ErrorCode.DB_NO_ENITY_ERROR).toJson();
	}
	jsonUtil.setModels(chineseMedicines)
		.setModels(westMedicines)
		.setModels(enterWestMedicines)
		.setModels(enterChineseMedicines)
		.setModels(enterprises)
		.setModels(meetings)
		.setModels(videos);
	return jsonUtil.setSuccessRes().toJson("/user/getAllSearch");
    }

    @RequestMapping(value = "/res/getEnterprise", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String res_getEnterprise(int type,int size, int currentPage) throws DaoException, UtilException {
	//type = 0-内资，1-外资，2-合资
	if(type != 0 && type != 1 && type !=2)
	    return ParamError();
	if(currentPage == 0 && size==0){
	    List<Enterprise> allAllowEnterprises = enterpriseManager.getEnterpriseByType(type, null);
	    return jsonUtil.setModels(allAllowEnterprises).setSuccessRes().toJson("/res/getEnterprise");
	}
	List<Enterprise> enterprises = enterpriseManager.getEnterpriseByType(type, new PageBean(currentPage, 0, size));
	return jsonUtil.setSuccessRes().setModels(enterprises).toJson("/res/getEnterprise");
    }
    
    @RequestMapping(value = "/user/getEnterContent", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getEnterContent(int id) throws UtilException, DaoException {
	ParamMap paramMap = new ParamMap().put(Enterprise.ID, id)
					  .put(Enterprise.STATUS, Enterprise.ON_PUBLISTH);
	List<Enterprise> enterprises = enterpriseManager.findByParam(paramMap);
	return jsonUtil.setSuccessRes().setObject("enterContent", enterprises.get(0)).toJson("/user/getEnterContent");
    }

    @RequestMapping(value = "/user/getEnterMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getEnterpriseMedicine(int id,int type) throws UtilException, DaoException {
	if (type != Medicine.ENTER_CHINESE && type != Medicine.ENTER_WEST) {
	    return ParamError();
	}
	Object jsonObject = null;
	if (type == Medicine.ENTER_CHINESE) {
	    ParamMap paramMap = new ParamMap().put(EnterChineseMedicine.ID, id)
		    .put(EnterChineseMedicine.STATUS,EnterChineseMedicine.ON_PUBLISTH);
	    List<EnterChineseMedicine> enterChineseMedicines = enterChineseMedicineManager.findByParam(paramMap);
	    jsonObject = enterChineseMedicines.get(0);
	} else if (type == Medicine.ENTER_WEST) {
	    ParamMap paramMap = new ParamMap().put(EnterWestMedicine.ID, id)
		    			      .put(EnterWestMedicine.STATUS,EnterWestMedicine.ON_PUBLISTH);
	    List<EnterWestMedicine> enterWestMedicines = enterWestMedicineManager.findByParam(paramMap);
	    jsonObject = enterWestMedicines.get(0);
	}
	return jsonUtil.setSuccessRes().setObject("entity", jsonObject).toJson("/user/getEnterMedicine");
    }

//   获取通用药品，未启用
//    @RequestMapping(value = UrlMapper.app_user_getMedicine, method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public String user_getMedicine(int typeId,int size,int currentPage) throws DaoException, UtilException {
//	Map<String, Object> res = medicineTypeManager.getMedicineByTypeId(typeId, new PageBean(currentPage, 0, size));
//	return jsonUtil.setModels((List) res.get("medicines")).setObject("type", res.get("type")).toJson(UrlMapper.app_user_getMedicine);
//    }

//    获取医药文档，未启用
//    @RequestMapping(value = "/user/getMedicineDom", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public String user_getMedicineDom(int medicineId,int type) throws UtilException, DaoException {
//	List<MedicineDocument> medicineDocuments = medicineDocumentManager.getDocument(medicineId, type);
//	return jsonUtil.setModels(medicineDocuments).toJson("/user/getMedicineDom");
//    }

    @RequestMapping(value = "/user/getMedicineType", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getMedicineType(int parentId) throws DaoException, UtilException {
	List<MedicineType> medicineTypes = medicineTypeManager.getTypeByParentId(parentId);
	return jsonUtil.setModels(medicineTypes).toJson("/user/getMedicineType");
    }

    @RequestMapping(value = "/user/getUserInfo", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getUserInfo(HttpSession httpSession) throws UtilException, DaoException, ControllerException {
	String account = getAccount(httpSession);
	User user = userManager.findByAccount(account);
	return jsonUtil.setObject("User",user).setSuccessRes().toJson("/user/getUserInfo");
    }

    @RequestMapping(value = "/getVerificationCode", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getVerificationCode(HttpSession httpSession,String phoneNumber, int type) {
	if (!StringUtil.isMobile(phoneNumber)) {
	    return jsonUtil.setFailRes(ErrorCode.PARAM_FORMAT_ERROR).toJson();
	}
	if(!allow_number.contains(phoneNumber)){
	    return jsonUtil.setFailRes(ErrorCode.USER_REGISTER_DENIED_ERROR).toJson();	    
	}
	if (type != 1 && type != 2) {
	    return jsonUtil.setFailRes(ErrorCode.PARAM_ERROR).toJson();
	}
	if (type == 1 && userManager.isExist(phoneNumber)){
	    return jsonUtil.setFailRes(ErrorCode.USER_EXISTED_ERROR).toJson();	    
	}
	if (type == 2 && !userManager.isExist(phoneNumber)){
	    return jsonUtil.setFailRes(ErrorCode.USER_NOT_EXISTED_ERROR).toJson();	    
	}
	String VerificationCode = StringUtil.generateCode(6);
	String timeLimit = "1";
	if (type == 2)
	    timeLimit = "2";
	SMS sms = new SMS();
	String res = "";
	switch (type) {
	case 1:
	    res = sms.sendSMS(phoneNumber, VerificationCode, timeLimit,
		    SMS.RegisterTemplateId);
	    break;
	case 2:
	    res = sms.sendSMS(phoneNumber, VerificationCode, timeLimit,
		    SMS.GetBackTemplateId);
	    break;
	}
	if (res.equals("success")) {
	    httpSession.setAttribute("phoneNumber", phoneNumber);
	    httpSession.setAttribute("code", VerificationCode);
	    httpSession.setAttribute("type", type);
	    httpSession.setAttribute("verify", false);
	    httpSession.setMaxInactiveInterval((new Integer(timeLimit)) * 60);
	    return jsonUtil.setSuccessRes().setObject("time", timeLimit).setObject("sessionid", httpSession.getId()).toJson();
	 }else {
	    Logger.getLogger(AppController.class).error(res);
	    return jsonUtil.setFailRes(ErrorCode.VERIFYCODE_SEND_ERROR).toJson();
	}
    }
    
//    添加医师信息，未启用  
//    @RequestMapping(value = UrlMapper.app_user_setDocInfo, method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public String user_setDocInfo(
//	    HttpSession httpSession, String name,String hospital, String office,String officePhone, String profession) {
//	if (StringUtil.hasText(new String[] { name, hospital, office,officePhone, profession })) {
//	    if (userManager.isLogined(httpSession)) {
//		if (doctorManager.setInfo((String) httpSession.getAttribute(User.ACCOUNT), name, hospital, office,officePhone, profession))
//		    jsonUtil.setSuccessRes();
//		else
//		    jsonUtil.setFailRes();
//		return jsonUtil.toJson();
//	    } else
//		return jsonUtil.setFailRes().setTip("用户未登录").toJson();
//	}
//	return jsonUtil.setFailRes().setTip("参数错误").toJson();
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_setLogin(HttpSession httpSession,String account,String password, int device, String deviceId) throws DaoException, ServiceException {
	if (!StringUtil.isMobile(account) || !StringUtil.hasText(password, deviceId)) {
	   return jsonUtil.setFailRes(ErrorCode.PARAM_FORMAT_ERROR).toJson();
	}
	if(device != 0 && device != 1 && device!=2){
	    return jsonUtil.setFailRes(ErrorCode.PARAM_ERROR).toJson();
	}
	
	userManager.appUserLogin(account, password, device, deviceId,httpSession);
	return jsonUtil.setSuccessRes().setObject("sessionid", httpSession.getId()).toJson();
    }
    
    @RequestMapping(value = "/loginOut", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_setLoginOut(HttpSession httpSession) throws ControllerException, DaoException {
	String account = getAccount(httpSession);
	userManager.loginOut(account,httpSession);
	return jsonUtil.setSuccessRes().toJson();
    }

    @RequestMapping(value = "/verifyCode", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_verifyCode(HttpSession httpSession,String code) {
	if(!StringUtil.hasText(code)){
	    return jsonUtil.setFailRes(ErrorCode.PARAM_ERROR).toJson();
	}
	Object typeAttr = httpSession.getAttribute("type"); 
	Object tempCodeAttr = httpSession.getAttribute("code");
	if (typeAttr == null || tempCodeAttr==null){
	    return jsonUtil.setFailRes(ErrorCode.ILLEGAL_ACCESS_ERROR).toJson();
	}
	int type = (int) typeAttr;
	String tempCode = (String) tempCodeAttr;
	if (!code.equals(tempCode)) {
	    return jsonUtil.setFailRes(ErrorCode.VERIFYCODE_DENIED_ERROR).toJson();
	}
	httpSession.setAttribute("verify", true);
	httpSession.setMaxInactiveInterval(10 * 60);
	return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_setRegister(
	    HttpSession httpSession,String password, int device) throws DaoException, ServiceException {
	if (!StringUtil.hasText(password)) {
	    return ParamError();
	} 
	if(device != User.DRVICE_ANDROID && device != User.DRVICE_IPHONE && device!=User.DRVICE_OTHER){
	    return ParamError();
	}
	Object phoneAttr = httpSession.getAttribute("phoneNumber");
	Object flagAttr = httpSession.getAttribute("verify");
	Object typeArr = httpSession.getAttribute("type");
	if (phoneAttr == null || flagAttr == null || typeArr == null){
	    return jsonUtil.setFailRes(ErrorCode.ILLEGAL_ACCESS_ERROR).toJson();
	}
	String tempPhone = (String) phoneAttr;
	boolean verifyFlag = (boolean) flagAttr;
	int type = (int) typeArr;
	if (type == 1 && verifyFlag) {
	    userManager.register(tempPhone, password);
	    userManager.userLogin(tempPhone, password, device, httpSession);
	    httpSession.setMaxInactiveInterval(6 * 60 * 60);
	    return jsonUtil.setSuccessRes().setObject("account", tempPhone).toJson();
	} else{
	    return jsonUtil.setFailRes(ErrorCode.NOT_VALICATE_ACCESS_ERROR).toJson();
	}
    }
    
    
//	添加学生信息，未启用
//    @RequestMapping(value = UrlMapper.app_user_setStuInfo, method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public String user_setStuInfo(HttpSession httpSession, String name,String school,int degree,String major) {
//	if (StringUtil.hasText(new String[] { name, school, major })
//		&& degree > -1) {
//	    if (userManager.isLogined(httpSession)) {
//		if (studentManager.setStuInfo(getAccount(httpSession), name, school, degree,major))
//		    jsonUtil.setSuccessRes();
//		else
//		    jsonUtil.setFailRes();
//		return jsonUtil.toJson();
//	    } else
//		jsonUtil.setFailRes().setTip("用户未登录");
//	}
//	return jsonUtil.setFailRes().setTip("参数错误").toJson();
//    }

    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_updatePassword( HttpSession httpSession,String oldPassword, String newPassword) throws DaoException, ServiceException, ControllerException {
	if (!StringUtil.hasText(oldPassword,newPassword)) {
	    return ParamError();
	}
	if(!userManager.verifyPassword(getAccount(httpSession),oldPassword)){
	    return jsonUtil.setFailRes(ErrorCode.USER_OLD_PASSWORD_ERROR).toJson();
	}
	userManager.updatePassword(getAccount(httpSession), oldPassword, newPassword);
	return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/getBackPassword", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getBackPassword(HttpSession httpSession,String newPassword) throws DaoException, ServiceException {
	if (!StringUtil.hasText(newPassword)) {
	    return ParamError();
	}
	 Object accountAttr = httpSession.getAttribute("phoneNumber");
	 Object flagAttr = httpSession.getAttribute("verify");
	 Object typeAttr = httpSession.getAttribute("type");
	 if(accountAttr ==null || flagAttr==null || typeAttr==null){
	     return jsonUtil.setFailRes(ErrorCode.ILLEGAL_ACCESS_ERROR).toJson();
	 }
	String account = (String) accountAttr;
	boolean verifyFlag = (boolean) flagAttr;
	int type = (int) typeAttr;
	if (!verifyFlag && type != 2) {
	   return jsonUtil.setFailRes(ErrorCode.NOT_VALICATE_ACCESS_ERROR).toJson();
	}
	userManager.getBackPassword(account, newPassword);
	httpSession.removeAttribute("phoneNumber");
	httpSession.removeAttribute("verify");
	httpSession.removeAttribute("type");
	httpSession.removeAttribute("code");
	return jsonUtil.setSuccessRes().toJson();
	
    }
    

    @RequestMapping(value = "/user/updateUserInfo", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_updateUserInfo(HttpSession httpSession,String name, int sex, Date birthday, int degree, String email) throws DaoException, ControllerException {
	if (!StringUtil.hasText(name) || !StringUtil.isEmail(email)) {
	    return jsonUtil.setFailRes(ErrorCode.PARAM_FORMAT_ERROR).toJson();
	}
	if(degree < 0 || degree>7 || sex < 0 || sex > 2){
	    return ParamError();
	}
	userManager.updateUserInfo(getAccount(httpSession),name,sex, birthday, degree,email);
	return jsonUtil.setSuccessRes().toJson();
    }
    
    
    @RequestMapping(value = "/user/updateDBversion", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_updateDBversion(int version) throws UtilException, ServiceException {
	int serverVersion =  dbLoger.getVersion();
	if (version < DBLoger.DEFAULT_VERSION || version > serverVersion){
	    return ParamError();
	}
	List<DBLogEntity> updates;
	try {
	    updates = dbLoger.getUpdates(version, serverVersion);
	} catch (DaoException e) {
	    updates = null;
	}
	List<DBLogEntity> deletes;
	try {
	    deletes = dbLoger.getDeletes(version, serverVersion);
	}catch (DaoException e) {
	    deletes = null;
	}
	List<DBLogEntity> saves;
	try {
	    saves = dbLoger.getSaves(version, serverVersion);
	} catch (DaoException e) {
	    saves = null;
	}
	if(updates == null && deletes == null && saves == null){
	    return jsonUtil.setFailRes(ErrorCode.DB_NO_ENITY_ERROR).toJson();
	}
	return jsonUtil.setSuccessRes().setObject("updates", updates).setObject("deletes", deletes).setObject("saves",saves).toJson("/user/updateDBversion",true);
    }
    
    @RequestMapping(value = "/checkCode", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_checkCode(String code) {
	if(!StringUtil.hasText(code)){
	    return ParamError();
	}
	if(androidCode.equals(code)){
	    return jsonUtil.setSuccessRes().toJson();	    
	}
	return jsonUtil.setFailRes(ErrorCode.CHECKCODE_ERROR).toJson();
    }
    
    @RequestMapping(value = "/res/getRecentMeeting", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String res_getRecentMeeting(int size) throws UtilException, DaoException {
	if(size < 0){
	    return ParamError();
	}
	List<Meeting> meetings = null;
	if(size == 0){
	    meetings = meetingManager.findRecentMeeting(null,Meeting.STATUS,Meeting.ON_PUBLISTH);
	}
	else {
	    meetings = meetingManager.findRecentMeeting(new PageBean(1, 0, size),Meeting.STATUS,Meeting.ON_PUBLISTH);
	}
	return jsonUtil.setSuccessRes().setModels(meetings).toJson("/res/getRecentMeeting");
    }
    
    @RequestMapping(value = "/user/getMeetings", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getMeetings(int id) throws DaoException, UtilException {
	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put(Meeting.ENTERPRISE_ID, id);
	paramMap.put(Meeting.STATUS, Meeting.ON_PUBLISTH);
	List<Meeting> meetings = meetingManager.findBySql(Meeting.TABLE_NAME, paramMap);
	return jsonUtil.setSuccessRes().setModels(meetings).toJson("/user/getMeetings");
    }
    
    
    @RequestMapping(value = "/user/getMeetingById", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getMeetingById(int id) throws UtilException, DaoException {
	ParamMap paramMap = new ParamMap().put(Meeting.ID, id)
					  .put(Meeting.STATUS, Meeting.ON_PUBLISTH);
	List<Meeting> meetings = meetingManager.findByParam(paramMap);
	return jsonUtil.setObject("meeting", meetings.get(0)).setSuccessRes().toJson("/user/getMeetingById");
    }
    
    
    
    @RequestMapping(value = "/res/getRecentVideo", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getRecentVideo(int size) throws UtilException, DaoException {
	if(size < 0){
	    return ParamError();
	}
	List<Video> videos = null;
	if(size == 0){
	    videos = videoManager.findRecentMeeting(null,Video.STATUS,Video.ON_PUBLISTH);	 
	}else {
	    videos = videoManager.findRecentMeeting(new PageBean(0, size),Video.STATUS,Video.ON_PUBLISTH);	    
	}
	return jsonUtil.setSuccessRes().setModels(videos).toJson("/res/getRecentVideo");
    }
    
    @RequestMapping(value = "/user/getVideoes", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getVideoes(int id) throws DaoException, UtilException {
	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put(Video.ENTERPRISE_ID, id);
	paramMap.put(Video.STATUS, Meeting.ON_PUBLISTH);
	List<Video> videos = videoManager.findBySql(Video.TABLE_NAME, paramMap);
	return jsonUtil.setSuccessRes().setModels(videos).toJson("/user/getVideoes"); 
    }
    
    @RequestMapping(value = "/user/getVideoById", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getVideoById(int id) throws DaoException, UtilException {
	 ParamMap paramMap = new ParamMap().put(Video.ID, id)
		 			   .put(Video.STATUS, Video.ON_PUBLISTH);
	 List<Video> videos = videoManager.findByParam(paramMap);
	 return jsonUtil.setSuccessRes().setObject("video", videos.get(0)).toJson("/user/getVideoById");
    }
    
    @RequestMapping(value = "/user/getEnterTypes", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getEnterTypes( int enterpriseId, int type) throws DaoException  {
	if(type != EnterpriseMedicineType.CHINESE && type != EnterpriseMedicineType.WEST){
	    return ParamError();
	}
	List<EnterpriseMedicineType> enterpriseMedicineTypes = enterpriseMedicineTypeManager.getTypes(enterpriseId, type);
	HashSet<TypeBean> parentTypes = new HashSet<TypeBean>();
	HashSet<TypeBean> childs = new HashSet<TypeBean>();
	for (EnterpriseMedicineType enterpriseMedicineType : enterpriseMedicineTypes) {
	    BackGroundMedicineType backType = enterpriseMedicineType.getBackGroundMedicineType();
	    parentTypes.add(new TypeBean(backType.getSecondType_id(), backType.getSecondType(), type));
	    childs.add(new TypeBean(backType.getThirdType_id(), backType.getThirdType(), backType.getSecondType_id()));
	}
	return jsonUtil.setSuccessRes().setObject("parentTypes", parentTypes).setObject("childTypes", childs).toJson();
	
    }
    
    @RequestMapping(value = "/user/getEnterMedicines", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getEnterMedicineList( int enterpriseId, int medicineTypeId) throws UtilException, DaoException {
	MedicineType medicineType = medicineTypeManager.find(medicineTypeId);
	if(!medicineType.getFlag()){
	    	List<MedicineType> allTypes = medicineTypeManager.getAllChild(medicineType);
	    	List<BaseModel> models = new ArrayList<BaseModel>();
            	Map<String, Object> paramMap = new HashMap<String, Object>();
	    for (MedicineType allType : allTypes) {
		paramMap.put(EnterChineseMedicine.MEDICINE_TYPE_ID,allType.getId());
		paramMap.put(EnterChineseMedicine.ENTERPRISE_ID, enterpriseId);
		paramMap.put(EnterChineseMedicine.STATUS, EnterChineseMedicine.ON_PUBLISTH);
		
		if (medicineType.getGib_type() == EnterpriseMedicineType.WEST) {
		    try {
			models.addAll(enterWestMedicineManager.findBySql(EnterWestMedicine.TABLE_NAME, paramMap));
		    } catch (DaoException e) {}
		} else if (medicineType.getGib_type() == EnterpriseMedicineType.CHINESE) {
		    try {
			models.addAll(enterChineseMedicineManager.findBySql(EnterChineseMedicine.TABLE_NAME,paramMap));
		    } catch (DaoException e) {}
		}
	    }
	    if(StringUtil.isEmpty(models)){
		return jsonUtil.setFailRes(ErrorCode.DB_NO_ENITY_ERROR).toJson();
	    }
            return jsonUtil.setSuccessRes().setModels(models).toJson("/user/getEnterMedicines");
	}
	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put(EnterChineseMedicine.MEDICINE_TYPE_ID,medicineTypeId);
	paramMap.put(EnterChineseMedicine.ENTERPRISE_ID, enterpriseId);
	paramMap.put(EnterChineseMedicine.STATUS, EnterChineseMedicine.ON_PUBLISTH);
	List models;
	if (medicineType.getGib_type() == EnterpriseMedicineType.WEST) {
	    try {
		models = enterWestMedicineManager.findBySql(EnterWestMedicine.TABLE_NAME, paramMap);
	    } catch (DaoException e) {
		models = null;
	    }
	} 
	else{
	    try {
		models = enterChineseMedicineManager.findBySql(EnterChineseMedicine.TABLE_NAME,paramMap);
	    } catch (DaoException e) {
		models = null;
	    }
	}
	if(StringUtil.isEmpty(models)){
	    return jsonUtil.setFailRes(ErrorCode.DB_NO_ENITY_ERROR).toJson();
	}
	return jsonUtil.setModels(models).setSuccessRes().toJson("/user/getEnterMedicines");
    }
    
    @ResponseBody
    @RequestMapping(value = "/checkUpdate", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public String user_checkUpdate(int type, int version) throws DaoException {
	if(type != ProjectConfig.TYPE_ANDROID && type != ProjectConfig.TYPE_IOS){
	    return ParamError();
	}
	String con_key = "";
	String detial_key = "";
	String outVersion_key = "";
	String url = DOMAIN_PATH;
	String name = "";
	if(type == ProjectConfig.TYPE_ANDROID){
	    con_key = ProjectConfig.APP_VERSION;
	    detial_key = ProjectConfig.APP_UPDATE_MESSAGE;
	    outVersion_key = ProjectConfig.APP_SHOW_VERSION;
	    url += APP_DOWN_PATH;
	    name = APP_FILE_NAME;
	}
	else if (type == ProjectConfig.TYPE_IOS) {
	    con_key = ProjectConfig.IOS_SERSION;
	    detial_key = ProjectConfig.IOS_UPDATE_MESSAGE;
	    outVersion_key = ProjectConfig.IOS_SHOW_SERSION;
	    url += IOS_DOWN_PATH+IOS_FILE_NAME;
	    name = IOS_FILE_NAME;
	}
	else {
	    return ParamError();
	}
	ProjectConfig versonConfig = projectConfigManager.findByKey(con_key);
	int updateVersion = new Integer(versonConfig.getCon_value());
	if(version >= updateVersion){
	    return jsonUtil.setSuccessRes().setObject("isUpdate", 0).toJson();
	}
	ProjectConfig detialConfig = projectConfigManager.findByKey(detial_key);
	ProjectConfig outVersionConfig = projectConfigManager.findByKey(outVersion_key);
	
	url += name.replace("$", outVersionConfig.getCon_value()+"");
	
	return jsonUtil.setSuccessRes().setObject("isUpdate", 1)
			.setObject("updateMessage", detialConfig.getCon_value())
			.setObject("updateVersion", updateVersion)
			.setObject("appVersion", outVersionConfig.getCon_value())
			.setObject("url", url)
			.toJson();
	
    }
    


}
