package com.rippletec.medicine.controller;

import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rippletec.medicine.service.EnterpriseManager;
import com.rippletec.medicine.utils.FileUtil;
import com.rippletec.medicine.utils.PPTUtil;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {
    @Resource
    private EnterpriseManager enterpriseManager;

    @RequestMapping("/helloword")
    public @ResponseBody String helloword() {
	return "hello world";
    }

    @RequestMapping("/testRoot")
    public @ResponseBody String getRootPath() {
	System.out.println(FileUtil.pptHtml);
	return FileUtil.getRootPath();
    }

    @RequestMapping("/pptTOimg")
    public @ResponseBody String pptTOimg() {
	File file = new File(FileUtil.getRootPath() + "/enterprise/ppt/20151025184324823055.pptx");
	System.out.println(file.length());
	try {
	    PPTUtil.saveToImg("20151025184324823055", file, 2, "png");
	} catch (IOException e) {
	    e.printStackTrace();
	    return "fail";
	}
	return "ok";
    }

    @RequestMapping("/pptTOpdf")
    public @ResponseBody String pptTOpdf() {
	File file = new File(FileUtil.getRootPath() + "/第2章 逻辑代数基础.ppt");
	System.out.println(file.length());
	PPTUtil.saveToHtml("testPdf2", file);
	return "ok";
    }

}
