package com.rippletec.medicine.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Url;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.ControllerException;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.ServiceException;
import com.rippletec.medicine.exception.UtilException;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterNews;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.FeedBackMass;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.Subject;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.model.extend.ChineseMedicineBean;
import com.rippletec.medicine.model.extend.EnterChMedicineBean;
import com.rippletec.medicine.model.extend.EnterWestMedicineBean;
import com.rippletec.medicine.model.extend.EnterpriseBean;
import com.rippletec.medicine.model.extend.FeedBackMassBean;
import com.rippletec.medicine.model.extend.WestMedicineBean;
import com.rippletec.medicine.utils.DateUtil;
import com.rippletec.medicine.utils.ErrorCode;
import com.rippletec.medicine.utils.FileUtil;
import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.app.ChineseMedicineVO;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.medicine.vo.web.ChMedicineVO;
import com.rippletec.medicine.vo.web.CheckDataVO;
import com.rippletec.medicine.vo.web.EnterChineseVO;
import com.rippletec.medicine.vo.web.EnterWestVO;
import com.rippletec.medicine.vo.web.EnterpriseInfoVO;
import com.rippletec.medicine.vo.web.WestMedicineVO;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping("/Web")
public class WebAdminController extends BaseController{
    

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

    

    @RequestMapping(value = "/adminuser/login", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_setLogin(
            HttpSession httpSession,String account, String password) throws DaoException, ServiceException {
        if (!StringUtil.isUsername(account) || !StringUtil.hasText(password)) {
            return jsonUtil.setFailRes(ErrorCode.PARAM_FORMAT_ERROR).toJson();
        } 
        if (userManager.isLogined(httpSession)){
    	    userManager.loginOut(account, httpSession);
        }
        userManager.adminLogin(account, password, httpSession);
        return jsonUtil.setSuccessRes().toJson();
    }

    @RequestMapping(value = "/admin/loginOut", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_setLoginOut(HttpSession httpSession) throws ControllerException, DaoException {
	String account = getAccount(httpSession);
	userManager.loginOut(account, httpSession);
        return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value =  "/admin/upatePassword", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_upatePassword(HttpSession httpSession,String oldpassword,String newPassword) throws ControllerException, DaoException, ServiceException {
	if(!StringUtil.hasText(oldpassword, newPassword)){
	    return ParamError();
	}
        String userAccount = getAccount(httpSession);
        userManager.updatePassword(userAccount, oldpassword, newPassword);
        return jsonUtil.setSuccessRes().toJson();
    }

    @RequestMapping(value =  "/admin/getMedicine", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getMedicine(int pageSize, int pageNum, int type) throws UtilException, DaoException {
        PageBean page = new PageBean(pageNum, 0, pageSize);
        if (type == 5) {
            if(pageNum == 0 && pageNum == 0)
        	page = null;
            List<Medicine> medicines = medicineManager.findByPage(page);
            List<BackGroundMedicineVO> models = new LinkedList<BackGroundMedicineVO>();
            medicineManager.getChineseOrWest(models, medicines);
            return jsonUtil.setSuccessRes().setModels(models).toJson("/admin/getMedicine");
        }
        if (medicineManager.getMedicine(page, type, jsonUtil))
            return jsonUtil.setSuccessRes().toJson("/admin/getMedicine");
        else
            return jsonUtil.setFailRes(ErrorCode.PARAM_ERROR).toJson();
    }

    @RequestMapping(value =  "/admin/getCategoryMedicine", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getCategoryMedicine(int pageSize, int pageNum, int type, int category) throws DaoException, UtilException {
        PageBean page = null;
        if(pageNum==0 && pageSize==0){
            page = null;
        }
        else{
            page = new PageBean(pageNum, 0, pageSize);
        }
        if (medicineManager.getMedicineByCategory(page, type, category, jsonUtil))
            return jsonUtil.setSuccessRes().toJson("/admin/getCategoryMedicine");
        else
            return jsonUtil.setFailRes(ErrorCode.PARAM_ERROR).toJson();
    }

    @RequestMapping(value = "/admin/deleteMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_deleteMedicine(int id, int type) throws DaoException {
	//（1-中药，2-西药，3-企业中药，4-企业西药）
	if(type < 1 || type > 4){
	    return jsonUtil.setFailRes(ErrorCode.PARAM_ERROR).toJson();
	}
        medicineManager.deleteMedicine(id, type);
        return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/admin/addChinMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_addChinMedicine(HttpSession httpSession,
	    @Validated @ModelAttribute ChMedicineVO chMedicineVO, BindingResult result, int medicineType_id) throws DaoException {
        if (result.hasErrors()){
            return ParamError();
        }
        MedicineType medicineType = medicineTypeManager.findType(medicineType_id, MedicineType.CHINESE);
        chineseMedicineManager.saveMedicine(chMedicineVO, medicineType);
        return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/admin/addWestMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_addWestMedicine(HttpSession httpSession,
	    @Validated @ModelAttribute WestMedicineVO westMedicineVO, BindingResult result, int medicineType_id) throws DaoException {
        if (result.hasErrors()){
            return jsonUtil.setFailRes(ErrorCode.PARAM_ERROR).toJson();
        }
        MedicineType medicineType = medicineTypeManager.findType(medicineType_id, MedicineType.WEST);
        westMedicineManager.saveMedicine(westMedicineVO, medicineType);
        return jsonUtil.setSuccessRes().toJson();
    }
    
 

    @RequestMapping(value = "/admin/updateWestMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_updateWestMedicine(HttpSession httpSession,
	    @Validated @ModelAttribute WestMedicineVO westMedicineVO, BindingResult result, int medicineType_id, int id) throws DaoException {
	if (result.hasErrors())
            return toErrorJson(result);
        MedicineType medicineType = medicineTypeManager.findType(medicineType_id, MedicineType.WEST);
        westMedicineManager.updateMedicine(id, westMedicineVO,medicineType);
        return jsonUtil.setSuccessRes().toJson();
    }


    @RequestMapping(value = "/admin/updateChinMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_updateWestMedicine(HttpSession httpSession,
	    @Validated @ModelAttribute ChMedicineVO chMedicineVO, BindingResult result, int medicineType_id, int id) throws DaoException {
	if (result.hasErrors()){
	    return ParamError();
	}
        MedicineType medicineType = medicineTypeManager.findType(medicineType_id, MedicineType.CHINESE);
        chineseMedicineManager.updateMedicine(id, chMedicineVO,medicineType);
        return jsonUtil.setSuccessRes().toJson();
    }
    

    @RequestMapping(value = "/admin/updateChinMedicine", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getCount(int type) throws NumberFormatException, DaoException {;
        int count = 0;
        switch (type) {
            case 1:
                count = userManager.getCount(User.TABLE_NAME, User.STATUS, User.STATUS_CHECKING);
                break;
            case 2:
                count = checkDataManager.getCount(CheckData.TABLE_NAME);
                break;
            case 3:
                count = meetingManager.getCount(Meeting.TABLE_NAME);
                break;
            case 4:
                count = enterpriseManager.getCount(Enterprise.TABLE_NAME,null,null, Enterprise.STATUS, new Object[]{Enterprise.ON_PUBLISTH, Enterprise.ON_CLOSE});
                break;
            case 5:
        	count = videoManager.getCount(Video.TABLE_NAME);
        	break;
            case 6:
        	count = feedBackMassManager.getCount(FeedBackMass.TABLE_NAME);
        	break;
            default:
        	return ParamError();
        }
        return jsonUtil.setSuccessRes().setObject("count", count).toJson();
    }
    
    @RequestMapping(value = "/admin/getCheckData", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getCheckData(int pageSize, int pageNum) throws DaoException, ServiceException {
        PageBean pBean = new PageBean(pageNum, 0, pageSize);
        List<Object> values = new LinkedList<Object>();
        values.add(CheckData.TYPE_MEDICINE_CHINESE);
        values.add(CheckData.TYPE_MEDICINE_WEST);
        values.add(CheckData.TYPE_MEETING);
        values.add(CheckData.TYPE_VIDEO);
        List<CheckData> checkDatas = checkDataManager.findResCheckData(CheckData.TYPE, values, pBean);
        List<CheckDataVO> checkDataVOs = new LinkedList<CheckDataVO>();
        for (CheckData checkData : checkDatas) {
            CheckDataVO checkDataVO = new CheckDataVO(checkData);
            checkDataVOs.add(checkDataVO);
        }
        return jsonUtil.setObject("checkDatas", checkDataVOs).setSuccessRes().toJson();

    }


    @RequestMapping(value = "/admin/passCheckData", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_passCheckData(@Validated @ModelAttribute CheckDataVO checkDataVO, BindingResult result) throws DaoException {
        if (result.hasErrors())
            return toErrorJson(result);
        CheckData checkData = null;
        if(checkDataVO.type == 1){
            checkData = new CheckData();
            checkData.setObject_id(checkDataVO.getId());
        }else {
            checkData = checkDataManager.find(checkDataVO.getId());	    
        } 
        switch (checkDataVO.type) {
            case CheckData.TYPE_ENTERPRISE:
        	enterpriseManager.active(checkData.getObject_id());
        	break;
            case CheckData.TYPE_MEDICINE_CHINESE:
        	enterChineseMedicineManager.active(checkData.getObject_id());
                break;
            case CheckData.TYPE_MEDICINE_WEST:
        	enterWestMedicineManager.active(checkData.getObject_id());
                break;
            case CheckData.TYPE_MEETING:
        	meetingManager.active(checkData.getObject_id());
                break;
            case CheckData.TYPE_VIDEO:
        	videoManager.active(checkData.getObject_id());
                break;
            default:
                return ParamError();
        }
        checkDataManager.delete(checkDataVO.getId());
        return jsonUtil.setSuccessRes().toJson();
    }


//    @RequestMapping(value = "/admin/uploadExcel/chinese", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public String admin_uploadExcel_chinese(HttpSession httpSession,
//                                            @RequestParam("excel") CommonsMultipartFile excel) {
//        String format = FileUtil.getSuffixByFilename(excel.getOriginalFilename());
//        if (!format.equals(".xls") && !format.equals(".xlsx"))
//            return jsonUtil.setFailRes().setTip("不允许上传此种格式excel").toJson();
//        String fileName = DateUtil.getSimpleDateTime(new Date()) + StringUtil.generateCode(6) + FileUtil.getSuffixByFilename(excel.getOriginalFilename());
//        Result res = FileUtil.saveFile(UploadController.SEVER_TEMP, excel, fileName);
//        if (!res.isSuccess()) {
//            return jsonUtil.setFailRes(res.getErrorCode()).toJson();
//        }
//        String savePath = FileUtil.getRootPath() + res.getMessage();
////        excelUtil.setExcelPath(savePath);
////        excelUtil.setChineseTypeToDatabase();
//        return jsonUtil.setSuccessRes().toJson();
//    }


//    @RequestMapping(value = "/admin/uploadExcel/west", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public String admin_uploadExcel_west(HttpSession httpSession,
//                                         @RequestParam("excel") CommonsMultipartFile excel) {
//        String format = FileUtil.getSuffixByFilename(excel.getName());
//        if (!format.equals(".xls") && !format.equals(".xlsx"))
//            return jsonUtil.setFailRes().setTip("不允许上传此种格式excel").toJson();
//        String fileName = DateUtil.getSimpleDateTime(new Date()) + StringUtil.generateCode(6) + FileUtil.getSuffixByFilename(excel.getOriginalFilename());
//        Result res = FileUtil.saveFile(UploadController.SEVER_TEMP, excel, fileName);
//        if (!res.isSuccess()) {
//            return jsonUtil.setFailRes(res.getErrorCode()).toJson();
//        }
//        String savePath = FileUtil.getRootPath() + res.getMessage();
////        excelUtil.setExcelPath(savePath);
////        excelUtil.setWestTypeToDatabase();
//        return jsonUtil.setSuccessRes().toJson();
//    }

    @RequestMapping(value = "/admin/searchMedicine", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_searchMedicine(String keyword){
        if (!StringUtil.hasText(keyword)) {
            return ParamError();
        }
        List<BackGroundMedicineVO> res = new LinkedList<BackGroundMedicineVO>();
        List<BackGroundMedicineVO> medicineTypeResult;
	try {
	    medicineTypeResult = medicineTypeManager.searchBackGroundVO(keyword);
	    res.addAll(medicineTypeResult);
	} catch (DaoException e) {}
        List<BackGroundMedicineVO> medicineNameResultWest;
	try {
	    medicineNameResultWest = westMedicineManager.searchBackGroudVO(keyword, WestMedicine.NAME);
	    res.addAll(medicineNameResultWest);
	} catch (DaoException e) {}
        List<BackGroundMedicineVO> medicineNameResultChinese;
	try {
	    medicineNameResultChinese = chineseMedicineManager.searchBackGroundVO(keyword, ChineseMedicine.NAME);
	    res.addAll(medicineNameResultChinese);
	} catch (DaoException e) {}
        List<BackGroundMedicineVO> medicineNameResultEnChinese;
	try {
	    medicineNameResultEnChinese = enterChineseMedicineManager.searchBackGroundVO(keyword, EnterChineseMedicine.NAME);
	    res.addAll(medicineNameResultEnChinese);
	} catch (DaoException e) {}
        List<BackGroundMedicineVO> medicineNameResultEnWest;
	try {
	    medicineNameResultEnWest = enterWestMedicineManager.searchBackGroundVO(keyword, EnterWestMedicine.NAME);
	    res.addAll(medicineNameResultEnWest);
	} catch (DaoException e) {}
        List<BackGroundMedicineVO> medicineEnterNameResultCh;
	try {
	    medicineEnterNameResultCh = enterChineseMedicineManager.searchBackGroundVO(keyword, EnterChineseMedicine.ENTERPRISE_NAME);
	    res.addAll(medicineEnterNameResultCh);
	} catch (DaoException e) {}
        List<BackGroundMedicineVO> medicineEnterNameResultWs;
	try {
	    medicineEnterNameResultWs = enterWestMedicineManager.searchBackGroundVO(keyword, EnterWestMedicine.ENTERPRISE_NAME);
	    res.addAll(medicineEnterNameResultWs);
	} catch (DaoException e) {}
	if(res.size() < 1){
	    return jsonUtil.setFailRes(ErrorCode.DB_NO_ENITY_ERROR).toJson();
	}
        return jsonUtil.setModels(res).setSuccessRes().toJson();
    }

    @RequestMapping(value = "/admin/deleteMeeting", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_deleteMeeting(int id) throws DaoException {
        meetingManager.delete(id);
        return jsonUtil.setSuccessRes().toJson();
    }

    @RequestMapping(value = "/admin/getMeetings", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getMeetings(int page,int size) throws UtilException, DaoException {
        List<Meeting> meetings = null;
        if(page == 0  && size == 0) {
            meetings = meetingManager.findAll();
            return  jsonUtil.setSuccessRes().setModels(meetings).toJson("/admin/getMeetingBySubject");
        }
        meetings = meetingManager.findByPage(new PageBean(page, 0, size));
        return jsonUtil.setSuccessRes().setModels(meetings).toJson("/admin/getMeetingBySubject");
    }

    @RequestMapping(value = "/admin/getMeetingBySubject", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getMeetingBySubject(String subject) throws DaoException, UtilException {
        if (!StringUtil.hasText(subject)){
            return ParamError();
        }
        List<Subject> subject_po = subjectManager.findByParam(Subject.NAME, subject);
        Subject resSubject = subject_po.get(0);
        List<Meeting> meeting = meetingManager.findBySql(Meeting.TABLE_NAME, Meeting.SUBJECT_ID, resSubject.getId());
        return jsonUtil.setSuccessRes().setModels(meeting).toJson("/admin/getMeetingBySubject");
    }

    @RequestMapping(value = "/admin/searchMeeting", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_searchMeeting(String keyword) throws UtilException{
        if (!StringUtil.hasText(keyword)) {
            return ParamError();
        }
        List<Meeting> resultName;
	try {
	    resultName = meetingManager.search(Meeting.NAME, keyword);
	} catch (DaoException e) {
	    resultName = null;
	}
       
        List<Meeting> resultSubject = new LinkedList<Meeting>();
        List<Subject> subjects;
	try {
	    subjects = subjectManager.search(Subject.NAME, keyword);
	    for (Subject subject : subjects) {
		try {
		    resultSubject.addAll(meetingManager.findBySql(Meeting.TABLE_NAME, Meeting.SUBJECT_ID, subject.getId()));
		} catch (Exception e) {}
	    }
	    if(resultSubject.size() < 1){
		resultSubject = null;
	    }
	} catch (DaoException e) {
	   resultSubject = null;
	}
        if(resultName == null && resultSubject == null){
            return jsonUtil.setFailRes(ErrorCode.DB_NO_ENITY_ERROR).toJson();
        }
  
        return jsonUtil.setSuccessRes().setModels(resultName).setModels(resultSubject).toJson("/admin/getMeetingBySubject");
    }


    @RequestMapping(value = "/admin/searchMeeting", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_deleteEnterprise(int id) throws DaoException {
        enterpriseManager.delete(id);
        return jsonUtil.setSuccessRes().toJson();
    }

    @RequestMapping(value = "/admin/searchEnterprise", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_searchEnterprise( String keyword) throws UtilException{
        if (!StringUtil.hasText(keyword)) {
            return ParamError();
        }
        List<Enterprise> resultNamePO;
        List<Enterprise> resultTypePO = new LinkedList<>();
        List<EnterpriseBean> resultName = new LinkedList<EnterpriseBean>();
        List<EnterpriseBean> resultType = new LinkedList<EnterpriseBean>();
	try {
	    resultNamePO = enterpriseManager.search(Enterprise.NAME, keyword);
	    for (Enterprise enterprise : resultNamePO) {
	  	resultName.add(new EnterpriseBean(enterprise));
	    }
	} catch (DaoException e) {
	    resultName = null;
	}
	try {
	    if ("内资".equals(keyword)) {
		resultTypePO = enterpriseManager.findByParam(Enterprise.TYPE,
			Enterprise.DOMESTIC);
	    } else if ("外资".equals(keyword)) {
		resultTypePO = enterpriseManager.findByParam(Enterprise.TYPE,
			Enterprise.FOREIGN);
	    } else if ("合资".equals(keyword)) {
		resultTypePO = enterpriseManager.findByParam(Enterprise.TYPE,
			Enterprise.JOINT);
	    }
	    for (Enterprise enterprise : resultTypePO) {
		resultType.add(new EnterpriseBean(enterprise));
	    }
	} catch (Exception e) {
	    resultType = null;
	}   
	if(resultName == null && resultType ==null){
	    return jsonUtil.setFailRes(ErrorCode.DB_NO_ENITY_ERROR).toJson();
	}
        return jsonUtil.setSuccessRes().setModels(resultName).setModels(resultType).toJson("/admin/getEnterprises");
          
    }
    @RequestMapping(value = "/admin/getEnterprises", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getEnterprises( int page, int size) throws UtilException, ServiceException {
        List<Enterprise> enterprisesPO = null;
        List<EnterpriseBean> enterprises = new LinkedList<EnterpriseBean>();
        if(page == 0  && size == 0) {
            enterprisesPO = enterpriseManager.getValiatedEnterprises(null);
        }else {
            enterprisesPO = enterpriseManager.getValiatedEnterprises(new PageBean(page, 0, size));
	}
        for (Enterprise enterprise : enterprisesPO) {
            enterprises.add(new EnterpriseBean(enterprise));
	}
        return jsonUtil.setSuccessRes().setModels(enterprises).toJson("/admin/getEnterprises");
    }

    @RequestMapping(value = "/admin/getUsers", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getUsers(int page,int size, int sort) throws ServiceException, UtilException, DaoException {
        List<User> users = new LinkedList<User>();
        List<Object> values = new ArrayList<Object>();
        values.add(User.TYPE_ENTER);
        values.add(User.TYPE_USER);
        String orderStr = User.REGEDITTIME;
        if(sort == 1){
            orderStr = User.REGEDITTIME;
        }else if (sort == 2) {
	    orderStr = User.LASTLOGIN;
	}else if (sort == 3) {
	    orderStr = User.ACCOUNT;
	}else {
	    orderStr = User.REGEDITTIME;
	}
        if(page == 0  && size == 0) {    
           users = userManager.getNormalUser(User.TYPE, values, null, orderStr);
           return jsonUtil.setSuccessRes().setModels(users).toJson("/admin/getUsers");
        }
        users = userManager.getNormalUser(User.TYPE, values,new PageBean(page, 0, size), orderStr);
        return jsonUtil.setSuccessRes().setModels(users).toJson("/admin/getUsers");
    }
    
    @RequestMapping(value = "/admin/getUsersByType", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getUsersByType(int page, int size,int type, int sort) throws UtilException, ServiceException, DaoException {
        if(type != User.TYPE_ENTER && type != User.TYPE_USER){
            return ParamError();
        }
        List<User> users = new LinkedList<User>();
        List<Object> values = new ArrayList<Object>();
        values.add(type);
        String orderStr = User.REGEDITTIME;
        if(sort == 1){
            orderStr = User.REGEDITTIME;
        }else if (sort == 2) {
	    orderStr = User.LASTLOGIN;
	}else if (sort == 3) {
	    orderStr = User.ACCOUNT;
	}else {
	    return ParamError();
	}
        if(page == 0  && size == 0) {    
           users = userManager.getNormalUser(User.TYPE, values, null, orderStr);
           return jsonUtil.setSuccessRes().setModels(users).toJson("/admin/getUsers");
        }
        users = userManager.getNormalUser(User.TYPE, values, new PageBean(page, 0, size), orderStr);
        return jsonUtil.setSuccessRes().setModels(users).toJson("/admin/getUsers");
    }

    @RequestMapping(value =  "/admin/searchUser", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_searchUser( String keyword) throws DaoException, UtilException {
        if (!StringUtil.hasText(keyword)) {
            return ParamError();
        }
        List<User> users = userManager.search(User.ACCOUNT, keyword);
        return jsonUtil.setSuccessRes().setModels(users).toJson("/admin/getUsers");
    }

    @RequestMapping(value = "/admin/deleteFeedBack", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_deleteFeedBack( int id) throws DaoException {
        feedBackMassManager.delete(id);
        return jsonUtil.setSuccessRes().toJson();
    }
    

    @RequestMapping(value = "/web/admin/flushJsonTypes", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_flushJsonTypes() throws UtilException, DaoException {
       medicineTypeManager.flushJsonString();
       return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/admin/getEnterCheck", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getEnterCheck(int pageSize, int pageNum) throws DaoException, UtilException {
        List<Enterprise> enterprise = null;
        if(pageNum == 0 && pageSize==0){
            enterprise = enterpriseManager.findByParam(Enterprise.STATUS, Enterprise.ON_CHECKING);
        }else {
            enterprise = enterpriseManager.findByPage(Enterprise.STATUS, Enterprise.ON_CHECKING, new PageBean(pageNum, 0, pageSize));
	}
        return jsonUtil.setModels(enterprise).setSuccessRes().toJson("/admin/getEnterCheck");

    }
    
    @RequestMapping(value = "/admin/meeting/block", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_blockMeeting(int id,int value) throws DaoException {
        if(1 == value){
            meetingManager.block(id);            
        }else if (0 == value) {
	    meetingManager.unblock(id);
	}else {
	    return ParamError();
	}
        return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/admin/video/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getVideo(HttpSession httpSession, int pageSize, int page) throws DaoException, UtilException {
	List<Video> videos = null;
	if(pageSize == 0 && page == 0){
	     videos = videoManager.findAll();
	}else {
	    videos = videoManager.findByPage(new PageBean(page, 0, pageSize));	    
	}
	return jsonUtil.setSuccessRes().setModels(videos).toJson("/admin/video/get");
    }
    
    @RequestMapping(value = "/admin/feedback/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getFeedback(HttpSession httpSession,int pageSize,int page) throws UtilException, DaoException {
	List<FeedBackMass> feedBackMassesPO = null;
	List<FeedBackMassBean> feedBackMasses = new LinkedList<FeedBackMassBean>();
	if(pageSize == 0 && page == 0){
	    feedBackMassesPO = feedBackMassManager.findByParam(FeedBackMass.STATUS, FeedBackMass.NO_READ);
	}else {
	    feedBackMassesPO = feedBackMassManager.findByPage(FeedBackMass.STATUS, FeedBackMass.NO_READ, new PageBean(page, 0, pageSize));	    
	}
	for (FeedBackMass feedBackMass : feedBackMassesPO) {
	    feedBackMasses.add(new FeedBackMassBean(feedBackMass));
	}
	return jsonUtil.setSuccessRes().setModels(feedBackMasses).toJson("/enterprise/feedback/get");
    }
    
    @RequestMapping(value = "/admin/video/search", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_searchVideo(HttpSession httpSession, String keyword) throws UtilException{
        if(!StringUtil.hasText(keyword)){
            return ParamError();
        }
        List<Video> nameResult;
	try {
	    nameResult = videoManager.search(Video.NAME, keyword);
	} catch (DaoException e) {
	    nameResult = null;
	}
        List<Video> typeResult = new LinkedList<Video>();
        List<Subject> subjects;
	try {
	    subjects = subjectManager.search(Subject.NAME, keyword);
	    for (Subject subject : subjects) {
		try {
		    typeResult.addAll(videoManager.findBySql(Video.TABLE_NAME, Video.SUBJECT_ID, subject.getId()));
		} catch (Exception e) {}
	    }
	    if(typeResult.size() < 1){
		typeResult = null;
	    }
	} catch (DaoException e) {
	    typeResult = null;
	}
	if(typeResult == null && nameResult == null){
	    return jsonUtil.setFailRes(ErrorCode.DB_NO_ENITY_ERROR).toJson();
	}
        return jsonUtil.setSuccessRes().setObject("nameResult", nameResult).setObject("typeResult", typeResult).toJson("/admin/video/get");
        
       
    }
    
    @RequestMapping(value = "/admin/video/block", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_blockVideo(int id, int value) throws DaoException {
        if(1 == value){
            videoManager.block(id);            
        }else if (0 == value) {
	    videoManager.unblock(id);
	}else {
	    return ParamError();
	}
        return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/admin/enterprise/block", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_blockEnterprise( int id, int value) throws DaoException {
        if(1 == value){
            enterpriseManager.block(id);            
        }else if (0 == value) {
	    enterpriseManager.unblock(id);
	}else {
	    return ParamError();
	}
        return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/admin/user/block", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_blockUser(int id,int value) throws DaoException {
        if(1 == value){
            userManager.block(id);            
        }else if (0 == value) {
	    userManager.unblock(id);
	}else {
	    return ParamError();
	}
	return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/admin/enterprise/info/update", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_updateEnterpriseInfo(HttpSession httpSession,
	    @Validated @ModelAttribute EnterpriseInfoVO enterpriseInfoVO, BindingResult result,int id) throws DaoException {
        if (result.hasErrors()){            
            return ParamError();
        }
	enterpriseManager.updateInfo(id, enterpriseInfoVO);
	return jsonUtil.setSuccessRes().toJson();
    }

    @RequestMapping(value = "/admin/enterprise/unpass", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_unPassEnterprise(int id) throws DaoException {
        Enterprise enterprise = enterpriseManager.find(id);
        if(enterprise.getStatus() == Enterprise.ON_CHECKING){
            enterpriseManager.delete(enterprise.getId());
            userManager.delete(enterprise.getUser().getId());
            return jsonUtil.setSuccessRes().toJson();
        }
        return jsonUtil.setFailRes(ErrorCode.BAD_ACCESS_ERROR).toJson();
    }
    
    @RequestMapping(value = "/admin/video/delete", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_deleteVideo(int id) throws DaoException {
        videoManager.delete(id);
        return jsonUtil.setSuccessRes().toJson();
    }
    
    @RequestMapping(value = "/admin/medicine/get/detail", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getMedicineDetail(HttpSession httpSession, int id, int type) throws DaoException, UtilException {
	    Object jsonObject = null;
	    if(type == Medicine.CHINESE){	
		ChineseMedicine medicine = chineseMedicineManager.find(id);
		    jsonObject = new ChineseMedicineBean(medicine);
	    }
	    else if (type == Medicine.WEST){	
		WestMedicine medicine = westMedicineManager.find(id);
		    jsonObject = new WestMedicineBean(medicine);
	    }else {
		return ParamError();
	    }
	    return jsonUtil.setSuccessRes().setObject("entity", jsonObject).toJson("/admin/medicine/get/detail");
    }
    
    @RequestMapping(value = "/admin/password/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_updatePassword(HttpSession httpSession,String oldPassword, String newPassword) throws ControllerException, DaoException, ServiceException {
	String account = getAccount(httpSession);
	userManager.updatePassword(account, oldPassword, newPassword);
	return jsonUtil.setSuccessRes().toJson();
    }
 

}
