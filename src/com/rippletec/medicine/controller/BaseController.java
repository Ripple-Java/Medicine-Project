package com.rippletec.medicine.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rippletec.medicine.exception.DaoException;
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
	    return jsonUtil.setResultFail(((DaoException) ex).getErrorCode()).toJsonString();
	}
	else if (ex instanceof UtilException) {
	    return jsonUtil.setResultFail(((UtilException) ex).getErrorCode()).toJsonString();
	}
	else {
	    return jsonUtil.setResultFail(ErrorCode.INTENAL_ERROR).toJsonString();
	}
    }  
    
    protected String toErrorJson(BindingResult result) {
	List<ObjectError> errors = result.getAllErrors();
	String errorStr = "";
	for (ObjectError objectError : errors) {
	    errorStr += objectError.getDefaultMessage() +", ";
	}
	errorStr = errorStr.substring(0,errorStr.length()-2);
	return jsonUtil.setResultFail(errorStr).toJsonString();
    }
    
    protected String ParamError() {
	return jsonUtil.setResultFail("参数错误").toJsonString();
    }
    
    
    protected String getAccount(HttpSession httpSession) {
	Object accountAttr = httpSession.getAttribute(User.ACCOUNT);
	return accountAttr == null ? null : (String) accountAttr;
    }
    
    protected int getEnterpriseId(HttpSession httpSession) {
	Object accountAttr = httpSession
		.getAttribute(EnterChineseMedicine.ENTERPRISE_ID);
	return accountAttr == null ? -1 : (int) accountAttr;
    }
    
}
