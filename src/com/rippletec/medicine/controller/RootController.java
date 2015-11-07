package com.rippletec.medicine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.utils.FileUtil;


/**
 * @author Liuyi
 *
 */
@Controller
@RequestMapping("/")
public class RootController extends BaseController {
    
    @RequestMapping(value = "/pptshow/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String user_getEnterTypes(@PathVariable int id) {
	Meeting meeting = meetingManager.find(id);
	if (meeting == null)
	    return jsonUtil.setResultFail("此id不存在").toJsonString();
	return FileUtil.pptHtml.replace("%value%", id+"");
    }
    
}
