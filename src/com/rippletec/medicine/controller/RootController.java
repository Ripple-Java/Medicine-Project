package com.rippletec.medicine.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.UtilException;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.utils.ErrorCode;
import com.rippletec.medicine.utils.FileUtil;


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
    
//    @RequestMapping(value = "/pptshow/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public String root_getEnterTypes(@PathVariable int id) throws DaoException {
//	Meeting meeting = meetingManager.find(id);
//	return FileUtil.pptHtml.replace("%value%", id+"");
//    }
    @Resource(name = ErrorCode.CLASS_NAME)
    public ErrorCode errorCode;
    
    @RequestMapping(value = "/share/medicine/{typeId}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String root_share(@PathVariable int id, @PathVariable int typeId) {
	if(typeId != Medicine.CHINESE && typeId != Medicine.WEST && typeId != Medicine.ENTER_CHINESE && typeId != Medicine.ENTER_WEST){
	    return ParamError();	    
	}
	return FileUtil.shareHtml.replace("%typeId%", typeId+"").replace("%id%", id+"");
    }
    
    @RequestMapping(value = "/share/medicine/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String root_getShareMedicine(int type,int id) throws DaoException, UtilException {
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
	return  jsonUtil.setSuccessRes().setObject("medicine", medicine).toJson("/share/medicine/get").replaceAll("\\\\n", "<br>");
    }
    
    
    @RequestMapping(value = "/error", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String root_error(){
	return ParamError();
    }
    
    
    
    @RequestMapping(value = "/updateApp", method = RequestMethod.GET)
    public void root_updateApp(HttpServletResponse response, HttpServletRequest request, int type, int updateVersion) {
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
	    return ;
	}
	
	File file = new File(filePath, fileName);
	if(!file.exists()){
	    Logger.getLogger(RootController.class)
	    	  .warn("root_updateApp() errorCode:"+ErrorCode.FILE_NOT_EXISTED_ERROR+" from："+getIpAddress(request)+" fileName: "+fileName);
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
	    Logger.getLogger(RootController.class)
	    	  .warn("root_updateApp() errorCode:"+ErrorCode.FILE_REDA_ERROR+" from："+getIpAddress(request)+" fileName: "+fileName);
	    return;
	}
	finally{
	    if(toClient != null){
		try {
		    toClient.close();
		} catch (IOException e) {
		    Logger.getLogger(RootController.class)
		    	  .warn("root_updateApp() errorCode:"+ErrorCode.FILE_CLOSE_ERROR+" from："+getIpAddress(request)+" fileName: "+fileName);
		}
	    }
	    if(inputFile != null){
		try {
		    inputFile.close();
		} catch (IOException e) {
		    Logger.getLogger(RootController.class)
		    	  .warn("root_updateApp() errorCode:"+ErrorCode.FILE_CLOSE_ERROR+" from："+getIpAddress(request)+" fileName: "+fileName);
		}
	    }
	  
	}
	return;
	
    }
    
    
}
