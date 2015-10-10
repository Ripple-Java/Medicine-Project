package com.rippletec.medicine.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.swing.text.View;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.rippletec.medicine.model.BackGroundMedicineType;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.UserFavorite;
import com.rippletec.medicine.service.BackGroundMedicineTypeManager;
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
import com.rippletec.medicine.service.StudentManager;
import com.rippletec.medicine.service.UserFavoriteManager;
import com.rippletec.medicine.service.UserManager;
import com.rippletec.medicine.service.VideoManager;
import com.rippletec.medicine.service.WestMedicineManager;
import com.rippletec.medicine.utils.JsonUtil;

public class BaseController {
    
	@Resource(name=UserFavoriteManager.NAME)
	protected UserFavoriteManager userFavoriteManager;
	
	@Resource(name=FeedBackMassManager.NAME)
	protected FeedBackMassManager feedBackMassManager;
	
    @Resource(name=JsonUtil.NAME)
    protected JsonUtil jsonUtil;
    
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
    
    //安卓app签名
    @Value("${android.code}")
    protected String androidCode;
    
    protected String toErrorJson(BindingResult result) {
	List<ObjectError> errors = result.getAllErrors();
	String errorStr = "";
	for (ObjectError objectError : errors) {
	    errorStr += objectError.getDefaultMessage() +", ";
	}
	errorStr = errorStr.substring(0,errorStr.length()-2);
	return jsonUtil.setResultFail(errorStr).toJsonString();
    }
    
    
    protected String getAccount(HttpSession httpSession) {
	Object accountAttr = httpSession.getAttribute(User.ACCOUNT);
	return accountAttr == null ? null : (String) accountAttr;
    }
       
    
    
}
