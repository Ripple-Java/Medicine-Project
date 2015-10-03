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
	
	@RequestMapping("/helloword")
	public @ResponseBody String helloword() {
	    return "hello world";
	}

}
