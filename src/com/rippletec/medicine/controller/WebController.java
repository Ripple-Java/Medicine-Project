package com.rippletec.medicine.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import sun.launcher.resources.launcher;
import sun.security.krb5.internal.EncAPRepPart;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.model.BaseModel;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineDocument;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.utils.DateUtil;
import com.rippletec.medicine.utils.FileUtil;
import com.rippletec.medicine.utils.MD5Util;
import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.medicine.vo.web.CheckDataVO;
import com.rippletec.medicine.vo.web.EnterChineseVO;
import com.rippletec.medicine.vo.web.EnterWestVO;
import com.rippletec.medicine.vo.web.MeetingVo;


@Controller
@RequestMapping("/Web")
public class WebController extends BaseController {
    

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
    
    @RequestMapping(value = "/adminuser/login", method = RequestMethod.POST)
    @ResponseBody
    public String user_setLogin(
	    HttpSession httpSession,
	    @RequestParam(value = "account", required = false, defaultValue = "") String account,
	    @RequestParam(value = "password", required = false, defaultValue = "") String password) {
	if (StringUtil.isUsername(account) && StringUtil.hasText(password)) {
	    if (userManager.isLogined(httpSession))
		return jsonUtil.setResultFail().setTip("账号已登录，请勿重复登陆").toJsonString();
	    if (userManager.adminLogin(account, password, httpSession))
		jsonUtil.setResultSuccess();
	    else
		jsonUtil.setResultFail().setTip("用户名或密码错误");
	} else
	    jsonUtil.setResultFail().setTip("参数错误");
	return jsonUtil.toJsonString();
    }
    
    @RequestMapping(value = "/admin/loginOut", method = RequestMethod.GET)
    @ResponseBody
    public String admin_setLoginOut(
	    HttpSession httpSession) {
	if(userManager.loginOut((String) httpSession.getAttribute(User.ACCOUNT),httpSession))
	    return jsonUtil.setResultSuccess().toJsonString();
	else 
	    return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value="/admin/getMedicine", method=RequestMethod.GET)
    @ResponseBody
    public String admin_getMedicine(int pageSize, int pageNum, int type){
	if(pageSize <= 0 || pageNum <= 0)
	    return jsonUtil.setResultFail().setTip("页面参数错误").toJsonString();
	PageBean page = new PageBean(pageNum, 0, pageSize);
	if(type == 5){
	    List<Medicine> medicines = medicineManager.findByPage(page);
	    List<BackGroundMedicineVO> models = new LinkedList<BackGroundMedicineVO>(); 
	    medicineManager.getChineseOrWest(models , medicines);
	    return jsonUtil.setResultSuccess().setModelList(models).toJsonString("/admin/getMedicine");
	}
	if(medicineManager.getMedicine(page, type, jsonUtil))
	    return jsonUtil.setResultSuccess().toJsonString("/admin/getMedicine");
	else
	    return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value="/admin/getCategoryMedicine", method=RequestMethod.GET)
    @ResponseBody
    public String admin_getCategoryMedicine(int pageSize, int pageNum,int type,int category){
	if(pageSize <= 0 || pageNum <= 0)
	    return jsonUtil.setResultFail().setTip("页面参数错误").toJsonString();
	PageBean page = new PageBean(pageNum, 0, pageSize);
	if(medicineManager.getMedicineByCategory(page,type,category, jsonUtil))
	    return jsonUtil.setResultSuccess().toJsonString("/admin/getCategoryMedicine");
	else
	    return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value="/admin/deleteMedicine", method=RequestMethod.POST)
    @ResponseBody
    public String admin_deleteMedicine(int id, int type){
	if(medicineManager.deleteMedicine(id, type))
	    jsonUtil.setResultSuccess();
	else
	    jsonUtil.setResultFail();
	return jsonUtil.toJsonString();
    }
     
    
    @RequestMapping(value="/admin/addChinMedicine", method=RequestMethod.POST)
    @ResponseBody
    public String admin_addChinMedicine(ChineseMedicine chineseMedicine, int medicineType_id){
	chineseMedicine.setSortKey(StringUtil.toPinYin(chineseMedicine.getName()));
	if((int)chineseMedicineManager.save(chineseMedicine) > 0)
	    return jsonUtil.setResultSuccess().toJsonString();
	return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value="/admin/addWestMedicine", method=RequestMethod.POST)
    @ResponseBody
    public String admin_addWestMedicine(WestMedicine westMedicine, int medicineType_id){
	westMedicine.setSortKey(westMedicine.getName());
	if((int)westMedicineManager.save(westMedicine) > 0)
	    return jsonUtil.setResultSuccess().toJsonString();
	return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value="/admin/updateWestMedicine", method=RequestMethod.POST)
    @ResponseBody
    public String admin_updateWestMedicine(WestMedicine westMedicine){
	westMedicineManager.update(westMedicine);
	return jsonUtil.setResultSuccess().toJsonString();
    }
    
    
    
    @RequestMapping(value="/admin/updateChinMedicine", method=RequestMethod.POST)
    @ResponseBody
    public String admin_updateWestMedicine(ChineseMedicine chineseMedicine){
	chineseMedicineManager.update(chineseMedicine);
	return jsonUtil.setResultSuccess().toJsonString(); 
    }
    
    @RequestMapping(value="/enteruser/setInfo", method=RequestMethod.POST)
    @ResponseBody
    public String enterprise_setInfo(HttpSession httpSession ,String name, int type, String number, String imgUrl){
	if(StringUtil.isEnterpriseName(name) && StringUtil.isEnterpriseType(type) && StringUtil.isNumber(number)){
	    CheckData checkData = new CheckData(null,name ,0,CheckData.TYPE_ENTERPRISE, imgUrl, new Date(), CheckData.CHECKING);
	    Enterprise enterprise = new Enterprise(type, name, number, null);
	    httpSession.setAttribute(Enterprise.CLASS_NAME, enterprise);
	    httpSession.setAttribute(CheckData.CLASS_NAME, checkData);
	    httpSession.setAttribute("infoFlag", true);
	    return jsonUtil.setResultSuccess().toJsonString();
	}
	return jsonUtil.setResultFail().setTip("参数不正确").toJsonString();
    }
    
    @RequestMapping(value="/enteruser/register", method=RequestMethod.POST)
    @ResponseBody
    public String enterprise_setRegister(HttpSession httpSession ,String email, String password){
	
	Object infoAttr = httpSession.getAttribute("infoFlag");
	if(infoAttr == null)
	   return jsonUtil.setResultFail().setTip("非法操作").toJsonString();
	if(StringUtil.isEmail(email) && StringUtil.hasText(password)){
	    if(userManager.isExist(email)){
		User user = userManager.findByAccount(email);
		if(user.getStatus() != User.STATUS_VAlIDATING)
		    return jsonUtil.setResultFail().setTip("该用户已存在").toJsonString();
		enterpriseManager.deleteByUser(user.getId());
	    }
	    if(userManager.registerEnterprise(email ,password, httpSession))
		return jsonUtil.setResultSuccess().toJsonString();
	    return jsonUtil.setResultFail().toJsonString();
	}
	return jsonUtil.setResultFail().setTip("参数不正确").toJsonString();
    }
    
    @RequestMapping(value="/enteruser/validate", method=RequestMethod.GET)
    @ResponseBody
    public String enterprise_setValidate(String action, String code, String account){
	if(!StringUtil.hasText(action,code,account))
	    return jsonUtil.setResultFail("参数不正确").toJsonString();
	if(userManager.isExist(account)){
	    User user = userManager.findByAccount(account);
	    if(user.getStatus() != User.STATUS_VAlIDATING)
		return jsonUtil.setResultFail("此用户已验证或被冻结").toJsonString();
	    try {
		if(MD5Util.validPasswd(user.getAccount()+user.getPassword(), code)){
		    userManager.validUser(account);
		    return jsonUtil.setResultSuccess().toJsonString();
		}   
	    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
		e.printStackTrace();
		return jsonUtil.setResultFail().toJsonString();
	    }
	    return jsonUtil.setResultFail("验证码不正确").toJsonString();
	}
	return jsonUtil.setResultFail("该用户未注册").toJsonString();
    }
    
    @RequestMapping(value = "/enteruser/login", method = RequestMethod.POST)
    @ResponseBody
    public String enterprise_setLogin(
	    HttpSession httpSession,
	    @RequestParam(value = "account", required = false, defaultValue = "") String account,
	    @RequestParam(value = "password", required = false, defaultValue = "") String password) {
	if (StringUtil.isEmail(account) && StringUtil.hasText(password)) {
	    if (userManager.isLogined(httpSession))
		return jsonUtil.setResultFail("账号已登录，请勿重复登陆").toJsonString();
	    if (userManager.adminLogin(account, password, httpSession))
		jsonUtil.setResultSuccess();
	    else
		jsonUtil.setResultFail("用户名或密码错误");
	} else
	    jsonUtil.setResultFail("参数错误");
	return jsonUtil.toJsonString();
    }
    
    @RequestMapping(value = "/enteruser/getBackPassword", method = RequestMethod.POST)
    @ResponseBody
    public String enterprise_getBackPassword(
	    @RequestParam(value = "account", required = false, defaultValue = "") String account) {
	if (StringUtil.isEmail(account)) {
	    if(!userManager.isExist(account))
		return jsonUtil.setResultFail("该用户不存在").toJsonString();
	    if(userManager.initPassword(account))
		return jsonUtil.setResultSuccess().toJsonString();
	} 
	return jsonUtil.setResultFail("参数错误").toJsonString();
    }
    
    @RequestMapping(value = "/enterprise/loginOut", method = RequestMethod.GET)
    @ResponseBody
    public String enterprise_setLoginOut(
	    HttpSession httpSession) {
	if(userManager.loginOut((String) httpSession.getAttribute(User.ACCOUNT),httpSession))
	    return jsonUtil.setResultSuccess().toJsonString();
	else return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/enterprise/addMeeting", method = RequestMethod.POST)
    @ResponseBody
    public String enterprise_addMeeting(HttpSession httpSession,@Validated @ModelAttribute MeetingVo meeting, BindingResult result) {
	if (result.hasErrors())
	    return toErrorJson(result);
	User user = userManager.findByAccount(getAccount(httpSession));
	Enterprise enterprise = enterpriseManager.findByUser(user);
	if(meetingManager.addMeeting(enterprise,meeting))
	    return jsonUtil.setResultSuccess().toJsonString();
	return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/enterprise/addChinMedicine", method = RequestMethod.POST)
    @ResponseBody
    public String enterprise_addChinMedicine(HttpSession httpSession,@Validated @ModelAttribute EnterChineseVO entChineseVO, BindingResult result) {
	if (result.hasErrors())
	    return toErrorJson(result);
	User user = userManager.findByAccount(getAccount(httpSession));
	Enterprise enterprise = enterpriseManager.findByUser(user);
	ChineseMedicine chineseMedicine = chineseMedicineManager.find(entChineseVO.getMedicineId());
	CheckData checkData  = enterChineseMedicineManager.addMedicine(enterprise,chineseMedicine,entChineseVO);
	if(checkData == null)
	    return jsonUtil.setResultFail("添加失败").toJsonString();
	checkDataManager.save(checkData);
	return jsonUtil.setResultSuccess().toJsonString();
    }
    
    @RequestMapping(value = "/enterprise/addWestMedicine", method = RequestMethod.POST)
    @ResponseBody
    public String enterprise_addWestMedicine(HttpSession httpSession,@Validated @ModelAttribute EnterWestVO enterWestVO, BindingResult result) {
	if (result.hasErrors())
	    return toErrorJson(result);
	User user = userManager.findByAccount(getAccount(httpSession));
	Enterprise enterprise = enterpriseManager.findByUser(user);
	WestMedicine westMedicine = westMedicineManager.find(enterWestVO.getMedicineId());
	CheckData checkData = enterWestMedicineManager.addMedicine(enterprise,westMedicine,enterWestVO);
	if(checkData == null)
	    return jsonUtil.setResultFail("保存失败").toJsonString();
	checkDataManager.save(checkData);
	return jsonUtil.setResultSuccess().toJsonString();
	
    }
    
    @RequestMapping(value = "/user/getCount", method = RequestMethod.GET)
    @ResponseBody
    public String user_getCount(
	    @RequestParam(value = "type", required = false, defaultValue = "0") int type) {
	if(type <= 0)
	    return jsonUtil.setResultFail("type参数必须大于0").toJsonString();
	int count = 0;
	
	switch (type) {
	case 11:
	    count = userManager.getCount(User.TABLE_NAME, User.TYPE, User.TYPE_USER);break;    
	case 12:
	    count = userManager.getCount(User.TABLE_NAME, new String[]{User.TYPE, User.STATUS}, new Object[]{User.TYPE_ENTER, User.STATUS_NORMAL});break;
	case 21:
	    count = chineseMedicineManager.getCount(ChineseMedicine.TABLE_NAME);break; 
	case 22:
	    count = westMedicineManager.getCount(WestMedicine.TABLE_NAME);break;
	case 23:
	    count = enterChineseMedicineManager.getCount(EnterChineseMedicine.TABLE_NAME, EnterChineseMedicine.STATUS, EnterChineseMedicine.ON_PUBLISTH);break;
	case 24:
	    count = enterWestMedicineManager.getCount(EnterWestMedicine.TABLE_NAME, EnterWestMedicine.STATUS, EnterWestMedicine.ON_PUBLISTH);break;
	case 31:
	    count = meetingManager.getCount(Meeting.TABLE_NAME, Meeting.STATUS, Meeting.ON_PUBLISTH);break;
	case 41:
	    count = videoManager.getCount(Video.TABLE_NAME, Video.STATUS, Video.ON_PUBLISTH);break;  
	default:
	    break;
	}
	return jsonUtil.setResultSuccess().setJsonObject("count", count).toJsonString();
    }
    
    @RequestMapping(value = "/admin/getCount", method = RequestMethod.GET)
    @ResponseBody
    public String admin_getCount(
	    @RequestParam(value = "type", required = false, defaultValue = "0") int type) {
	if(type <= 0)
	    return jsonUtil.setResultFail("type参数必须大于0").toJsonString();
	int count = 0;
	
	switch (type) {
	case 1:
	    count = userManager.getCount(User.TABLE_NAME, User.STATUS, User.STATUS_CHECKING);break;
	case 2:
	    count = checkDataManager.getCount(CheckData.TABLE_NAME);break;
	case 3:
	    count = meetingManager.getCount(Meeting.TABLE_NAME);break;
	case 4:
	    count = enterpriseManager.getCount(Enterprise.TABLE_NAME);break;
	
	}
	return jsonUtil.setResultSuccess().setJsonObject("count", count).toJsonString();
    }
    
    @RequestMapping(value = "/user/getPPTImageCount", method = RequestMethod.GET)
    @ResponseBody
    public String user_getPPTImageCount(
	    @RequestParam(value = "meetingID", required = false, defaultValue = "0") int meetingID) {
	if(meetingID<=0)
	    return jsonUtil.setResultFail("mettingID不正确").toJsonString();
	Meeting meeting = meetingManager.find(meetingID);
	if(meeting == null)
	    return jsonUtil.setResultFail("此会议不存在").toJsonString();
	String path = FileUtil.getRootPath()+meeting.getPPT();
	File pptFiles = new File(path);
	if(!pptFiles.exists() || !pptFiles.isDirectory())
	    return jsonUtil.setResultFail("ppt文件不存在").toJsonString();
	return jsonUtil.setJsonObject("imageCount", pptFiles.listFiles().length).setResultSuccess().toJsonString();
    }
    
    @RequestMapping(value = "/user/getPPT", method = RequestMethod.GET)
    @ResponseBody
    public String user_getPPT(
	    @RequestParam(value = "meetingID", required = false, defaultValue = "0") int meetingID) {
	if(meetingID<=0)
	    return jsonUtil.setResultFail("mettingID不正确").toJsonString();
	Meeting meeting = meetingManager.find(meetingID);
	if(meeting == null)
	    return jsonUtil.setResultFail("此会议不存在").toJsonString();
	String path = FileUtil.getRootPath()+meeting.getPPT();
	File pptFiles = new File(path);
	if(!pptFiles.exists() || !pptFiles.isDirectory())
	    return jsonUtil.setResultFail("ppt文件不存在").toJsonString();
	List<String> fileNames = new ArrayList<String>();
	File[] files = pptFiles.listFiles();
	Arrays.sort(files, new Comparator<File>() {
	    @Override
	    public int compare(File file1, File file2) {
		int file1Index = new Integer(FileUtil.getSimpleFilename(file1.getName()));
		int file2Index = new Integer(FileUtil.getSimpleFilename(file2.getName()));
		return file1Index - file2Index;
	    }
	});
	for (int i = 0; i < files.length; i++) {
	    String url = meeting.getPPT()+"/"+files[i].getName();
	    fileNames.add(url);
	}
	return jsonUtil.setResultSuccess().setJsonObject("images", fileNames).setJsonObject("count", files.length).toJsonString();
    }
    
    @RequestMapping(value = "/user/getAllMedicineType", method = RequestMethod.GET)
    @ResponseBody
    public String user_getAllMedicineType() {
	String typeJson = medicineTypeManager.getTypeJson();
	if(typeJson != null && typeJson.length() >0)
	    return typeJson;
	return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/admin/getCheckData", method = RequestMethod.GET)
    @ResponseBody
    public String admin_getCheckData(int pageSize, int pageNum) {
	if(pageSize <0 || pageNum <0)
	    return jsonUtil.setResultFail("参数不能小于0").toJsonString();
	PageBean pBean = new PageBean(pageNum, 0, pageSize);
	List<Object> values = new LinkedList<Object>();
	values.add( CheckData.TYPE_MEDICINE_CHINESE);
	values.add(CheckData.TYPE_MEDICINE_WEST);
	values.add( CheckData.TYPE_MEETING);
	values.add(CheckData.TYPE_VIDEO);
	List<CheckData> checkDatas = checkDataManager.findResCheckData(CheckData.TYPE,values, pBean);
	List<CheckDataVO> checkDataVOs = new LinkedList<CheckDataVO>();
	for (CheckData checkData : checkDatas) {
	    CheckDataVO checkDataVO = new CheckDataVO(checkData);
	    checkDataVOs.add(checkDataVO);
	}
	return jsonUtil.setJsonObject("checkDatas", checkDataVOs).setResultSuccess().toJsonString();
	
    }
    
    
    @RequestMapping(value = "/admin/passCheckData", method = RequestMethod.POST)
    @ResponseBody
    public String admin_passCheckData(@Validated @ModelAttribute CheckDataVO checkDataVO, BindingResult result) {
	if (result.hasErrors())
	    return toErrorJson(result);
	CheckData checkData = checkDataManager.find(checkDataVO.getId());
	if (checkData == null)
	    return jsonUtil.setResultFail("此id的审核记录不存在  ").toJsonString();
	switch (checkDataVO.type) {
	case CheckData.TYPE_MEDICINE_CHINESE:
	    enterChineseMedicineManager.active(checkDataVO.getId());
	    break;
	case CheckData.TYPE_MEDICINE_WEST:
	    enterWestMedicineManager.active(checkDataVO.getId());
	    break;
	case CheckData.TYPE_MEETING:
	    meetingManager.active(checkDataVO.getId());
	    break;
	case CheckData.TYPE_VIDEO:
	    videoManager.active(checkDataVO.getId());
	    break;
	default:
	    return jsonUtil.setResultFail("类型参数错误").toJsonString();
	}
	checkDataManager.delete(checkDataVO.getId());
	return jsonUtil.setResultSuccess().toJsonString();
    }
    
    
    
    
    
    @RequestMapping(value = "/admin/uploadExcel/chinese", method = RequestMethod.POST)
    @ResponseBody
    public String admin_uploadExcel_chinese(HttpSession httpSession,
	    @RequestParam("excel") CommonsMultipartFile excel) {
	String format = FileUtil.getSuffixByFilename(excel.getOriginalFilename());
	if(!format.equals("xls") && !format.equals("xlsx"))
	    return jsonUtil.setResultFail().setTip("不允许上传此种格式excel").toJsonString();
	String fileName =DateUtil.getSimpleDateTime(new Date()) + StringUtil.generateCode(6) + FileUtil.getSuffixByFilename(excel.getOriginalFilename());
	Result res = FileUtil.saveFile(UploadController.SEVER_TEMP, excel, fileName);
	if(!res.isSuccess()){
	    return jsonUtil.setResultFail().setTip(res.getMessage()).toJsonString();
	}
	String savePath = FileUtil.getRootPath() + res.getMessage();
	excelUtil.setExcelPath(savePath);
	excelUtil.setChineseTypeToDatabase();
	return jsonUtil.setResultSuccess().toJsonString();
    }
    
    
    @RequestMapping(value = "/admin/uploadExcel/west", method = RequestMethod.POST)
    @ResponseBody
    public String admin_uploadExcel_west(HttpSession httpSession,
	    @RequestParam("excel") CommonsMultipartFile excel) {
	String format = FileUtil.getSuffixByFilename(excel.getName());
	if(!format.equals("xls") && !format.equals("xlsx"))
	    return jsonUtil.setResultFail().setTip("不允许上传此种格式excel").toJsonString();
	String fileName =DateUtil.getSimpleDateTime(new Date()) + StringUtil.generateCode(6) + FileUtil.getSuffixByFilename(excel.getOriginalFilename());
	Result res = FileUtil.saveFile(UploadController.SEVER_TEMP, excel, fileName);
	if(!res.isSuccess()){
	    return jsonUtil.setResultFail().setTip(res.getMessage()).toJsonString();
	}
	String savePath = FileUtil.getRootPath() + res.getMessage();
	excelUtil.setExcelPath(savePath);
	excelUtil.setWestTypeToDatabase();
	return jsonUtil.setResultSuccess().toJsonString();
    }
    
    @RequestMapping(value = "/admin/searchMedicine", method = RequestMethod.POST)
    @ResponseBody
    public String user_SearchMedicine(
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
	if (StringUtil.hasText(keyword)) {
	    List<BackGroundMedicineVO> res = new LinkedList<BackGroundMedicineVO>();
	    List<BackGroundMedicineVO> medicineTypeResult = medicineTypeManager.searchBackGroundVO(keyword);
	    
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }
    
    @RequestMapping(value = "/admin/deleteMeeting", method = RequestMethod.POST)
    @ResponseBody
    public String user_deleteMeeting(
	    @RequestParam(value = "id", required = false, defaultValue = "") int id) {
	if(id < 1)
	    return jsonUtil.setResultFail("参数不合法").toJsonString();
	Meeting meeting = meetingManager.find(id);
	if(meeting == null)
	    return jsonUtil.setResultFail("此会议不存在").toJsonString();
	meetingManager.delete(id);
	return jsonUtil.setResultSuccess().toJsonString();
    }
    
    @RequestMapping(value = "/admin/getMeetings", method = RequestMethod.POST)
    @ResponseBody
    public String user_getMeetings(
	    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	    @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
	
    }
    
    @RequestMapping(value = "/admin/getMeetingBySubject", method = RequestMethod.POST)
    @ResponseBody
    public String user_getMeetingBySubject(
	    @RequestParam(value = "subject", required = false, defaultValue = "") String subject) {
	if(!StringUtil.hasText(subject))
	    return jsonUtil.setResultFail("参数不合法").toJsonString();
	List<Meeting> meeting = meetingManager.findByParam(Meeting.SUBJECT, subject);
	return jsonUtil.setResultSuccess().setModelList(meeting).toJsonString("/admin/getMeetingBySubject");
    }
    
    @RequestMapping(value = "/admin/searchMeeting", method = RequestMethod.POST)
    @ResponseBody
    public String user_searchMeeting(
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
	if (StringUtil.hasText(keyword)) {
	    List<Meeting> resultName = meetingManager.search(Meeting.NAME, keyword);
	   List<Meeting> resultSubject = meetingManager.search(Meeting.SUBJECT, keyword);
	   return jsonUtil.setResultSuccess().setModelList(resultName).setModelList(resultSubject).toJsonString();
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }
    
    
    @RequestMapping(value = "/admin/deleteEnterprise", method = RequestMethod.POST)
    @ResponseBody
    public String user_deleteEnterprise(
	    @RequestParam(value = "id", required = false, defaultValue = "") int id) {
	if(id < 1)
	    return jsonUtil.setResultFail("参数不合法").toJsonString();
	Enterprise enterprise = enterpriseManager.find(id);
	if(enterprise == null)
	    return jsonUtil.setResultFail("此企业不存在不存在").toJsonString();
	enterpriseManager.delete(id);
	return jsonUtil.setResultSuccess().toJsonString();
    }
    
    
    @RequestMapping(value = "/admin/searchEnterprise", method = RequestMethod.POST)
    @ResponseBody
    public String user_searchEnterprise(
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
	if (StringUtil.hasText(keyword)) {
	    List<Enterprise> resultName = enterpriseManager.search(Enterprise.NAME, keyword);
	    List<Enterprise> resultType = enterpriseManager.search(Enterprise.TYPE, keyword);
	    return jsonUtil.setResultSuccess().setModelList(resultName).setModelList(resultType).toJsonString();
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }
   
   

}
