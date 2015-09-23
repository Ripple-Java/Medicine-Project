package com.rippletec.medicine.controller;

import javax.annotation.Resource;

import com.rippletec.medicine.model.UserFavorite;
import com.rippletec.medicine.service.ChineseMedicineManager;
import com.rippletec.medicine.service.DoctorManager;
import com.rippletec.medicine.service.EnterpriseManager;
import com.rippletec.medicine.service.EnterpriseMedicineTypeManager;
import com.rippletec.medicine.service.FeedBackMassManager;
import com.rippletec.medicine.service.MedicineDocumentManager;
import com.rippletec.medicine.service.MedicineManager;
import com.rippletec.medicine.service.MedicineTypeManager;
import com.rippletec.medicine.service.StudentManager;
import com.rippletec.medicine.service.UserFavoriteManager;
import com.rippletec.medicine.service.UserManager;
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
    
    
}
