package com.rippletec.medicine.controller;
import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.service.EnterpriseManager;


@Controller
@RequestMapping("/test")
public class TestController {
	@Resource
	private EnterpriseManager enterpriseManager;
	
	@RequestMapping("/simple")
	public @ResponseBody String simple() {
		return "hello world";
	}
	
	@RequestMapping(value="/json", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Enterprise byProducesJson() {
	    return null;
	}

}
