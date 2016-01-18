package com.rippletec.medicine.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rippletec.medicine.exception.ControllerException;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.ServiceException;
import com.rippletec.medicine.exception.UtilException;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.BackGroundMedicineTypeManager;
import com.rippletec.medicine.service.CheckDataManager;
import com.rippletec.medicine.service.ChineseMedicineManager;
import com.rippletec.medicine.service.DBLoger;
import com.rippletec.medicine.service.DoctorManager;
import com.rippletec.medicine.service.EnterChineseMedicineManager;
import com.rippletec.medicine.service.EnterWestMedicineManager;
import com.rippletec.medicine.service.EnterpriseManager;
import com.rippletec.medicine.service.EnterpriseMedicineTypeManager;
import com.rippletec.medicine.service.FeedBackMassManager;
import com.rippletec.medicine.service.MedicineDocumentManager;
import com.rippletec.medicine.service.MedicineManager;
import com.rippletec.medicine.service.MedicineTypeManager;
import com.rippletec.medicine.service.MeetingManager;
import com.rippletec.medicine.service.ProjectConfigManager;
import com.rippletec.medicine.service.StudentManager;
import com.rippletec.medicine.service.SubjectManager;
import com.rippletec.medicine.service.UserFavoriteManager;
import com.rippletec.medicine.service.UserManager;
import com.rippletec.medicine.service.VideoManager;
import com.rippletec.medicine.service.WestMedicineManager;
import com.rippletec.medicine.utils.ErrorCode;
import com.rippletec.medicine.utils.ExcelUtil;
import com.rippletec.medicine.utils.JsonUtil;



public class BaseController {
    
    @Resource(name = UserFavoriteManager.NAME)
    protected UserFavoriteManager userFavoriteManager;

    @Resource(name = FeedBackMassManager.NAME)
    protected FeedBackMassManager feedBackMassManager;

    @Resource(name=JsonUtil.NAME)
    protected JsonUtil jsonUtil;
    
    
    @Resource(name=ExcelUtil.NAME)
    protected ExcelUtil excelUtil;
    
    @Resource(name=UserManager.NAME)
    protected UserManager userManager;
    
    @Resource(name=DoctorManager.NAME)
    protected DoctorManager doctorManager;
    
    @Resource(name=StudentManager.NAME)
    protected StudentManager studentManager;

    @Resource(name=ChineseMedicineManager.NAME)
    protected ChineseMedicineManager chineseMedicineManager;
    
    @Resource(name=EnterChineseMedicineManager.NAME)
    protected EnterChineseMedicineManager enterChineseMedicineManager;
    
    @Resource(name=EnterWestMedicineManager.NAME)
    protected EnterWestMedicineManager enterWestMedicineManager;
    
    @Resource(name=EnterpriseMedicineTypeManager.NAME)
    protected EnterpriseMedicineTypeManager enterpriseMedicineTypeManager;
    
    @Resource(name=EnterpriseManager.NAME)
    protected EnterpriseManager enterpriseManager;
    
    @Resource(name=MedicineDocumentManager.NAME)
    protected MedicineDocumentManager medicineDocumentManager;
    
    @Resource(name=MedicineManager.NAME)
    protected MedicineManager medicineManager;
    
    @Resource(name=MedicineTypeManager.NAME)
    protected MedicineTypeManager medicineTypeManager;
    
    @Resource(name=WestMedicineManager.NAME)
    protected WestMedicineManager westMedicineManager;
    
    @Resource(name=DBLoger.NAME)
    protected DBLoger dbLoger;
    
    @Resource(name=MeetingManager.NAME)
    protected MeetingManager meetingManager;
    
    @Resource(name=VideoManager.NAME)
    protected VideoManager videoManager;
    
    @Resource(name=BackGroundMedicineTypeManager.NAME)
    protected BackGroundMedicineTypeManager backGroundMedicineTypeManager;
    
    @Resource(name=CheckDataManager.NAME)
    protected CheckDataManager checkDataManager;
    
    @Resource(name = SubjectManager.NAME)
    protected SubjectManager subjectManager;
    
    @Resource(name = ProjectConfigManager.NAME)
    protected ProjectConfigManager projectConfigManager;
    
    //安卓app签名
    @Value("${android.code}")
    protected String androidCode;
    
    //测试号码
    @Value("${allow.number}")
    protected String allow_number;
    
    /** 基于@ExceptionHandler异常处理 */  
    @ExceptionHandler 
    @ResponseBody
    public String exp(HttpServletRequest request, Exception ex) {  
	if (ex instanceof DaoException) {
	    return jsonUtil.setFailRes(((DaoException) ex).getErrorCode()).toJson();
	}
	else if (ex instanceof UtilException) {
	    return jsonUtil.setFailRes(((UtilException) ex).getErrorCode()).toJson();
	}
	else if (ex instanceof ServiceException) {
	    return jsonUtil.setFailRes(((ServiceException) ex).getErrorCode()).toJson();
	}
	else if (ex instanceof ControllerException) {
	    return jsonUtil.setFailRes(((ControllerException) ex).getErrorCode()).toJson();
	}
	else if(ex instanceof TypeMismatchException){
	    return jsonUtil.setFailRes(ErrorCode.PARAM_ERROR).toJson();
	}
	else if(ex instanceof IllegalStateException){
	    return jsonUtil.setFailRes(ErrorCode.PARAM_ERROR).toJson();
	}
	else if (ex instanceof ConstraintViolationException) {
	    return jsonUtil.setFailRes(ErrorCode.PARAM_ERROR).toJson();
	}
	else {
	    ex.printStackTrace();
	    return jsonUtil.setFailRes(ErrorCode.INTENAL_ERROR).toJson();
	}
    }  
    
    protected String toErrorJson(BindingResult result) {
	List<ObjectError> errors = result.getAllErrors();
	String errorStr = "";
	for (ObjectError objectError : errors) {
	    errorStr += objectError.getDefaultMessage() +", ";
	}
	errorStr = errorStr.substring(0,errorStr.length()-2);
	return jsonUtil.setFailRes(errorStr).toJson();
    }
    
    protected String ParamError() {
	return jsonUtil.setFailRes(ErrorCode.PARAM_ERROR).toJson();
    }
    
    
    protected String getAccount(HttpSession httpSession) throws ControllerException {
	Object accountAttr = httpSession.getAttribute(User.ACCOUNT);
	if(accountAttr == null){
	    throw new ControllerException(ErrorCode.USER_GET_ACCOUNT_ERROR, new Date().toLocaleString()+" : getAccount()");
	}
	return (String) accountAttr;
    }
    
    protected int getEnterpriseId(HttpSession httpSession) throws ControllerException {
	Object accountAttr = httpSession
		.getAttribute(EnterChineseMedicine.ENTERPRISE_ID);
	if(accountAttr == null){
	    throw new ControllerException(ErrorCode.ENTERPRISE_GET_ID_ERROR, new Date().toLocaleString()+" : getEnterpriseId()");
	}
	return (int) accountAttr;
    }
    
    public static String getIpAddress(HttpServletRequest request) {
	String ip = request.getHeader("x-forwarded-for");
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getHeader("Proxy-Client-IP");
	}
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getHeader("WL-Proxy-Client-IP");
	}
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getHeader("HTTP_CLIENT_IP");
	}
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	}
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    ip = request.getRemoteAddr();
	}
	return ip;
    }

}
