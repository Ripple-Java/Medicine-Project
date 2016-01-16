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
import com.rippletec.medicine.exception.DaoException;
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
        if (StringUtil.isEnterpriseName(name) && StringUtil.isEnterpriseType(type) && StringUtil.isNumber(number)) {
            Enterprise enterprise = new Enterprise(type, name, number, null, imgUrl,Enterprise.ON_VAlIDATING);
            httpSession.setAttribute(Enterprise.CLASS_NAME, enterprise);
            httpSession.setAttribute("infoFlag", true);
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail().setTip("参数不正确").toJsonString();
    }

    @RequestMapping(value = "/enteruser/register", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_setRegister(HttpSession httpSession, String email, String password) {

        Object infoAttr = httpSession.getAttribute("infoFlag");
        if (infoAttr == null)
            return jsonUtil.setResultFail("非法操作").toJsonString();
        if (StringUtil.isEmail(email) && StringUtil.hasText(password)) {
            User user = userManager.findByAccount(email);
            if (user != null) {
                if (user.getStatus() != User.STATUS_VAlIDATING)
                    return jsonUtil.setResultFail().setTip("该用户已存在").toJsonString();
                enterpriseManager.deleteByUser(user.getId());
            }
            if (userManager.registerEnterprise(email, password, httpSession))
                return jsonUtil.setResultSuccess().toJsonString();
            return jsonUtil.setResultFail().toJsonString();
        }
        return jsonUtil.setResultFail().setTip("参数不正确").toJsonString();
    }

    @RequestMapping(value = "/enteruser/validate", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_setValidate(String action, String code, String account) {
        if (!StringUtil.hasText(action, code, account))
            return jsonUtil.setResultFail("参数不正确").toJsonString();
        User user = userManager.findByAccount(account);
        if (user != null) {
            if (user.getStatus() != User.STATUS_VAlIDATING)
                return jsonUtil.setResultFail("此用户不需要要验证").toJsonString();
            try {
                if (MD5Util.validPasswd(user.getAccount() + user.getPassword(), code)) {
                    userManager.validUser(account);
                    enterpriseManager.validEnterPrise(user);
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

    @RequestMapping(value = "/enteruser/login", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_setLogin(
            HttpSession httpSession,
            @RequestParam(value = "account", required = false, defaultValue = "") String account,
            @RequestParam(value = "password", required = false, defaultValue = "") String password) {
        if (StringUtil.isEmail(account) && StringUtil.hasText(password)) {
            if (userManager.isLogined(httpSession)){
        	userManager.loginOutEnterprise(account, httpSession);
            }
            Result result = userManager.adminLogin(account, password, httpSession);
            if (result.isSuccess()){
        	User user = userManager.findByAccount(account);
        	Enterprise enterprise = enterpriseManager.findByUser(user);
        	httpSession.setAttribute(EnterChineseMedicine.ENTERPRISE_ID, enterprise.getId());
        	jsonUtil.setResultSuccess();
            }
            else
                jsonUtil.setResultFail(result.getErrorCode()).toJsonString();
        } else
            jsonUtil.setResultFail("参数错误");
        return jsonUtil.toJsonString();
    }

    @RequestMapping(value = "/enteruser/getBackPassword", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getBackPassword(
            @RequestParam(value = "account", required = false, defaultValue = "") String account) {
        if (StringUtil.isEmail(account)) {
            if (!userManager.isExist(account))
                return jsonUtil.setResultFail("该用户不存在").toJsonString();
            if (userManager.initPassword(account))
                return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail("参数错误").toJsonString();
    }

    @RequestMapping(value = "/enterprise/loginOut", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_setLoginOut(
            HttpSession httpSession) {
        if (userManager.loginOutEnterprise(getAccount(httpSession), httpSession)){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        else return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/enterprise/addMeeting", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_addMeeting(HttpSession httpSession, @Validated @ModelAttribute MeetingVo meeting, BindingResult result) throws DaoException {
        if (result.hasErrors())
            return toErrorJson(result);
        User user = userManager.findByAccount(getAccount(httpSession));
        Enterprise enterprise = enterpriseManager.findByUser(user);
        Subject subject = subjectManager.find(meeting.getSubject_id());
        if (meetingManager.addMeeting(enterprise, meeting, subject))
            return jsonUtil.setResultSuccess().toJsonString();
        return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/enterprise/video/add", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_addVideo(HttpSession httpSession, @Validated @ModelAttribute VideoVO video, BindingResult result) throws DaoException {
        if (result.hasErrors())
            return toErrorJson(result);
        Subject subject = subjectManager.find(video.getSubject_id());
        Enterprise enterprise = enterpriseManager.find(getEnterpriseId(httpSession));
        if(enterprise == null)
            return jsonUtil.setResultFail("企业信息不存在").toJsonString();
        
        Result res = videoManager.addVideo(enterprise, video, subject);
        if(res.isSuccess())
            return jsonUtil.setResultSuccess().toJsonString();
        return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
    }
    
    
    @RequestMapping(value = "/enterprise/info/add", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_addInfo(HttpSession httpSession, @Validated @ModelAttribute EnterpriseInfoVO enterpriseInfoVO, BindingResult result) throws DaoException {
        if (result.hasErrors()){            
            return toErrorJson(result);
        }
        int enterpriseId = getEnterpriseId(httpSession);
        Result res = enterpriseManager.updateInfo(enterpriseId, enterpriseInfoVO);
        if(res.isSuccess()){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
    }


    @RequestMapping(value = "/enterprise/addChinMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_addChinMedicine(HttpSession httpSession, @Validated @ModelAttribute EnterChineseVO entChineseVO, BindingResult result) throws DaoException {
        if (result.hasErrors())
            return toErrorJson(result);
        User user = userManager.findByAccount(getAccount(httpSession));
        Enterprise enterprise = enterpriseManager.findByUser(user);
        ChineseMedicine chineseMedicine = chineseMedicineManager.find(entChineseVO.getMedicineId());
        CheckData checkData = enterChineseMedicineManager.addMedicine(enterprise, chineseMedicine, entChineseVO);
        if (checkData == null)
            return jsonUtil.setResultFail("添加失败").toJsonString();
        checkDataManager.save(checkData);
        return jsonUtil.setResultSuccess().toJsonString();
    }

    @RequestMapping(value = "/enterprise/addWestMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_addWestMedicine(HttpSession httpSession, @Validated @ModelAttribute EnterWestVO enterWestVO, BindingResult result) throws DaoException {
        if (result.hasErrors())
            return toErrorJson(result);
        User user = userManager.findByAccount(getAccount(httpSession));
        Enterprise enterprise = enterpriseManager.findByUser(user);
        WestMedicine westMedicine = westMedicineManager.find(enterWestVO.getMedicineId());
        CheckData checkData = enterWestMedicineManager.addMedicine(enterprise, westMedicine, enterWestVO);
        if (checkData == null)
            return jsonUtil.setResultFail("保存失败").toJsonString();
        checkDataManager.save(checkData);
        return jsonUtil.setResultSuccess().toJsonString();

    }
    
    @RequestMapping(value = "/enterprise/getMedicine", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getMedicines(HttpSession httpSession,
	    @RequestParam(value = "pageSize", defaultValue = "-1", required = false) int pageSize,
	    @RequestParam(value = "page", defaultValue = "-1", required = false) int page) throws DaoException {
	if(pageSize < 0 || page < 0){
	    return ParamError();
	}
	Enterprise enterprise = enterpriseManager.find(getEnterpriseId(httpSession));
	List<BackGroundMedicineVO> chineseBackGroundMedicineVOs = enterChineseMedicineManager.getBackGroundVO(enterprise, 0, 0);
	List<BackGroundMedicineVO> westBackGroundMedicineVOs = enterWestMedicineManager.getBackGroundVO(enterprise, 0, 0);
	westBackGroundMedicineVOs.addAll(chineseBackGroundMedicineVOs);
	if(page == 0 && pageSize == 0)
	    return jsonUtil.setResultSuccess().setModelList(westBackGroundMedicineVOs).toJsonString();
	PageBean pageBean = new PageBean(page, 0, pageSize);
	int start = pageBean.offset;
	int end = start + pageBean.pageSize;
	if(pageBean.offset+pageBean.pageSize > westBackGroundMedicineVOs.size())
	    end = westBackGroundMedicineVOs.size();
	if(westBackGroundMedicineVOs.size() == 0){
	    return jsonUtil.setResultFail("无相关结果").toJsonString();
	}
	return jsonUtil.setResultSuccess().setModelList(westBackGroundMedicineVOs.subList(start,end)).toJsonString("/enterprise/getMedicine");
    }
    
    @RequestMapping(value = "/enterprise/deleteMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_deleteMedicine(HttpSession httpSession,
	    @RequestParam(value = "id", defaultValue = "-1", required = false) int id,
	    @RequestParam(value = "type", defaultValue = "-1", required = false) int type) {
	if(id <= 0 || type <= 0){
	    return ParamError();
	}
	Integer enterpriseId = getEnterpriseId(httpSession);
	if(type == Medicine.ENTER_CHINESE){
	    if(enterChineseMedicineManager.deleteMedicine(id, enterpriseId)){
		return jsonUtil.setResultSuccess().toJsonString();
	    }else {
		return jsonUtil.setResultFail("删除失败").toJsonString();
	    }
	}else if (type == Medicine.ENTER_WEST) {
	    if(enterWestMedicineManager.deleteMedicine(id, enterpriseId)){
		return jsonUtil.setResultSuccess().toJsonString();
	    }else {
		return jsonUtil.setResultFail("删除失败").toJsonString();
	    }
	}
	else {
	    return ParamError();
	}
	
    }
    

    @RequestMapping(value = "/enterprise/getCount", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getCount(HttpSession httpSession,
            @RequestParam(value = "type", required = false, defaultValue = "0") int type) {
        if (type <= 0){
            return ParamError();
        }
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
        return jsonUtil.setResultSuccess().setJsonObject("count", count).toJsonString("/enterprise/getCount");
    }
    
    @RequestMapping(value = "/enterprise/searchMedicine", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_searchMedicine(HttpSession httpSession,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        if(!StringUtil.hasText(keyword)){
            return ParamError();
        }
        List<BackGroundMedicineVO> nameResult = enterChineseMedicineManager.searchBackGroundVO(keyword, EnterChineseMedicine.NAME, EnterChineseMedicine.ENTERPRISE_ID, getEnterpriseId(httpSession));
        List<BackGroundMedicineVO> nameResult_2 = enterWestMedicineManager.searchBackGroundVO(keyword, EnterWestMedicine.NAME, EnterWestMedicine.ENTERPRISE_ID, getEnterpriseId(httpSession));
        List<BackGroundMedicineVO> typeResult = new LinkedList<BackGroundMedicineVO>();
        List<MedicineType> medicineTypes = medicineTypeManager.search(MedicineType.TABLE_NAME, MedicineType.NAME, keyword, MedicineType.FLAG, 1);
        for (MedicineType medicineType : medicineTypes) {
	    typeResult.addAll(medicineTypeManager.getEnterBackGroundMedicineVO(medicineType, getEnterpriseId(httpSession)));
	}
        nameResult.addAll(nameResult_2);
        if(nameResult.size() < 1 && typeResult.size() < 1){
            return jsonUtil.setResultFail("无相关结果").toJsonString();
        }
        return jsonUtil.setResultSuccess().setJsonObject("nameResult", nameResult).setJsonObject("typeResult", typeResult).toJsonString();
       
    }
    
    
    @RequestMapping(value = "/enterprise/getMeeting", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getMeeting(HttpSession httpSession,
	    @RequestParam(value = "pageSize", defaultValue = "-1", required = false) int pageSize,
	    @RequestParam(value = "page", defaultValue = "-1", required = false) int page) {
	if(pageSize < 0 || page < 0){
	    return ParamError();
	}
	int enterpriseId = getEnterpriseId(httpSession);
	List<Meeting> meetings = null;
	if(pageSize == 0 && page == 0){
	     meetings = meetingManager.findBySql(Meeting.TABLE_NAME, Meeting.ENTERPRISE_ID, enterpriseId);
	     return jsonUtil.setResultSuccess().setModelList(meetings).toJsonString("/enterprise/getMeeting");
	}
	
	meetings = meetingManager.findBySql(Meeting.TABLE_NAME, Meeting.ENTERPRISE_ID, enterpriseId, new PageBean(page, 0, pageSize));
	return jsonUtil.setResultSuccess().setModelList(meetings).toJsonString("/enterprise/getMeeting");
    }
    
    @RequestMapping(value = "/enterprise/getMeeting/detail", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getMeetingDetial(HttpSession httpSession,
	    @RequestParam(value = "id", defaultValue = "0", required = false) int id) {
	int enterpriseId = getEnterpriseId(httpSession);
	Meeting meeting = meetingManager.getMeeting(id, enterpriseId);
	if(meeting == null){
	    return jsonUtil.setResultFail("此会议不存在").toJsonString();
	}
	return jsonUtil.setResultSuccess().setJsonObject("Meeting", meeting).toJsonString("/enterprise/getMeeting/detail");
    }
    
    @RequestMapping(value = "/enterprise/deleteMeeting", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_deleteMeeting(HttpSession httpSession,
	    @RequestParam(value = "id", defaultValue = "-1", required = false) int id) {
	if(id <= 0){
	    return ParamError();
	}
	Integer enterpriseId = getEnterpriseId(httpSession);
	if (meetingManager.deleteMeeting(id, enterpriseId)) {
	    return jsonUtil.setResultSuccess().toJsonString();
	} else {
	    return jsonUtil.setResultFail("删除失败").toJsonString();
	}
    }
    

    @RequestMapping(value = "/enterprise/updateMeeting", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_updateMeeting(HttpSession httpSession,
	    @Validated @ModelAttribute MeetingVo meeting, BindingResult result,
	    int meetingId) throws DaoException {
        if (result.hasErrors())
            return toErrorJson(result);
        int enterpriseId = getEnterpriseId(httpSession);
        Integer subjectId = meeting.getSubject_id();
        Subject subject = subjectManager.find(subjectId);
        Result res = meetingManager.updateMeeting(meetingId , enterpriseId, meeting, subject);
        if (res.isSuccess())
            return jsonUtil.setResultSuccess().toJsonString();
        return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
    }
    
    @RequestMapping(value = "/enterprise/searchMeeting", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_searchMeeting(HttpSession httpSession,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        if(!StringUtil.hasText(keyword)){
            return ParamError();
        }
        Integer enterpriseId = getEnterpriseId(httpSession);
        List<Meeting> nameResult = meetingManager.search(Meeting.TABLE_NAME, Meeting.NAME, keyword, Meeting.ENTERPRISE_ID, enterpriseId);
        List<Meeting> typeResult = new LinkedList<Meeting>();
        List<Subject> subjects = subjectManager.search(Subject.NAME, keyword);
        for (Subject subject : subjects) {
	    typeResult.addAll(meetingManager.findBySubject(subject.getId(), enterpriseId));
	}
        if(nameResult.size() < 1 && typeResult.size() < 1){
            return jsonUtil.setResultFail("无相关结果").toJsonString();
        }
        return jsonUtil.setResultSuccess().setJsonObject("nameResult", nameResult).setJsonObject("typeResult", typeResult).toJsonString("/enterprise/getMeeting");
       
    }
    
    @RequestMapping(value = "/enterprise/meeting/get/BySubjcet", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getMeetingBySubject(HttpSession httpSession,
            @RequestParam(value = "subjcetId", required = false, defaultValue = "0") int subjcetId) throws DaoException {
        Subject subject = subjectManager.find(subjcetId);
        List<Meeting> meetings = meetingManager.findBySubject(subjcetId, getEnterpriseId(httpSession));
        if(StringUtil.isEmpty(meetings))
            return jsonUtil.setResultFail("此科目下未找到相关会议").toJsonString();
        return jsonUtil.setResultSuccess().setModelList(meetings).toJsonString("/enterprise/getMeeting");
       
    }
    
    
    @RequestMapping(value = "/enterprise/video/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getVideo(HttpSession httpSession,
	    @RequestParam(value = "pageSize", defaultValue = "-1", required = false) int pageSize,
	    @RequestParam(value = "page", defaultValue = "-1", required = false) int page) {
	if(pageSize < 0 || page < 0){
	    return ParamError();
	}
	int enterpriseId = getEnterpriseId(httpSession);
	List<Video> videos = null;
	if(pageSize == 0 && page == 0){
	     videos = videoManager.findBySql(Video.TABLE_NAME, Video.ENTERPRISE_ID, enterpriseId);
	     return jsonUtil.setResultSuccess().setModelList(videos).toJsonString("/enterprise/video/get");
	}
	
	videos = videoManager.findBySql(Video.TABLE_NAME, Video.ENTERPRISE_ID, enterpriseId, new PageBean(page, 0, pageSize));
	return jsonUtil.setResultSuccess().setModelList(videos).toJsonString("/enterprise/video/get");
    }
    
    @RequestMapping(value = "/enterprise/video/get/detail", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getVideoDetial(HttpSession httpSession,
	    @RequestParam(value = "id", defaultValue = "0", required = false) int id) {
	int enterpriseId = getEnterpriseId(httpSession);
	Video video = videoManager.getVideo(id, enterpriseId);
	if(video == null){
	    return jsonUtil.setResultFail("此视频不存在").toJsonString();
	}
	return jsonUtil.setResultSuccess().setJsonObject(Video.CLASS_NAME, video).toJsonString("/enterprise/video/get/detail");
    }
    
    @RequestMapping(value = "/enterprise/video/delete", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_deleteVideo(HttpSession httpSession,
	    @RequestParam(value = "id", defaultValue = "-1", required = false) int id) {
	if(id <= 0){
	    return ParamError();
	}
	Integer enterpriseId = getEnterpriseId(httpSession);
	Result result = videoManager.deleteVideo(id, enterpriseId);
	if(result.isSuccess()){
	    return jsonUtil.setResultSuccess().toJsonString();
	}
	return jsonUtil.setResultFail(result.getErrorCode()).toJsonString();
    }
    

    @RequestMapping(value = "/enterprise/video/update", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_updateVideo(HttpSession httpSession,
	    @Validated @ModelAttribute VideoVO video, BindingResult result,
	    int videoId) throws DaoException {
        if (result.hasErrors())
            return toErrorJson(result);
        int enterpriseId = getEnterpriseId(httpSession);
        Integer subjectId = video.getSubject_id();
        Subject subject = subjectManager.find(subjectId);
        Result res = videoManager.updateVideo(videoId , enterpriseId, video, subject);
        if (res.isSuccess())
            return jsonUtil.setResultSuccess().toJsonString();
        return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
    }
    
    
    
    @RequestMapping(value = "/enterprise/video/search", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_searchVideo(HttpSession httpSession,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        if(!StringUtil.hasText(keyword)){
            return ParamError();
        }
        Integer enterpriseId = getEnterpriseId(httpSession);
        List<Video> nameResult = videoManager.search(Video.TABLE_NAME, Video.NAME, keyword, Video.ENTERPRISE_ID, enterpriseId);
        List<Video> typeResult = new LinkedList<Video>();
        List<Subject> subjects = subjectManager.search(Subject.NAME, keyword);
        for (Subject subject : subjects) {
	    typeResult.addAll(videoManager.findBySubject(subject.getId(), enterpriseId));
	}
        if(nameResult.size() < 1 && typeResult.size() < 1){
            return jsonUtil.setResultFail("无相关结果").toJsonString();
        }
        return jsonUtil.setResultSuccess().setJsonObject("nameResult", nameResult).setJsonObject("typeResult", typeResult).toJsonString("/enterprise/video/get");
        
       
    }
    
    
    @RequestMapping(value = "/enterprise/video/get/BySubjcet", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getVideoBySubject(HttpSession httpSession,
            @RequestParam(value = "subjcetId", required = false, defaultValue = "0") int subjcetId) throws DaoException {
        Subject subject = subjectManager.find(subjcetId);
        List<Video> videos = videoManager.findBySubject(subjcetId, getEnterpriseId(httpSession));
        if(StringUtil.isEmpty(videos)){
            return jsonUtil.setResultFail("无相关视频").toJsonString();
        }
        return jsonUtil.setResultSuccess().setModelList(videos).toJsonString("/enterprise/video/get");
       
    }
    
    @RequestMapping(value = "/enterprise/info/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getInfo(HttpSession httpSession) throws DaoException {
	int enterpriseId = getEnterpriseId(httpSession);
	Enterprise enterprise = enterpriseManager.find(enterpriseId);
	return jsonUtil.setJsonObject("enterprise", enterprise).toJsonString("/enterprise/info/get");
    }
    
    @RequestMapping(value = "/enterprise/upatePassword", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_upatePassword(HttpSession httpSession,
	    @RequestParam(value = "oldPassword", required = false, defaultValue="") String oldPassword,
	    @RequestParam(value = "newPassword", required = false, defaultValue="") String newPassword) {
	if(!StringUtil.hasText(oldPassword, newPassword)){
	    return ParamError();
	}
        String userAccount = getAccount(httpSession);
        boolean result = userManager.updatePassword(userAccount, oldPassword, newPassword);
        if(result){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/enterprise/ChinMedicine/update", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_updateChinMedicine(HttpSession httpSession,
	    @Validated @ModelAttribute EnterChineseVO entChineseVO, BindingResult result,
	    int id) throws DaoException {
        if (result.hasErrors())
            return toErrorJson(result);
        Enterprise enterprise = enterpriseManager.find(getEnterpriseId(httpSession));
        Result updateResult = enterChineseMedicineManager.updateMedicine(id, entChineseVO, enterprise);
        if(updateResult.isSuccess()){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail(updateResult.getErrorCode()).toJsonString();
    }
    
    
    @RequestMapping(value = "/enterprise/WestMedicine/update", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_updateWestMeicine(HttpSession httpSession,
	    @Validated @ModelAttribute EnterWestVO enterWestVO, BindingResult result,
	    int id) throws DaoException {
        if (result.hasErrors())
            return toErrorJson(result);
        Enterprise enterprise = enterpriseManager.find(getEnterpriseId(httpSession));
        Result updateResult = enterWestMedicineManager.updateMedicine(id, enterWestVO, enterprise);
        if(updateResult.isSuccess()){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail(updateResult.getErrorCode()).toJsonString();
    }
    
    @RequestMapping(value = "/enterprise/EnterMedicine/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getEnterpriseMedicine(
	    HttpSession httpSession,
	    @RequestParam(value = "id", required = false, defaultValue = "-1") int id,
	    @RequestParam(value = "type", required = false, defaultValue = "-1") int type) {
	    Object jsonObject = null;
	    int enterpriseId = getEnterpriseId(httpSession);
	    System.out.println(enterpriseId);
	    if(type == Medicine.ENTER_CHINESE){	
		EnterChineseMedicine medicine = enterChineseMedicineManager.getMedicine(id, enterpriseId);
		if(medicine == null){
		    jsonObject = null;
		}else {
		    jsonObject = new EnterChMedicineBean(medicine);
		}
	    }
	    else if (type == Medicine.ENTER_WEST){	
		EnterWestMedicine medicine = enterWestMedicineManager.getMedicine(id, enterpriseId);
		if (medicine == null) {
		    jsonObject = null;
		} else {
		    jsonObject = new EnterWestMedicineBean(medicine);
		}
	    }else {
		return ParamError();
	    }
	    if(jsonObject == null){
		return jsonUtil.setResultFail("该药品不存在").toJsonString();
	    }
	    return jsonUtil.setResultSuccess().setJsonObject("entity", jsonObject).toJsonString("/enterprise/EnterMedicine/get");
    }
 
 


}
