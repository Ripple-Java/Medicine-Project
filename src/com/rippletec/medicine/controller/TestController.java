package com.rippletec.medicine.controller;
import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.service.EnterpriseManager;


@Controller
@RequestMapping("/test")
public class TestController extends BaseController{
	@Resource
	private EnterpriseManager enterpriseManager;
	
	@RequestMapping(value="/json", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Enterprise byProducesJson() {
	    
	    return null;
	}
	
	@RequestMapping("/simple")
	public @ResponseBody String simple() {
	    MedicineType medicineType = new MedicineType("test1", MedicineType.DEFAULT_PARENT_ID, MedicineType.CHINESE);
		Medicine medicine = new Medicine(Medicine.CHINESE);
		ChineseMedicine chineseMedicine = new ChineseMedicine(medicine, medicineType,"name", "content", "efficacy", "annouce", "preparations", "manual", "store", "category", 100.0, ChineseMedicine.ON_PUBLISTH, "key");
		chineseMedicineManager.save(chineseMedicine);
		return "hello world";
	}

}
