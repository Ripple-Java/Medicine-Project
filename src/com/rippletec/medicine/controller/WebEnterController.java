package com.rippletec.medicine.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.ControllerException;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.ServiceException;
import com.rippletec.medicine.exception.UtilException;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.Subject;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.model.extend.EnterChMedicineBean;
import com.rippletec.medicine.model.extend.EnterWestMedicineBean;
import com.rippletec.medicine.service.EnterWestMedicineManager;
import com.rippletec.medicine.utils.ErrorCode;
import com.rippletec.medicine.utils.MD5Util;
import com.rippletec.medicine.utils.ParamMap;
import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.medicine.vo.web.EnterChineseVO;
import com.rippletec.medicine.vo.web.EnterWestVO;
import com.rippletec.medicine.vo.web.EnterpriseInfoVO;
import com.rippletec.medicine.vo.web.MeetingVo;
import com.rippletec.medicine.vo.web.VideoVO;
import com.sun.org.apache.bcel.internal.generic.NEW;


@Controller
@RequestMapping("/Web")
public class WebEnterController extends BaseController {
    

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
    
    
    
    @RequestMapping(value = "/enteruser/setInfo", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_setInfo(HttpSession httpSession, String name, int type, String number, String imgUrl) {
        if (!StringUtil.isEnterpriseName(name) || !StringUtil.isEnterpriseType(type) || !StringUtil.isNumber(number)) {
           return jsonUtil.setFailRes(ErrorCode.PARAM_FORMAT_ERROR).toJson();
        }
        Enterprise enterprise = new Enterprise(type, name, number, null, imgUrl,Enterprise.ON_VAlIDATING);
        httpSession.setAttribute(Enterprise.CLASS_NAME, enterprise);
        httpSession.setAttribute("infoFlag", true);
        return jsonUtil.setSuccessRes().toJson();
    }

    @RequestMapping(value = "/enteruser/register", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_setRegister(HttpSession httpSession, String email, String password) throws DaoException, ServiceException {
        Object infoAttr = httpSession.getAttribute("infoFlag");
        if (infoAttr == null){
            return jsonUtil.setFailRes(ErrorCode.ILLEGAL_ACCESS_ERROR).toJson();
        }
        if (!StringUtil.isEmail(email) || !StringUtil.hasText(password)) {
            return jsonUtil.setFailRes(ErrorCode.PARAM_FORMAT_ERROR).toJson();
        }
        User user;
	try {
	    user = userManager.findByAccount(email);
	    if (user.getStatus() != User.STATUS_VAlIDATING){
        	return jsonUtil.setFailRes(ErrorCode.USER_EXISTED_ERROR).toJson();
            }
            enterpriseManager.deleteByUser(user.getId());
	} catch (DaoException e) {}
        userManager.registerEnterprise(email, password, httpSession);
        return jsonUtil.setSuccessRes().toJson();
    }

    @RequestMapping(value = "/enteruser/validate", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_setValidate(String action, String code, String account) {
        if (!StringUtil.hasText(action, code, account)){
            return ParamError();
        }
        User user;
	try {
	    user = userManager.findByAccount(account);
	    if (user.getStatus() != User.STATUS_VAlIDATING){
		return jsonUtil.setFailRes(ErrorCode.BAD_ACCESS_ERROR).toJson();
	    }
            try {
                if (MD5Util.validPasswd(user.getAccount() + user.getPassword(), code)) {
                    userManager.validUser(account);
                    enterpriseManager.validEnterPrise(user);
                    return jsonUtil.setSuccessRes().toJson();
                }
                return jsonUtil.setFailRes(ErrorCode.ENTERPRISE_VALICATE_ERROR).toJson();
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                return jsonUtil.setFailRes(ErrorCode.INTENAL_ERROR).toJson();
            }
	} catch (DaoException e) {
	    return jsonUtil.setFailRes(ErrorCode.BAD_ACCESS_ERROR).toJson();
	}
    }

    @RequestMapping(value = "/enteruser/login", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_setLogin(HttpSession httpSession, String account, String password) throws DaoException, ServiceException {
        if (!StringUtil.isEmail(account) || !StringUtil.hasText(password)) {
            return jsonUtil.setFailRes(ErrorCode.PARAM_FORMAT_ERROR).toJson();
        }
        if (userManager.isLogined(httpSession)){
    	    userManager.loginOutEnterprise(account, httpSession);
        }
        userManager.adminLogin(account, password, httpSession);
    	User user = userManager.findByAccount(account);
    	Enterprise enterprise = enterpriseManager.findByUser(user);
    	httpSession.setAttribute(EnterChineseMedicine.ENTERPRISE_ID, enterprise.getId());
    	return jsonUtil.setSuccessRes().toJson();
    }

//    @RequestMapping(value = "/enteruser/getBackPassword", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public String enterprise_getBackPassword( String account) {
//        if (!StringUtil.isEmail(account)) {
//            return jsonUtil.setFailRes(ErrorCode.PARAM_FORMAT_ERROR).toJson();
//        }
//        if (!userManager.isExist(account))
//        if (userManager.initPassword(account))
//            return jsonUtil.setSuccessRes().toJson();
//        
//    }

    @RequestMapping(value = "/enterprise/loginOut", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_setLoginOut(HttpSession httpSession) throws DaoException, ControllerException {
	String account = getAccount(httpSession);
	userManager.loginOutEnterprise(account, httpSession);
        return jsonUtil.setSuccessRes().toJson();        
    }

    @RequestMapping(value = "/enterprise/addMeeting", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_addMeeting(HttpSession httpSession, @Validated @ModelAttribute MeetingVo meeting, BindingResult result) throws DaoException, ControllerException {
        if (result.hasErrors()){
            return ParamError();
        }
        User user = userManager.findByAccount(getAccount(httpSession));
        Enterprise enterprise = enterpriseManager.findByUser(user);
        Subject subject = subjectManager.find(meeting.getSubject_id());
        meetingManager.addMeeting(enterprise, meeting, subject);
        return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/enterprise/video/add", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_addVideo(HttpSession httpSession, @Validated @ModelAttribute VideoVO video, BindingResult result) throws DaoException, ControllerException {
        if (result.hasErrors()){
            return ParamError();
        }
        Subject subject = subjectManager.find(video.getSubject_id());
        Enterprise enterprise = enterpriseManager.find(getEnterpriseId(httpSession));
        videoManager.addVideo(enterprise, video, subject);
        return jsonUtil.setSuccessRes().toJson();
    }
    
    
    @RequestMapping(value = "/enterprise/info/add", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_addInfo(HttpSession httpSession, @Validated @ModelAttribute EnterpriseInfoVO enterpriseInfoVO, BindingResult result) throws DaoException, ControllerException {
        if (result.hasErrors()){            
            return ParamError();
        }
        int enterpriseId = getEnterpriseId(httpSession);
        enterpriseManager.updateInfo(enterpriseId, enterpriseInfoVO);
        return jsonUtil.setSuccessRes().toJson();
    }


    @RequestMapping(value = "/enterprise/addChinMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_addChinMedicine(HttpSession httpSession, @Validated @ModelAttribute EnterChineseVO entChineseVO, BindingResult result) throws DaoException, ControllerException {
        if (result.hasErrors())
            return toErrorJson(result);
        User user = userManager.findByAccount(getAccount(httpSession));
        Enterprise enterprise = enterpriseManager.findByUser(user);
        ChineseMedicine chineseMedicine = chineseMedicineManager.find(entChineseVO.getMedicineId());
        CheckData checkData = enterChineseMedicineManager.addMedicine(enterprise, chineseMedicine, entChineseVO);
        checkDataManager.save(checkData);
        return jsonUtil.setSuccessRes().toJson();
    }

    @RequestMapping(value = "/enterprise/addWestMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_addWestMedicine(HttpSession httpSession, @Validated @ModelAttribute EnterWestVO enterWestVO, BindingResult result) throws DaoException, ControllerException {
        if (result.hasErrors()){
            return ParamError();
        }
        User user = userManager.findByAccount(getAccount(httpSession));
        Enterprise enterprise = enterpriseManager.findByUser(user);
        WestMedicine westMedicine = westMedicineManager.find(enterWestVO.getMedicineId());
        CheckData checkData = enterWestMedicineManager.addMedicine(enterprise, westMedicine, enterWestVO);
        checkDataManager.save(checkData);
        return jsonUtil.setSuccessRes().toJson();

    }
    
    @RequestMapping(value = "/enterprise/getMedicine", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getMedicines(HttpSession httpSession, int pageSize, int page) throws UtilException, DaoException, ControllerException{
	Enterprise enterprise = enterpriseManager.find(getEnterpriseId(httpSession));
	List<BackGroundMedicineVO> westBackGroundMedicineVOs;
	try {
	    westBackGroundMedicineVOs = enterWestMedicineManager.getBackGroundVO(enterprise, 0, 0);
	} catch (DaoException e) {
	    westBackGroundMedicineVOs = new LinkedList<BackGroundMedicineVO>();
	}
	List<BackGroundMedicineVO> chineseBackGroundMedicineVOs;
	try {
	    chineseBackGroundMedicineVOs = enterChineseMedicineManager.getBackGroundVO(enterprise, 0, 0);
	    westBackGroundMedicineVOs.addAll(chineseBackGroundMedicineVOs);
	} catch (DaoException e) {}
	if(StringUtil.isEmpty(westBackGroundMedicineVOs)){
	    return jsonUtil.setFailRes(ErrorCode.DB_NO_ENITY_ERROR).toJson();
	}
	if(page == 0 && pageSize == 0)
	    return jsonUtil.setSuccessRes().setModels(westBackGroundMedicineVOs).toJson();
	PageBean pageBean = new PageBean(page, 0, pageSize);
	int start = pageBean.offset;
	int end = start + pageBean.pageSize;
	if(pageBean.offset+pageBean.pageSize > westBackGroundMedicineVOs.size())
	    end = westBackGroundMedicineVOs.size();
	return jsonUtil.setSuccessRes().setModels(westBackGroundMedicineVOs.subList(start,end)).toJson("/enterprise/getMedicine");
    }
    
    @RequestMapping(value = "/enterprise/deleteMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_deleteMedicine(HttpSession httpSession,int id, int type) throws DaoException, ControllerException {
	Integer enterpriseId = getEnterpriseId(httpSession);
	if(type == Medicine.ENTER_CHINESE){
	    enterChineseMedicineManager.deleteMedicine(id, enterpriseId);
	}else if (type == Medicine.ENTER_WEST) {
	    enterWestMedicineManager.deleteMedicine(id, enterpriseId);
	}
	else {
	    return ParamError();
	}
	return jsonUtil.setSuccessRes().toJson();
	
    }
    

    @RequestMapping(value = "/enterprise/getCount", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getCount(HttpSession httpSession,int type) throws UtilException, ControllerException, NumberFormatException, DaoException {
        int count = 0;
        int enterpriseId = getEnterpriseId(httpSession);

        switch (type) {
            case 1:
                count = enterChineseMedicineManager.getCount(EnterChineseMedicine.TABLE_NAME, EnterChineseMedicine.ENTERPRISE_ID, enterpriseId) + 
                	enterWestMedicineManager.getCount(EnterWestMedicine.TABLE_NAME, EnterWestMedicine.ENTERPRISE_ID, enterpriseId);
                break;
            case 2:
                count = meetingManager.getCount(Meeting.TABLE_NAME, Meeting.ENTERPRISE_ID, enterpriseId);
                break;
            case 3:
                count = videoManager.getCount(Video.TABLE_NAME, Video.ENTERPRISE_ID, enterpriseId);
                break;
            default:
        	return ParamError();

        }
        return jsonUtil.setSuccessRes().setObject("count", count).toJson("/enterprise/getCount");
    }
    
    @RequestMapping(value = "/enterprise/searchMedicine", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_searchMedicine(HttpSession httpSession, String keyword) throws ControllerException {
        if(!StringUtil.hasText(keyword)){
            return ParamError();
        }
        int enterpriseId = getEnterpriseId(httpSession);
        List<BackGroundMedicineVO> nameResult;
	try {
	    nameResult = enterChineseMedicineManager.searchBackGroundVO(keyword, EnterChineseMedicine.NAME, EnterChineseMedicine.ENTERPRISE_ID, enterpriseId);
	} catch (DaoException e) {
	    nameResult = new LinkedList<BackGroundMedicineVO>();
	}
        List<BackGroundMedicineVO> nameResult_2;
	try {
	    nameResult_2 = enterWestMedicineManager.searchBackGroundVO(keyword, EnterWestMedicine.NAME, EnterWestMedicine.ENTERPRISE_ID, enterpriseId);
	} catch (DaoException e) {
	    nameResult_2 = null;
	}
        List<BackGroundMedicineVO> typeResult = new LinkedList<BackGroundMedicineVO>();
        List<MedicineType> medicineTypes;
	try {
	    medicineTypes = medicineTypeManager.search(MedicineType.TABLE_NAME, MedicineType.NAME, keyword, MedicineType.FLAG, 1);
	    for (MedicineType medicineType : medicineTypes) {
		try {
		    typeResult.addAll(medicineTypeManager.getEnterBackGroundMedicineVO(medicineType, getEnterpriseId(httpSession)));
		} catch (Exception e) {}
	    }
	    if(typeResult.size() < 1){
		typeResult = null;
	    }
	} catch (DaoException e) {
	    typeResult = null;
	}
        
        nameResult.addAll(nameResult_2);
        if(StringUtil.isEmpty(nameResult) && StringUtil.isEmpty(typeResult)){
            return jsonUtil.setFailRes(ErrorCode.DB_NO_ENITY_ERROR).toJson();
        }
        return jsonUtil.setSuccessRes().setObject("nameResult", nameResult).setObject("typeResult", typeResult).toJson();
       
    }
    
    
    @RequestMapping(value = "/enterprise/getMeeting", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getMeeting(HttpSession httpSession, int pageSize, int page) throws DaoException, ControllerException, UtilException {
	int enterpriseId = getEnterpriseId(httpSession);
	List<Meeting> meetings = null;
	if(pageSize == 0 && page == 0){
	     meetings = meetingManager.findBySql(Meeting.TABLE_NAME, Meeting.ENTERPRISE_ID, enterpriseId);
	     return jsonUtil.setSuccessRes().setModels(meetings).toJson("/enterprise/getMeeting");
	}
	
	meetings = meetingManager.findBySql(Meeting.TABLE_NAME, Meeting.ENTERPRISE_ID, enterpriseId, new PageBean(page, 0, pageSize));
	return jsonUtil.setSuccessRes().setModels(meetings).toJson("/enterprise/getMeeting");
    }
    
    @RequestMapping(value = "/enterprise/getMeeting/detail", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getMeetingDetial(HttpSession httpSession, int id) throws ControllerException, DaoException, UtilException {
	int enterpriseId = getEnterpriseId(httpSession);
	Meeting meeting = meetingManager.getMeeting(id, enterpriseId);
	return jsonUtil.setSuccessRes().setObject("Meeting", meeting).toJson("/enterprise/getMeeting/detail");
    }
    
    @RequestMapping(value = "/enterprise/deleteMeeting", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_deleteMeeting(HttpSession httpSession, int id) throws ControllerException, DaoException {
	Integer enterpriseId = getEnterpriseId(httpSession);
	meetingManager.deleteMeeting(id, enterpriseId);
	return jsonUtil.setSuccessRes().toJson();
    }
    

    @RequestMapping(value = "/enterprise/updateMeeting", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_updateMeeting(HttpSession httpSession, @Validated @ModelAttribute MeetingVo meeting, BindingResult result,int meetingId) throws DaoException, ControllerException {
        if (result.hasErrors()){
            return ParamError();
        }
        int enterpriseId = getEnterpriseId(httpSession);
        Integer subjectId = meeting.getSubject_id();
        Subject subject = subjectManager.find(subjectId);
        meetingManager.updateMeeting(meetingId , enterpriseId, meeting, subject);
        return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/enterprise/searchMeeting", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_searchMeeting(HttpSession httpSession, String keyword) throws UtilException, ControllerException {
        if(!StringUtil.hasText(keyword)){
            return ParamError();
        }
        Integer enterpriseId = getEnterpriseId(httpSession);
        List<Meeting> nameResult;
	try {
	    nameResult = meetingManager.search(Meeting.TABLE_NAME, Meeting.NAME, keyword, Meeting.ENTERPRISE_ID, enterpriseId);
	} catch (DaoException e) {
	    nameResult = null;
	}
        List<Meeting> typeResult = new LinkedList<Meeting>();
        List<Subject> subjects;
	try {
	    subjects = subjectManager.search(Subject.NAME, keyword);
	    for (Subject subject : subjects) {
		try {
		    typeResult.addAll(meetingManager.findBySubject(subject.getId(), enterpriseId));
		} catch (Exception e) {}   
	    }
	} catch (DaoException e) {
	    typeResult = null;
	}
	if(StringUtil.isEmpty(nameResult) && StringUtil.isEmpty(typeResult)){
	    return jsonUtil.setFailRes(ErrorCode.DB_NO_ENITY_ERROR).toJson();
	}
        return jsonUtil.setSuccessRes().setObject("nameResult", nameResult).setObject("typeResult", typeResult).toJson("/enterprise/getMeeting");
       
    }
    
    @RequestMapping(value = "/enterprise/meeting/get/BySubjcet", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getMeetingBySubject(HttpSession httpSession,int subjcetId) throws DaoException, ControllerException, UtilException {
        Subject subject = subjectManager.find(subjcetId);
        List<Meeting> meetings = meetingManager.findBySubject(subjcetId, getEnterpriseId(httpSession));
        return jsonUtil.setSuccessRes().setModels(meetings).toJson("/enterprise/getMeeting");
       
    }
    
    
    @RequestMapping(value = "/enterprise/video/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getVideo(HttpSession httpSession,int pageSize, int page) throws ControllerException, DaoException, UtilException {
	int enterpriseId = getEnterpriseId(httpSession);
	List<Video> videos = null;
	if(pageSize == 0 && page == 0){
	    videos = videoManager.findBySql(Video.TABLE_NAME, Video.ENTERPRISE_ID, enterpriseId);
	}else {
	    videos = videoManager.findBySql(Video.TABLE_NAME, Video.ENTERPRISE_ID, enterpriseId, new PageBean(page, 0, pageSize));	    
	}
	return jsonUtil.setSuccessRes().setModels(videos).toJson("/enterprise/video/get");
    }
    
    @RequestMapping(value = "/enterprise/video/get/detail", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getVideoDetial(HttpSession httpSession,
	    @RequestParam(value = "id", defaultValue = "0", required = false) int id) throws ControllerException, DaoException, UtilException {
	int enterpriseId = getEnterpriseId(httpSession);
	Video video = videoManager.getVideo(id, enterpriseId);
	return jsonUtil.setSuccessRes().setObject(Video.CLASS_NAME, video).toJson("/enterprise/video/get/detail");
    }
    
    @RequestMapping(value = "/enterprise/video/delete", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_deleteVideo(HttpSession httpSession,int id) throws ControllerException, DaoException {
	Integer enterpriseId = getEnterpriseId(httpSession);
	videoManager.deleteVideo(id, enterpriseId);
	return jsonUtil.setSuccessRes().toJson();
    }
    

    @RequestMapping(value = "/enterprise/video/update", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_updateVideo(HttpSession httpSession,
	    @Validated @ModelAttribute VideoVO video, BindingResult result,int videoId) throws DaoException, ControllerException {
        if (result.hasErrors()){
            return ParamError();
        }
        int enterpriseId = getEnterpriseId(httpSession);
        Integer subjectId = video.getSubject_id();
        Subject subject = subjectManager.find(subjectId);
        videoManager.updateVideo(videoId , enterpriseId, video, subject);
        return jsonUtil.setSuccessRes().toJson();
    }
    
    
    
    @RequestMapping(value = "/enterprise/video/search", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_searchVideo(HttpSession httpSession, String keyword) throws UtilException, ControllerException {
        if(!StringUtil.hasText(keyword)){
            return ParamError();
        }
        Integer enterpriseId = getEnterpriseId(httpSession);
        List<Video> nameResult;
	try {
	    nameResult = videoManager.search(Video.TABLE_NAME, Video.NAME, keyword, Video.ENTERPRISE_ID, enterpriseId);
	} catch (DaoException e) {
	    nameResult = null;
	}
        List<Video> typeResult = new LinkedList<Video>();
        List<Subject> subjects;
	try {
	    subjects = subjectManager.search(Subject.NAME, keyword);
	    for (Subject subject : subjects) {
		try {
		    typeResult.addAll(videoManager.findBySubject(subject.getId(), enterpriseId));
		} catch (Exception e) {}
	    }
	} catch (DaoException e) {
	    typeResult = null;
	}
	if(StringUtil.isEmpty(nameResult) && StringUtil.isEmpty(typeResult)){
	    return jsonUtil.setFailRes(ErrorCode.DB_NO_ENITY_ERROR).toJson();
	}
        return jsonUtil.setSuccessRes().setObject("nameResult", nameResult).setObject("typeResult", typeResult).toJson("/enterprise/video/get");
        
       
    }
    
    
    @RequestMapping(value = "/enterprise/video/get/BySubjcet", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getVideoBySubject(HttpSession httpSession,int subjcetId) throws DaoException, ControllerException, UtilException {
        Subject subject = subjectManager.find(subjcetId);
        List<Video> videos = videoManager.findBySubject(subjcetId, getEnterpriseId(httpSession));
        return jsonUtil.setSuccessRes().setModels(videos).toJson("/enterprise/video/get");
       
    }
    
    @RequestMapping(value = "/enterprise/info/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getInfo(HttpSession httpSession) throws DaoException, ControllerException, UtilException {
	int enterpriseId = getEnterpriseId(httpSession);
	Enterprise enterprise = enterpriseManager.find(enterpriseId);
	return jsonUtil.setObject("enterprise", enterprise).toJson("/enterprise/info/get");
    }
    
    @RequestMapping(value = "/enterprise/upatePassword", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_upatePassword(HttpSession httpSession, String oldPassword, String newPassword) throws DaoException, ServiceException, ControllerException {
	if(!StringUtil.hasText(oldPassword, newPassword)){
	    return ParamError();
	}
        String userAccount = getAccount(httpSession);
        userManager.updatePassword(userAccount, oldPassword, newPassword);
        return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/enterprise/ChinMedicine/update", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_updateChinMedicine(HttpSession httpSession,
	    @Validated @ModelAttribute EnterChineseVO entChineseVO, BindingResult result,int id) throws DaoException, ControllerException {
        if (result.hasErrors()){
            return ParamError();
        }
        Enterprise enterprise = enterpriseManager.find(getEnterpriseId(httpSession));
        enterChineseMedicineManager.updateMedicine(id, entChineseVO, enterprise);
        return jsonUtil.setSuccessRes().toJson();
    }
    
    
    @RequestMapping(value = "/enterprise/WestMedicine/update", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_updateWestMeicine(HttpSession httpSession,
	    @Validated @ModelAttribute EnterWestVO enterWestVO, BindingResult result, int id) throws DaoException, ControllerException {
        if (result.hasErrors()){
            return ParamError();
        }
        Enterprise enterprise = enterpriseManager.find(getEnterpriseId(httpSession));
        enterWestMedicineManager.updateMedicine(id, enterWestVO, enterprise);
        return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/enterprise/EnterMedicine/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getEnterpriseMedicine(
	    HttpSession httpSession, int id, int type) throws DaoException, ControllerException, UtilException {
	    Object jsonObject = null;
	    int enterpriseId = getEnterpriseId(httpSession);
	    if(type == Medicine.ENTER_CHINESE){	
		EnterChineseMedicine medicine = enterChineseMedicineManager.getMedicine(id, enterpriseId);
		jsonObject = new EnterChMedicineBean(medicine);
	    }
	    else if (type == Medicine.ENTER_WEST){	
		EnterWestMedicine medicine = enterWestMedicineManager.getMedicine(id, enterpriseId);
		jsonObject = new EnterWestMedicineBean(medicine);
	    }else {
		return ParamError();
	    }
	    return jsonUtil.setSuccessRes().setObject("entity", jsonObject).toJson("/enterprise/EnterMedicine/get");
    }
 
 


}
