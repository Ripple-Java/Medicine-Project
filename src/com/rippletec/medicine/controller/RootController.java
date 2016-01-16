package com.rippletec.medicine.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import antlr.InputBuffer;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.BaseModel;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.utils.FileUtil;
import com.rippletec.medicine.utils.LoggerUtil;


/**
 * @author Liuyi
 *
 */
@Controller
@RequestMapping("/")
public class RootController extends BaseController {
    
    public static final String APP_DOWN_PATH = "/download/app";
    public static final String IOS_DOWN_PATH = "/download/ios";
    public static final String APP_FILE_NAME = "APPupdate_";
    public static final String IOS_FILE_NAME = "IOSupdate_";
    public static final String OUTPUT_NAME = "MedicineHub.apk";
    
    @RequestMapping(value = "/pptshow/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String root_getEnterTypes(@PathVariable int id) throws DaoException {
	Meeting meeting = meetingManager.find(id);
	return FileUtil.pptHtml.replace("%value%", id+"");
    }
    
    @RequestMapping(value = "/share/medicine/{typeId}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String root_share(@PathVariable int id, @PathVariable int typeId) {
	if(typeId != Medicine.CHINESE && typeId != Medicine.WEST && typeId != Medicine.ENTER_CHINESE && typeId != Medicine.ENTER_WEST)
	    return ParamError();
	if(id < 1)
	    return ParamError();
	return FileUtil.shareHtml.replace("%typeId%", typeId+"").replace("%id%", id+"");
    }
    
    @RequestMapping(value = "/share/medicine/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String root_getShareMedicine( @RequestParam(value="type", defaultValue="0", required=false) int type,
	    @RequestParam(value="id", defaultValue="0", required=false) int id) throws DaoException {
	if(id < 1)
	    return ParamError();
	Object medicine = null;
	if(type == Medicine.WEST){
	    medicine = westMedicineManager.find(id);
	}else if (type == Medicine.CHINESE) {
	    medicine = chineseMedicineManager.find(id);
	}else if (type == Medicine.ENTER_CHINESE) {
	    medicine = enterChineseMedicineManager.find(id);
	}else if (type == Medicine.ENTER_WEST) {
	    medicine = enterWestMedicineManager.find(id);
	}else {
	    return ParamError();
	}

	if(medicine == null){
	    return jsonUtil.setResultFail("该药品不存在").toJsonString();
	}
	return  jsonUtil.setResultSuccess().setJsonObject("medicine", medicine).toJsonString("/share/medicine/get").replaceAll("\\\\n", "<br>");
    }
    
    
    
    
    @RequestMapping(value = "/updateApp", method = RequestMethod.GET)
    public void root_updateApp(HttpServletResponse response,
	    @RequestParam(value="type", defaultValue="0", required=false) int type,
	    @RequestParam(value="updateVersion", defaultValue="0", required=false) int updateVersion) {
	if(type <=0 )
	    return;
	
	String filePath = "";
	String fileName = "";
	if(type == 1){
	    filePath =  FileUtil.getRootPath()+APP_DOWN_PATH;
	    fileName =  APP_FILE_NAME+updateVersion+".apk";
	}
	else if (type == 2) {
	    filePath = FileUtil.getRootPath()+IOS_DOWN_PATH;
	    fileName = IOS_FILE_NAME+updateVersion+".app";
	}
	else {
	    return;
	}
	
	File file = new File(filePath, fileName);
	if(!file.exists()){
	    LoggerUtil.ControllerLogger.info("下载文件不存在");
	    return ; 
	}
	OutputStream toClient = null;
	FileInputStream inputFile = null;
	try {
	    inputFile = new FileInputStream(file);
		
            response.setContentType("multipart/form-data");
	    response.setHeader("Content-Disposition", "attachment;fileName="+new String(OUTPUT_NAME.getBytes(),"UTF-8"));
	    response.setHeader("Content-Length", file.length()+"");
	    response.setContentType("bin");
	    
	    toClient = new BufferedOutputStream(response.getOutputStream());
	    byte[] buf = new byte[1024];
	    int i = 0;
	    while((i = inputFile.read(buf)) > 0){
		toClient.write(buf,0,i);
	    }
	    toClient.flush();
	    
	} catch (IOException e) {
	    LoggerUtil.ControllerLogger.error("获取下载文件失败");
	    e.printStackTrace();
	    return;
	}
	finally{
	    if(toClient != null){
		try {
		    toClient.close();
		} catch (IOException e) {
		    LoggerUtil.ControllerLogger.error("关闭下载文件toClient流失败");
		    e.printStackTrace();
		}
	    }
	    if(inputFile != null){
		try {
		    inputFile.close();
		} catch (IOException e) {
		    LoggerUtil.ControllerLogger.error("关闭下载文件inputFile流失败");
		    e.printStackTrace();
		}
	    }
	  
	}
	return;
	
    }
    
    
}
