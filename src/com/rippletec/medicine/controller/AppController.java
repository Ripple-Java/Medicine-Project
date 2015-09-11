package com.rippletec.medicine.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rippletec.medicine.SMS.SMS;
import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.MedicineDocument;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.utils.StringUtil;

/**
 * @author Liuyi
 *
 */
@Controller
@RequestMapping("/App")
public class AppController extends BaseController {

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

    @RequestMapping(value = "/user/getAllSearch", method = RequestMethod.GET)
    @ResponseBody
    public String user_getAllSearch(
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
	if (keyword.length() > 0) {
	    jsonUtil.setModelList(
		    westMedicineManager.search(WestMedicine.NAME, keyword))
		    .setModelList(
			    chineseMedicineManager.search(ChineseMedicine.NAME,
				    keyword))
		    .setModelList(
			    medicineTypeManager.search(MedicineType.NAME,
				    keyword))
		    .setModelList(
			    enterpriseManager.search(Enterprise.NAME, keyword))
		    .setModelList(
			    medicineDocumentManager.search(
				    MedicineDocument.TITLE, keyword));
	    return jsonUtil.setResultSuccess().toJsonString(
		    "/user/getAllSearch");
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }

    @RequestMapping(value = "/user/getEnterprise", method = RequestMethod.GET)
    @ResponseBody
    public String user_getEnterprise(
	    @RequestParam(value = "type", required = false, defaultValue = "0") int type,
	    @RequestParam(value = "size", required = false, defaultValue = "0") int size,
	    @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
	if (type > 0 && size > 0 && currentPage > 0) {
	    List<Enterprise> enterprises = enterpriseManager.getEnterprise(
		    size, type, currentPage);
	    return jsonUtil.setModelList(enterprises).toJsonString(
		    "/user/getEnterprise");
	}
	return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/user/getEnterMedicine", method = RequestMethod.GET)
    @ResponseBody
    public String user_getEnterpriseMedicine(
	    @RequestParam(value = "typeId", required = false, defaultValue = "-1") int typeId,
	    @RequestParam(value = "size", required = false, defaultValue = "0") int size,
	    @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
	if (typeId >= 0) {
	    Map<String, Object> res = enterpriseMedicineTypeManager
		    .getMedicinesByTypeId(typeId, size, currentPage);
	    if (res == null)
		return "{}";
	    return jsonUtil.setModelList((List) res.get("medicines"))
		    .setJsonObject("type", res.get("type"))
		    .toJsonString("/user/getEnterMedicine");
	}
	return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/user/getEnterMedicineType", method = RequestMethod.GET)
    @ResponseBody
    public String user_getEnterpriseMedicineType(
	    @RequestParam(value = "id", required = false, defaultValue = "-1") int id) {
	if (id >= 0) {
	    List<EnterpriseMedicineType> enterpriseMedicineTypes = enterpriseMedicineTypeManager
		    .getTypesByEnterpriseId(id);
	    return jsonUtil.setModelList(enterpriseMedicineTypes).toJsonString(
		    "/user/getEnterMedicineType");
	}
	return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/user/getMedicine", method = RequestMethod.GET)
    @ResponseBody
    public String user_getMedicine(
	    @RequestParam(value = "typeId", required = false, defaultValue = "0") int typeId,
	    @RequestParam(value = "size", required = false, defaultValue = "0") int size,
	    @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
	if (typeId >= 0) {
	    Map<String, Object> res = medicineTypeManager.getMedicineByTypeId(
		    typeId, new PageBean(currentPage, 0, size));
	    if (res == null)
		return "{}";
	    return jsonUtil.setModelList((List) res.get("medicines"))
		    .setJsonObject("type", res.get("type"))
		    .toJsonString("/user/getMedicine");
	}
	return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/user/getMedicineDom", method = RequestMethod.GET)
    @ResponseBody
    public String user_getMedicineDom(
	    @RequestParam(value = "medicineId", required = false, defaultValue = "0") int medicineId,
	    @RequestParam(value = "type", required = false, defaultValue = "0") int type) {
	if (type > 0 && medicineId > 0) {
	    List<MedicineDocument> medicineDocuments = medicineDocumentManager
		    .getDocument(medicineId, type);
	    return jsonUtil.setModelList(medicineDocuments).toJsonString(
		    "/user/getMedicineDom");
	}
	return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/user/getMedicineType", method = RequestMethod.GET)
    @ResponseBody
    public String user_getMedicineType(
	    @RequestParam(value = "parentId", required = false, defaultValue = "-1") int parentId) {
	if (parentId >= -1) {
	    List<MedicineType> medicineTypes = medicineTypeManager
		    .getTypeByParentId(parentId);
	    return jsonUtil.setModelList(medicineTypes).toJsonString(
		    "/user/getMedicineType");
	}
	return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/user/getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public String user_getUserInfo(
	    HttpSession httpSession,
	    @RequestParam(value = "account", required = false, defaultValue = "") String account) {
	if (StringUtil.hasText(account)) {
	    if (userManager.isLogined(account, httpSession))
		return jsonUtil.setJsonObject("User",
			userManager.findByAccount(account)).toJsonString(
			"/user/getUserInfo");
	    return jsonUtil.setResultFail().setTip("用户未登录").toJsonString();
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }

    @RequestMapping(value = "/user/getVerificationCode", method = RequestMethod.GET)
    @ResponseBody
    public String user_getVerificationCode(
	    HttpSession httpSession,
	    @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
	    @RequestParam(value = "type", required = false, defaultValue = "0") int type) {
	if (StringUtil.isMobile(phoneNumber)) {
	    if (type > 0 || userManager.isExist(phoneNumber))
		return jsonUtil.setResultFail().setJsonObject("tip", "此账号以存在")
			.toJsonString();
	    String VerificationCode = StringUtil.generateCode(6);
	    String timeLimit = "1";
	    SMS sms = new SMS();
	    String res = "";
	    switch (type) {
	    case 0:
		res = sms.send(phoneNumber, VerificationCode, timeLimit);
		break;
	    // 待定，可用多种模板
	    case 1:
		res = sms.send(phoneNumber, VerificationCode, timeLimit);
		break;
	    default:
		res = sms.send(phoneNumber, VerificationCode, timeLimit);
		break;
	    }
	    if (res.equals("success")) {
		httpSession.setAttribute("phoneNumber", phoneNumber);
		httpSession.setAttribute("code", VerificationCode);
		httpSession
			.setMaxInactiveInterval((new Integer(timeLimit)) * 60);
		jsonUtil.setJsonObject("result", "success")
			.setJsonObject("time", timeLimit)
			.setJsonObject("sessionid", httpSession.getId());
	    } else
		jsonUtil.setResultFail().setJsonObject("tip", res);
	} else
	    jsonUtil.setResultFail().setJsonObject("tip", "手机号码格式错误");
	return jsonUtil.toJsonString();
    }

    @RequestMapping(value = "/user/setDocInfo", method = RequestMethod.GET)
    @ResponseBody
    public String user_setDocInfo(
	    HttpSession httpSession,
	    @RequestParam(value = "account", required = false, defaultValue = "") String account,
	    @RequestParam(value = "name", required = false, defaultValue = "") String name,
	    @RequestParam(value = "hospital", required = false, defaultValue = "") String hospital,
	    @RequestParam(value = "office", required = false, defaultValue = "") String office,
	    @RequestParam(value = "officePhone", required = false, defaultValue = "") String officePhone,
	    @RequestParam(value = "profession", required = false, defaultValue = "") String profession) {
	if (StringUtil.isMobile(account)
		&& StringUtil.hasText(new String[] { name, hospital, office,
			officePhone, profession })) {
	    if (userManager.isLogined(account, httpSession)) {
		if (doctorManager.setInfo(account, name, hospital, office,
			officePhone, profession))
		    jsonUtil.setResultSuccess();
		else
		    jsonUtil.setResultFail();
		return jsonUtil.toJsonString();
	    } else
		jsonUtil.setResultFail().setTip("用户未登录");
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    @ResponseBody
    public String user_setLogin(
	    HttpSession httpSession,
	    @RequestParam(value = "account", required = false, defaultValue = "") String account,
	    @RequestParam(value = "password", required = false, defaultValue = "") String password) {
	if (StringUtil.isMobile(account) && StringUtil.isPassword(password)) {
	    if (userManager.userLogin(account, password, httpSession))
		jsonUtil.setResultSuccess();
	    else
		jsonUtil.setResultFail().setTip("用户名或密码错误");
	} else
	    jsonUtil.setResultFail().setTip("参数错误");
	return jsonUtil.toJsonString();
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    @ResponseBody
    public String user_setRegister(
	    HttpSession httpSession,
	    @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
	    @RequestParam(value = "password", required = false, defaultValue = "") String password,
	    @RequestParam(value = "code", required = false, defaultValue = "") String code) {
	if (StringUtil.isMobile(phoneNumber) && StringUtil.isPassword(password)) {
	    String tempPhone = (String) httpSession.getAttribute("phoneNumber");
	    String tempCode = (String) httpSession.getAttribute("code");
	    if (phoneNumber.equals(tempPhone) && code.equals(tempCode)) {
		if (userManager.register(phoneNumber, password))
		    jsonUtil.setResultSuccess().setJsonObject("account",
			    phoneNumber);
		else
		    jsonUtil.setResultFail().setTip("注册失败");
	    } else
		jsonUtil.setResultFail().setJsonObject("tip", "手机号或验证码错误");
	} else
	    jsonUtil.setResultFail().setJsonObject("tip", "参数错误");
	return jsonUtil.toJsonString();
    }

    @RequestMapping(value = "/user/setStuInfo", method = RequestMethod.GET)
    @ResponseBody
    public String user_setStuInfo(
	    HttpSession httpSession,
	    @RequestParam(value = "account", required = false, defaultValue = "") String account,
	    @RequestParam(value = "name", required = false, defaultValue = "") String name,
	    @RequestParam(value = "school", required = false, defaultValue = "") String school,
	    @RequestParam(value = "degree", required = false, defaultValue = "-1") int degree,
	    @RequestParam(value = "major", required = false, defaultValue = "") String major) {
	if (StringUtil.isMobile(account)
		&& StringUtil.hasText(new String[] { name, school, major })
		&& degree > -1) {
	    if (userManager.isLogined(account, httpSession)) {
		if (studentManager.setStuInfo(account, name, school, degree,
			major))
		    jsonUtil.setResultSuccess();
		else
		    jsonUtil.setResultFail();
		return jsonUtil.toJsonString();
	    } else
		jsonUtil.setResultFail().setTip("用户未登录");
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }

    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.GET)
    @ResponseBody
    public String user_updatePassword(
	    HttpSession httpSession,
	    @RequestParam(value = "account", required = false, defaultValue = "") String account,
	    @RequestParam(value = "password", required = false, defaultValue = "") String password) {
	if (StringUtil.isMobile(account) && StringUtil.isPassword(password)) {
	    if (userManager.isLogined(account, httpSession)) {
		if (userManager.updatePassword(account, password))
		    jsonUtil.setResultSuccess();
		else
		    jsonUtil.setResultFail();
		return jsonUtil.toJsonString();
	    } else
		jsonUtil.setResultFail().setTip("用户未登录");
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }

    @RequestMapping(value = "/user/updateUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public String user_updateUserInfo(
	    HttpSession httpSession,
	    @RequestParam(value = "account", required = false, defaultValue = "") String account,
	    @RequestParam(value = "sex", required = false, defaultValue = "-1") int sex,
	    @RequestParam(value = "birthday", required = false, defaultValue = "") Date birthday,
	    @RequestParam(value = "degree", required = false, defaultValue = "-1") int degree,
	    @RequestParam(value = "email", required = false, defaultValue = "") String email) {
	if (StringUtil.hasText(email) && StringUtil.hasText(account)
		&& degree > -1 && sex > -1) {
	    if (userManager.isLogined(account, httpSession)) {
		userManager.updateUserInfo(account, sex, birthday, degree,
			email);
		return jsonUtil.setResultSuccess().toJsonString();
	    }
	    return jsonUtil.setResultFail().setTip("用户未登录").toJsonString();
	}
	return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }

}
