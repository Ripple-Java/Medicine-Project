package com.rippletec.medicine.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.model.BaseModel;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.BackGroundMedicineVO;


@Controller
@RequestMapping("/Web")
public class WebController extends BaseController {
    

    /**
     * form表单提交 Date类型数据绑定 <功能详细描述>
     * 
     * @param binder
     * @see [类、类#方法、类#成员]
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	dateFormat.setLenient(false);
	binder.registerCustomEditor(Date.class, new CustomDateEditor(
		dateFormat, true));
    }
    
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public String user_setLogin(
	    HttpSession httpSession,
	    @RequestParam(value = "account", required = false, defaultValue = "") String account,
	    @RequestParam(value = "password", required = false, defaultValue = "") String password) {
	if (StringUtil.isUsername(account) && StringUtil.hasText(password)) {
	    if (userManager.isLogined(httpSession))
		return jsonUtil.setResultFail().setTip("账号已登录，请勿重复登陆").toJsonString();
	    if (userManager.adminLogin(account, password, httpSession))
		jsonUtil.setResultSuccess();
	    else
		jsonUtil.setResultFail().setTip("用户名或密码错误");
	} else
	    jsonUtil.setResultFail().setTip("参数错误");
	return jsonUtil.toJsonString();
    }
    
    @RequestMapping(value = "/admin/loginOut", method = RequestMethod.GET)
    @ResponseBody
    public String admin_setLoginOut(
	    HttpSession httpSession) {
	    if (userManager.isLogined(httpSession)){
		if(userManager.loginOut((String) httpSession.getAttribute(User.ACCOUNT),httpSession))
		    return jsonUtil.setResultSuccess().toJsonString();
		else return jsonUtil.setResultFail().toJsonString();
	    }
	    else
		jsonUtil.setResultFail().setTip("用户未登录");
	return jsonUtil.toJsonString();
    }
    
    @RequestMapping(value="/admin/getMedicine", method=RequestMethod.GET)
    @ResponseBody
    public String admin_getMedicine(int pageSize, int pageNum, int type){
	if(pageSize <= 0 || pageNum <= 0)
	    return jsonUtil.setResultFail().setTip("页面参数错误").toJsonString();
	PageBean page = new PageBean(pageNum, 0, pageSize);
	if(type == 5){
	    List<Medicine> medicines = medicineManager.findByPage(page);
	    List<BackGroundMedicineVO<BaseModel>> models = new LinkedList<BackGroundMedicineVO<BaseModel>>(); 
	    medicineManager.getChineseOrWest(models , medicines);
	    return jsonUtil.setResultSuccess().setModelList(models).toJsonString("/admin/getMedicine");
	}
	if(medicineManager.getMedicine(page, type, jsonUtil))
	    return jsonUtil.setResultSuccess().toJsonString("/admin/getMedicine");
	else
	    return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value="/admin/getCategoryMedicine", method=RequestMethod.GET)
    @ResponseBody
    public String admin_getCategoryMedicine(int pageSize, int pageNum,int type,int category){
	if(pageSize <= 0 || pageNum <= 0)
	    return jsonUtil.setResultFail().setTip("页面参数错误").toJsonString();
	PageBean page = new PageBean(pageNum, 0, pageSize);
	if(medicineManager.getMedicineByCategory(page,type,category, jsonUtil))
	    return jsonUtil.setResultSuccess().toJsonString("/admin/getCategoryMedicine");
	else
	    return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value="/admin/deleteMedicine", method=RequestMethod.POST)
    @ResponseBody
    public String admin_deleteMedicine(int id, int type){
	if(medicineManager.deleteMedicine(id, type))
	    jsonUtil.setResultSuccess();
	else
	    jsonUtil.setResultFail();
	return jsonUtil.toJsonString();
    }
     
    
    @RequestMapping(value="/admin/addChinMedicine", method=RequestMethod.POST)
    @ResponseBody
    public String admin_addChinMedicine(ChineseMedicine chineseMedicine, int medicineType_id){
	if((int)chineseMedicineManager.save(chineseMedicine) > 0)
	    return jsonUtil.setResultSuccess().toJsonString();
	return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value="/admin/addWestMedicine", method=RequestMethod.POST)
    @ResponseBody
    public String admin_addWestMedicine(WestMedicine westMedicine, int medicineType_id){
	if((int)westMedicineManager.save(westMedicine) > 0)
	    return jsonUtil.setResultSuccess().toJsonString();
	return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value="/admin/updateWestMedicine", method=RequestMethod.POST)
    @ResponseBody
    public String admin_updateWestMedicine(WestMedicine westMedicine){
	westMedicineManager.update(westMedicine);
	return jsonUtil.setResultSuccess().toJsonString();
    }
    
    @RequestMapping(value="/admin/updateChinMedicine", method=RequestMethod.POST)
    @ResponseBody
    public String admin_updateWestMedicine(ChineseMedicine chineseMedicine){
	chineseMedicineManager.update(chineseMedicine);
	return jsonUtil.setResultSuccess().toJsonString(); 
    }
    
    @RequestMapping(value="/enteruser/setInfo", method=RequestMethod.POST)
    @ResponseBody
    public String enterprise_setInfo(HttpSession httpSession ,String name, int type, String number, String imgUrl){
	if(StringUtil.isEnterpriseName(name) && StringUtil.isEnterpriseType(type) && StringUtil.isNumber(number)){
	    CheckData checkData = new CheckData(0, CheckData.TYPE_ENTERPRISE, imgUrl, new Date(), CheckData.CHECKING);
	    Enterprise enterprise = new Enterprise(type, name, number);
	    httpSession.setAttribute(Enterprise.CLASS_NAME, enterprise);
	    httpSession.setAttribute(CheckData.CLASS_NAME, checkData);
	    httpSession.setAttribute("infoFlag", true);
	    return jsonUtil.setResultSuccess().toJsonString();
	}
	return jsonUtil.setResultFail().setTip("参数不正确").toJsonString();
    }
    
    @RequestMapping(value="/enteruser/setInfo", method=RequestMethod.POST)
    @ResponseBody
    public String enterprise_setInfo(HttpSession httpSession ,String email, String password){
	Object infoAttr = httpSession.getAttribute("infoFlag");
	if(infoAttr == null)
	   return jsonUtil.setResultFail().setTip("非法操作").toJsonString();
	if(StringUtil.isEmail(email)){
	    if(userManager.registerEnterprise(email ,password, httpSession))
		return jsonUtil.setResultSuccess().toJsonString();
	    return jsonUtil.setResultFail().toJsonString();
	}
	return jsonUtil.setResultFail().setTip("参数不正确").toJsonString();
    }

}
