package com.rippletec.medicine.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterNews;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.FeedBackMass;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.Subject;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.model.extend.ChineseMedicineBean;
import com.rippletec.medicine.model.extend.EnterChMedicineBean;
import com.rippletec.medicine.model.extend.EnterWestMedicineBean;
import com.rippletec.medicine.model.extend.EnterpriseBean;
import com.rippletec.medicine.model.extend.FeedBackMassBean;
import com.rippletec.medicine.model.extend.WestMedicineBean;
import com.rippletec.medicine.utils.DateUtil;
import com.rippletec.medicine.utils.FileUtil;
import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.app.ChineseMedicineVO;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.medicine.vo.web.ChMedicineVO;
import com.rippletec.medicine.vo.web.CheckDataVO;
import com.rippletec.medicine.vo.web.EnterChineseVO;
import com.rippletec.medicine.vo.web.EnterWestVO;
import com.rippletec.medicine.vo.web.EnterpriseInfoVO;
import com.rippletec.medicine.vo.web.WestMedicineVO;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping("/Web")
public class WebAdminController extends BaseController{
    

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

    

    @RequestMapping(value = "/adminuser/login", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_setLogin(
            HttpSession httpSession,
            @RequestParam(value = "account", required = false, defaultValue = "") String account,
            @RequestParam(value = "password", required = false, defaultValue = "") String password) {
        if (StringUtil.isUsername(account) && StringUtil.hasText(password)) {
            if (userManager.isLogined(httpSession)){
        	userManager.loginOut(account, httpSession);
            }
            Result result = userManager.adminLogin(account, password, httpSession);
            if (result.isSuccess()){
        	jsonUtil.setResultSuccess();        	
            }
            else
                jsonUtil.setResultFail(result.getErrorCode());
        } else
            jsonUtil.setResultFail().setTip("参数错误");
        return jsonUtil.toJsonString();
    }

    @RequestMapping(value = "/admin/loginOut", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_setLoginOut(
            HttpSession httpSession) {
        if (userManager.loginOut((String) httpSession.getAttribute(User.ACCOUNT), httpSession))
            return jsonUtil.setResultSuccess().toJsonString();
        else
            return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/admin/upatePassword", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_upatePassword(HttpSession httpSession,
	    @RequestParam(value = "oldpassword", required = false, defaultValue="") String oldpassword,
	    @RequestParam(value = "newPassword", required = false, defaultValue="") String newPassword) {
	if(!StringUtil.hasText(oldpassword, newPassword)){
	    return ParamError();
	}
        String userAccount = getAccount(httpSession);
        boolean result = userManager.updatePassword(userAccount, oldpassword, newPassword);
        if(result){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/admin/getMedicine", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getMedicine(int pageSize, int pageNum, int type) {
        if (pageSize < 0 || pageNum < 0)
            return jsonUtil.setResultFail().setTip("页面参数错误").toJsonString();
        PageBean page = new PageBean(pageNum, 0, pageSize);
        if (type == 5) {
            if(pageNum == 0 && pageNum == 0)
        	page = null;
            List<Medicine> medicines = medicineManager.findByPage(page);
            List<BackGroundMedicineVO> models = new LinkedList<BackGroundMedicineVO>();
            medicineManager.getChineseOrWest(models, medicines);
            return jsonUtil.setResultSuccess().setModelList(models).toJsonString("/admin/getMedicine");
        }
        if (medicineManager.getMedicine(page, type, jsonUtil))
            return jsonUtil.setResultSuccess().toJsonString("/admin/getMedicine");
        else
            return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/admin/getCategoryMedicine", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getCategoryMedicine(int pageSize, int pageNum, int type, int category) {
        if (pageNum < 0 || pageSize < 0)
            return jsonUtil.setResultFail().setTip("页面参数错误").toJsonString();
        PageBean page = null;
        if(pageNum==0 && pageSize==0){
            page = null;
        }
        else{
            page = new PageBean(pageNum, 0, pageSize);
        }
        if (medicineManager.getMedicineByCategory(page, type, category, jsonUtil))
            return jsonUtil.setResultSuccess().toJsonString("/admin/getCategoryMedicine");
        else
            return jsonUtil.setResultFail().toJsonString();
    }

    @RequestMapping(value = "/admin/deleteMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_deleteMedicine(int id, int type) {
        if (medicineManager.deleteMedicine(id, type))
            jsonUtil.setResultSuccess();
        else
            jsonUtil.setResultFail();
        return jsonUtil.toJsonString();
    }
    
    @RequestMapping(value = "/admin/addChinMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_addChinMedicine(HttpSession httpSession,
	    @Validated @ModelAttribute ChMedicineVO chMedicineVO, BindingResult result, int medicineType_id) {
        if (result.hasErrors())
            return toErrorJson(result);
        MedicineType medicineType = medicineTypeManager.findType(medicineType_id, MedicineType.CHINESE);
        if(medicineType == null){
            return jsonUtil.setResultFail("此分类不存在").toJsonString();
        }
        Result res = chineseMedicineManager.saveMedicine(chMedicineVO, medicineType);
        if(res.isSuccess()){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
    }
    
    @RequestMapping(value = "/admin/addWestMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_addWestMedicine(HttpSession httpSession,
	    @Validated @ModelAttribute WestMedicineVO westMedicineVO, BindingResult result, int medicineType_id) {
        if (result.hasErrors())
            return toErrorJson(result);
        MedicineType medicineType = medicineTypeManager.findType(medicineType_id, MedicineType.WEST);
        if(medicineType == null){
            return jsonUtil.setResultFail("此分类不存在").toJsonString();
        }
        Result res = westMedicineManager.saveMedicine(westMedicineVO, medicineType);
        if(res.isSuccess()){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
    }
    
 

    @RequestMapping(value = "/admin/updateWestMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_updateWestMedicine(HttpSession httpSession,
	    @Validated @ModelAttribute WestMedicineVO westMedicineVO, BindingResult result, int medicineType_id, int id) throws DaoException {
	if (result.hasErrors())
            return toErrorJson(result);
        MedicineType medicineType = medicineTypeManager.findType(medicineType_id, MedicineType.WEST);
        Result res = westMedicineManager.updateMedicine(id, westMedicineVO,medicineType);
        if(res.isSuccess()){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
    }


    @RequestMapping(value = "/admin/updateChinMedicine", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_updateWestMedicine(HttpSession httpSession,
	    @Validated @ModelAttribute ChMedicineVO chMedicineVO, BindingResult result, int medicineType_id, int id) throws DaoException {
	if (result.hasErrors())
            return toErrorJson(result);
        MedicineType medicineType = medicineTypeManager.findType(medicineType_id, MedicineType.CHINESE);
        Result res = chineseMedicineManager.updateMedicine(id, chMedicineVO,medicineType);
        if(res.isSuccess()){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
    }
    

    @RequestMapping(value = "/admin/getCount", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getCount(
            @RequestParam(value = "type", required = false, defaultValue = "0") int type) {
        if (type <= 0)
            return jsonUtil.setResultFail("type参数必须大于0").toJsonString();
        int count = 0;

        switch (type) {
            case 1:
                count = userManager.getCount(User.TABLE_NAME, User.STATUS, User.STATUS_CHECKING);
                break;
            case 2:
                count = checkDataManager.getCount(CheckData.TABLE_NAME);
                break;
            case 3:
                count = meetingManager.getCount(Meeting.TABLE_NAME);
                break;
            case 4:
                count = enterpriseManager.getCount(Enterprise.TABLE_NAME,null,null, Enterprise.STATUS, new Object[]{Enterprise.ON_PUBLISTH, Enterprise.ON_CLOSE});
                break;
            case 5:
        	count = videoManager.getCount(Video.TABLE_NAME);
        	break;
            case 6:
        	count = feedBackMassManager.getCount(FeedBackMass.TABLE_NAME);
        	break;
            default:
        	return ParamError();
        }
        return jsonUtil.setResultSuccess().setJsonObject("count", count).toJsonString();
    }
    
    @RequestMapping(value = "/admin/getCheckData", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getCheckData(int pageSize, int pageNum) {
        if (pageSize < 0 || pageNum < 0)
            return jsonUtil.setResultFail("参数不能小于0").toJsonString();
        PageBean pBean = new PageBean(pageNum, 0, pageSize);
        List<Object> values = new LinkedList<Object>();
        values.add(CheckData.TYPE_MEDICINE_CHINESE);
        values.add(CheckData.TYPE_MEDICINE_WEST);
        values.add(CheckData.TYPE_MEETING);
        values.add(CheckData.TYPE_VIDEO);
        List<CheckData> checkDatas = checkDataManager.findResCheckData(CheckData.TYPE, values, pBean);
        List<CheckDataVO> checkDataVOs = new LinkedList<CheckDataVO>();
        for (CheckData checkData : checkDatas) {
            CheckDataVO checkDataVO = new CheckDataVO(checkData);
            checkDataVOs.add(checkDataVO);
        }
        return jsonUtil.setJsonObject("checkDatas", checkDataVOs).setResultSuccess().toJsonString();

    }


    @RequestMapping(value = "/admin/passCheckData", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_passCheckData(@Validated @ModelAttribute CheckDataVO checkDataVO, BindingResult result) throws DaoException {
        if (result.hasErrors())
            return toErrorJson(result);
        CheckData checkData = null;
        if(checkDataVO.type == 1){
            checkData = new CheckData();
            checkData.setObject_id(checkDataVO.getId());
        }else {
            checkData = checkDataManager.find(checkDataVO.getId());	    
        } 
        Result res = null;
        switch (checkDataVO.type) {
            case CheckData.TYPE_ENTERPRISE:
        	res = enterpriseManager.active(checkData.getObject_id());
        	break;
            case CheckData.TYPE_MEDICINE_CHINESE:
        	res = enterChineseMedicineManager.active(checkData.getObject_id());
                break;
            case CheckData.TYPE_MEDICINE_WEST:
        	res= enterWestMedicineManager.active(checkData.getObject_id());
                break;
            case CheckData.TYPE_MEETING:
        	res = meetingManager.active(checkData.getObject_id());
                break;
            case CheckData.TYPE_VIDEO:
        	res = videoManager.active(checkData.getObject_id());
                break;
            default:
                return jsonUtil.setResultFail("类型参数错误").toJsonString();
        }
        if(res.isSuccess()){
            checkDataManager.delete(checkDataVO.getId());
            return jsonUtil.setResultSuccess().toJsonString();            
        }
        return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
    }


    @RequestMapping(value = "/admin/uploadExcel/chinese", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_uploadExcel_chinese(HttpSession httpSession,
                                            @RequestParam("excel") CommonsMultipartFile excel) {
        String format = FileUtil.getSuffixByFilename(excel.getOriginalFilename());
        if (!format.equals(".xls") && !format.equals(".xlsx"))
            return jsonUtil.setResultFail().setTip("不允许上传此种格式excel").toJsonString();
        String fileName = DateUtil.getSimpleDateTime(new Date()) + StringUtil.generateCode(6) + FileUtil.getSuffixByFilename(excel.getOriginalFilename());
        Result res = FileUtil.saveFile(UploadController.SEVER_TEMP, excel, fileName);
        if (!res.isSuccess()) {
            return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
        }
        String savePath = FileUtil.getRootPath() + res.getMessage();
//        excelUtil.setExcelPath(savePath);
//        excelUtil.setChineseTypeToDatabase();
        return jsonUtil.setResultSuccess().toJsonString();
    }


    @RequestMapping(value = "/admin/uploadExcel/west", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_uploadExcel_west(HttpSession httpSession,
                                         @RequestParam("excel") CommonsMultipartFile excel) {
        String format = FileUtil.getSuffixByFilename(excel.getName());
        if (!format.equals(".xls") && !format.equals(".xlsx"))
            return jsonUtil.setResultFail().setTip("不允许上传此种格式excel").toJsonString();
        String fileName = DateUtil.getSimpleDateTime(new Date()) + StringUtil.generateCode(6) + FileUtil.getSuffixByFilename(excel.getOriginalFilename());
        Result res = FileUtil.saveFile(UploadController.SEVER_TEMP, excel, fileName);
        if (!res.isSuccess()) {
            return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
        }
        String savePath = FileUtil.getRootPath() + res.getMessage();
//        excelUtil.setExcelPath(savePath);
//        excelUtil.setWestTypeToDatabase();
        return jsonUtil.setResultSuccess().toJsonString();
    }

    @RequestMapping(value = "/admin/searchMedicine", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_searchMedicine(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        if (StringUtil.hasText(keyword)) {
            List<BackGroundMedicineVO> res = new LinkedList<BackGroundMedicineVO>();
            List<BackGroundMedicineVO> medicineTypeResult = medicineTypeManager.searchBackGroundVO(keyword);
            List<BackGroundMedicineVO> medicineNameResultWest =  westMedicineManager.searchBackGroudVO(keyword, WestMedicine.NAME);
            List<BackGroundMedicineVO> medicineNameResultChinese = chineseMedicineManager.searchBackGroundVO(keyword, ChineseMedicine.NAME);
            List<BackGroundMedicineVO> medicineNameResultEnChinese = enterChineseMedicineManager.searchBackGroundVO(keyword, EnterChineseMedicine.NAME);
            List<BackGroundMedicineVO> medicineNameResultEnWest = enterWestMedicineManager.searchBackGroundVO(keyword, EnterWestMedicine.NAME);
            List<BackGroundMedicineVO> medicineEnterNameResultCh = enterChineseMedicineManager.searchBackGroundVO(keyword, EnterChineseMedicine.ENTERPRISE_NAME);
            List<BackGroundMedicineVO> medicineEnterNameResultWs = enterWestMedicineManager.searchBackGroundVO(keyword, EnterWestMedicine.ENTERPRISE_NAME);
            res.addAll(medicineEnterNameResultCh);
            res.addAll(medicineEnterNameResultWs);
            res.addAll(medicineNameResultChinese);
            res.addAll(medicineNameResultEnChinese);
            res.addAll(medicineNameResultEnWest);
            res.addAll(medicineNameResultWest);
            res.addAll(medicineTypeResult);
            if(res == null || res.size() == 0)
                return jsonUtil.setResultFail("未搜索到相关结果").toJsonString();
            return  jsonUtil.setModelList(res).setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }

    @RequestMapping(value = "/admin/deleteMeeting", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_deleteMeeting(
            @RequestParam(value = "id", required = false, defaultValue = "") int id) throws DaoException {
        if (id < 1)
            return jsonUtil.setResultFail("参数不合法").toJsonString();
        Meeting meeting = meetingManager.find(id);
        meetingManager.delete(id);
        return jsonUtil.setResultSuccess().toJsonString();
    }

    @RequestMapping(value = "/admin/getMeetings", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getMeetings(
            @RequestParam(value = "page", required = false, defaultValue = "-1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "-1") int size) {
        List<Meeting> meetings = null;
        if(page == 0  && size == 0) {
            meetings = meetingManager.findAll();
            return  jsonUtil.setResultSuccess().setModelList(meetings).toJsonString("/admin/getMeetingBySubject");
        }
        if(!StringUtil.isPositive(page,size))
            return  jsonUtil.setResultFail("参数错误").toJsonString();
        meetings = meetingManager.findByPage(new PageBean(page, 0, size));
        return jsonUtil.setResultSuccess().setModelList(meetings).toJsonString("/admin/getMeetingBySubject");
    }

    @RequestMapping(value = "/admin/getMeetingBySubject", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getMeetingBySubject(
            @RequestParam(value = "subject", required = false, defaultValue = "") String subject) {
        if (!StringUtil.hasText(subject))
            return jsonUtil.setResultFail("参数不合法").toJsonString();
        List<Subject> subject_po = subjectManager.findByParam(Subject.NAME, subject);
        if(subject_po == null || subject_po.size() < 1)
            return jsonUtil.setResultFail("此科目不存在").toJsonString();
        Subject resSubject = subject_po.get(0);
        List<Meeting> meeting = meetingManager.findBySql(Meeting.TABLE_NAME, Meeting.SUBJECT_ID, resSubject.getId());
        return jsonUtil.setResultSuccess().setModelList(meeting).toJsonString("/admin/getMeetingBySubject");
    }

    @RequestMapping(value = "/admin/searchMeeting", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_searchMeeting(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        if (StringUtil.hasText(keyword)) {
            List<Meeting> resultName = meetingManager.search(Meeting.NAME, keyword);
            List<Subject> subjects = subjectManager.search(Subject.NAME, keyword);
            System.out.println(subjects.size());
            List<Meeting> resultSubject = new LinkedList<Meeting>();
            for (Subject subject : subjects) {
		resultSubject.addAll(meetingManager.findBySql(Meeting.TABLE_NAME, Meeting.SUBJECT_ID, subject.getId()));
	    }
            return jsonUtil.setResultSuccess().setModelList(resultName).setModelList(resultSubject).toJsonString("/admin/getMeetingBySubject");
        }
        return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }


    @RequestMapping(value = "/admin/deleteEnterprise", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_deleteEnterprise(
            @RequestParam(value = "id", required = false, defaultValue = "") int id) throws DaoException {
        if (id < 1)
            return jsonUtil.setResultFail("参数不合法").toJsonString();
        Enterprise enterprise = enterpriseManager.find(id);
        enterpriseManager.delete(id);
        return jsonUtil.setResultSuccess().toJsonString();
    }

    @RequestMapping(value = "/admin/searchEnterprise", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_searchEnterprise(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        if (StringUtil.hasText(keyword)) {
            List<Enterprise> resultNamePO = enterpriseManager.search(Enterprise.NAME, keyword);
            List<Enterprise> resultTypePO = new LinkedList<>();
            List<EnterpriseBean> resultName = new LinkedList<EnterpriseBean>();
            List<EnterpriseBean> resultType = new LinkedList<EnterpriseBean>();
            if("内资".equals(keyword))
                resultTypePO = enterpriseManager.findByParam(Enterprise.TYPE, Enterprise.DOMESTIC);
            else if ("外资".equals(keyword)){
                resultTypePO = enterpriseManager.findByParam(Enterprise.TYPE,Enterprise.FOREIGN);
            }
            else if ("合资".equals(keyword)){
                resultTypePO = enterpriseManager.findByParam(Enterprise.TYPE, Enterprise.JOINT);
            }
            if(resultNamePO.size() == 0 && resultTypePO.size()==0){
        	return jsonUtil.setResultFail("未找到相关结果").toJsonString();
            }
            for (Enterprise enterprise : resultNamePO) {
		resultName.add(new EnterpriseBean(enterprise));
	    }
            for (Enterprise enterprise : resultTypePO) {
		resultType.add(new EnterpriseBean(enterprise));
	    }
            return jsonUtil.setResultSuccess().setModelList(resultName).setModelList(resultType).toJsonString("/admin/getEnterprises");
        }
        return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }
    @RequestMapping(value = "/admin/getEnterprises", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getEnterprises(
            @RequestParam(value = "page", required = false, defaultValue = "-1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "-1") int size) {
        List<Enterprise> enterprisesPO = null;
        List<EnterpriseBean> enterprises = new LinkedList<EnterpriseBean>();
        if(page == 0  && size == 0) {
            enterprisesPO = enterpriseManager.getValiatedEnterprises(null);
        }else {
            if(!StringUtil.isPositive(page,size))
                return  jsonUtil.setResultFail("参数错误").toJsonString();
            enterprisesPO = enterpriseManager.getValiatedEnterprises(new PageBean(page, 0, size));
	}
       
        if(StringUtil.isEmpty(enterprisesPO)){
            return jsonUtil.setResultFail("无相关结果").toJsonString();
        }
        System.out.println(enterprisesPO.size());
        for (Enterprise enterprise : enterprisesPO) {
            enterprises.add(new EnterpriseBean(enterprise));
	}
        return jsonUtil.setResultSuccess().setModelList(enterprises).toJsonString("/admin/getEnterprises");
    }

    @RequestMapping(value = "/admin/getUsers", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getUsers(
            @RequestParam(value = "page", required = false, defaultValue = "-1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "-1") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "1") int sort) {
        List<User> users = new LinkedList<User>();
        List<Object> values = new ArrayList<Object>();
        values.add(User.TYPE_ENTER);
        values.add(User.TYPE_USER);
        String orderStr = User.REGEDITTIME;
        if(sort == 1){
            orderStr = User.REGEDITTIME;
        }else if (sort == 2) {
	    orderStr = User.LASTLOGIN;
	}else if (sort == 3) {
	    orderStr = User.ACCOUNT;
	}else {
	    orderStr = User.REGEDITTIME;
	}
        if(page == 0  && size == 0) {    
           users = userManager.getNormalUser(User.TYPE, values, null, orderStr);
           return jsonUtil.setResultSuccess().setModelList(users).toJsonString("/admin/getUsers");
        }
        if(!StringUtil.isPositive(page,size))
            return  jsonUtil.setResultFail("参数错误").toJsonString();
        users = userManager.getNormalUser(User.TYPE, values,new PageBean(page, 0, size), orderStr);
        return jsonUtil.setResultSuccess().setModelList(users).toJsonString("/admin/getUsers");
    }
    
    @RequestMapping(value = "/admin/getUsersByType", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getUsersByType(
            @RequestParam(value = "page", required = false, defaultValue = "-1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "-1") int size,
            @RequestParam(value = "type", required = false, defaultValue = "-1") int type,
            @RequestParam(value = "sort", required = false, defaultValue = "1") int sort) {
        if(type != User.TYPE_ENTER && type != User.TYPE_USER){
            return ParamError();
        }
        List<User> users = new LinkedList<User>();
        List<Object> values = new ArrayList<Object>();
        values.add(type);
        String orderStr = User.REGEDITTIME;
        if(sort == 1){
            orderStr = User.REGEDITTIME;
        }else if (sort == 2) {
	    orderStr = User.LASTLOGIN;
	}else if (sort == 3) {
	    orderStr = User.ACCOUNT;
	}else {
	    orderStr = User.REGEDITTIME;
	}
        if(page == 0  && size == 0) {    
           users = userManager.getNormalUser(User.TYPE, values, null, orderStr);
           return jsonUtil.setResultSuccess().setModelList(users).toJsonString("/admin/getUsers");
        }
        if(!StringUtil.isPositive(page,size))
            return  jsonUtil.setResultFail("参数错误").toJsonString();
        users = userManager.getNormalUser(User.TYPE, values, new PageBean(page, 0, size), orderStr);
        return jsonUtil.setResultSuccess().setModelList(users).toJsonString("/admin/getUsers");
    }

    @RequestMapping(value = "/admin/searchUser", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_searchUser(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        if (StringUtil.hasText(keyword)) {
            List<User> users = userManager.search(User.ACCOUNT, keyword);
            return jsonUtil.setResultSuccess().setModelList(users).toJsonString("/admin/getUsers");
        }
        return jsonUtil.setResultFail().setTip("参数错误").toJsonString();
    }

    @RequestMapping(value = "/admin/deleteFeedBack", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_deleteFeedBack(
            @RequestParam(value = "id", required = false, defaultValue = "") int id) throws DaoException {
        if (id < 1)
            return jsonUtil.setResultFail("参数不合法").toJsonString();
        FeedBackMass feedBackMass = feedBackMassManager.find(id);
        feedBackMassManager.delete(id);
        return jsonUtil.setResultSuccess().toJsonString();
    }
    

    @RequestMapping(value = "/admin/flushJsonTypes", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_flushJsonTypes() {
       if(medicineTypeManager.flushJsonString())
	   return jsonUtil.setResultSuccess().toJsonString();
       return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/admin/getEnterCheck", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getEnterCheck(int pageSize, int pageNum) {
        if (pageSize < 0 || pageNum < 0)
            return jsonUtil.setResultFail("参数不能小于0").toJsonString();
        List<Enterprise> enterprise = null;
        if(pageNum == 0 && pageSize==0){
            enterprise = enterpriseManager.findByParam(Enterprise.STATUS, Enterprise.ON_CHECKING);
        }else {
            enterprise = enterpriseManager.findByPage(Enterprise.STATUS, Enterprise.ON_CHECKING, new PageBean(pageNum, 0, pageSize));
	}
        if(StringUtil.isEmpty(enterprise)){
            return jsonUtil.setResultFail("无相关结果").toJsonString();
        }
        return jsonUtil.setModelList(enterprise).setResultSuccess().toJsonString("/admin/getEnterCheck");

    }
    
    @RequestMapping(value = "/admin/meeting/block", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_blockMeeting(@RequestParam(value="id", defaultValue="-1", required=false) int id,
	    @RequestParam(value="value", defaultValue="1", required=false) int value) throws DaoException {
	Result result = null;
        if(1 == value){
            result = meetingManager.block(id);            
        }else if (0 == value) {
	    result = meetingManager.unblock(id);
	}else {
	    return ParamError();
	}
        if(result.isSuccess()){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail(result.getErrorCode()).toJsonString();
    }
    
    @RequestMapping(value = "/admin/video/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getVideo(HttpSession httpSession,
	    @RequestParam(value = "pageSize", defaultValue = "-1", required = false) int pageSize,
	    @RequestParam(value = "page", defaultValue = "-1", required = false) int page) {
	if(pageSize < 0 || page < 0){
	    return ParamError();
	}
	List<Video> videos = null;
	if(pageSize == 0 && page == 0){
	     videos = videoManager.findAll();
	}else {
	    videos = videoManager.findByPage(new PageBean(page, 0, pageSize));	    
	}
	
	if(StringUtil.isEmpty(videos)){
	    return jsonUtil.setResultFail("无相关结果").toJsonString();
	}
	return jsonUtil.setResultSuccess().setModelList(videos).toJsonString("/enterprise/video/get");
    }
    
    @RequestMapping(value = "/admin/feedback/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getFeedback(HttpSession httpSession,
	    @RequestParam(value = "pageSize", defaultValue = "-1", required = false) int pageSize,
	    @RequestParam(value = "page", defaultValue = "-1", required = false) int page) {
	if(pageSize < 0 || page < 0){
	    return ParamError();
	}
	List<FeedBackMass> feedBackMassesPO = null;
	List<FeedBackMassBean> feedBackMasses = new LinkedList<FeedBackMassBean>();
	if(pageSize == 0 && page == 0){
	    feedBackMassesPO = feedBackMassManager.findByParam(FeedBackMass.STATUS, FeedBackMass.NO_READ);
	}else {
	    feedBackMassesPO = feedBackMassManager.findByPage(FeedBackMass.STATUS, FeedBackMass.NO_READ, new PageBean(page, 0, pageSize));	    
	}
	if(StringUtil.isEmpty(feedBackMassesPO)){
	    return jsonUtil.setResultFail("无相关结果").toJsonString();
	}
	for (FeedBackMass feedBackMass : feedBackMassesPO) {
	    feedBackMasses.add(new FeedBackMassBean(feedBackMass));
	}
	return jsonUtil.setResultSuccess().setModelList(feedBackMasses).toJsonString("/enterprise/feedback/get");
    }
    
    @RequestMapping(value = "/admin/video/search", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_searchVideo(HttpSession httpSession,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        if(!StringUtil.hasText(keyword)){
            return ParamError();
        }
        List<Video> nameResult = videoManager.search(Video.NAME, keyword);
        List<Video> typeResult = new LinkedList<Video>();
        List<Subject> subjects = subjectManager.search(Subject.NAME, keyword);
        for (Subject subject : subjects) {
	    typeResult.addAll(videoManager.findBySql(Video.TABLE_NAME, Video.SUBJECT_ID, subject.getId()));
	}
        if(nameResult.size() < 1 && typeResult.size() < 1){
            return jsonUtil.setResultFail("无相关结果").toJsonString();
        }
        return jsonUtil.setResultSuccess().setJsonObject("nameResult", nameResult).setJsonObject("typeResult", typeResult).toJsonString("/admin/video/get");
        
       
    }
    
    @RequestMapping(value = "/admin/video/block", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_blockVideo(@RequestParam(value="id", defaultValue="-1", required=false) int id, 
	    @RequestParam(value="value", defaultValue="1", required=false) int value) throws DaoException {
	Result result = null;
        if(1 == value){
            result = videoManager.block(id);            
        }else if (0 == value) {
	    result = videoManager.unblock(id);
	}else {
	    return ParamError();
	}
        if(result.isSuccess()){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail(result.getErrorCode()).toJsonString();
    }
    
    @RequestMapping(value = "/admin/enterprise/block", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_blockEnterprise(@RequestParam(value="id", defaultValue="-1", required=false) int id, 
	    @RequestParam(value="value", defaultValue="1", required=false) int value) throws DaoException {
	Result result = null;
        if(1 == value){
            result = enterpriseManager.block(id);            
        }else if (0 == value) {
	    result = enterpriseManager.unblock(id);
	}else {
	    return ParamError();
	}
        if(result.isSuccess()){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail(result.getErrorCode()).toJsonString();
    }
    
    @RequestMapping(value = "/admin/user/block", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_blockUser(@RequestParam(value="id", defaultValue="-1", required=false) int id,
	    @RequestParam(value="value", defaultValue="1", required=false) int value) throws DaoException {
	Result result = null;
        if(1 == value){
            result = userManager.block(id);            
        }else if (0 == value) {
	    result = userManager.unblock(id);
	}else {
	    return ParamError();
	}
        if(result.isSuccess()){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail(result.getErrorCode()).toJsonString();
    }
    
    @RequestMapping(value = "/admin/enterprise/info/update", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_updateEnterpriseInfo(HttpSession httpSession, @Validated @ModelAttribute EnterpriseInfoVO enterpriseInfoVO, BindingResult result,
	    @RequestParam(value = "id", defaultValue = "-1", required=false) int id) throws DaoException {
        if (result.hasErrors()){            
            return toErrorJson(result);
        }
        if(id < 1){
            return ParamError();
        }
        Result res = enterpriseManager.updateInfo(id, enterpriseInfoVO);
        if(res.isSuccess()){
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail(res.getErrorCode()).toJsonString();
    }

    @RequestMapping(value = "/admin/enterprise/unpass", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_unPassEnterprise(@RequestParam(value="id", defaultValue="-1", required=false) int id) throws DaoException {
        if(id < 1){
            return ParamError();
        }
        Enterprise enterprise = enterpriseManager.find(id);
        if(enterprise.getStatus() == Enterprise.ON_CHECKING){
            enterpriseManager.delete(enterprise.getId());
            userManager.delete(enterprise.getUser().getId());
            return jsonUtil.setResultSuccess().toJsonString();
        }
        return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/admin/video/delete", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_deleteVideo(
            @RequestParam(value = "id", required = false, defaultValue = "") int id) throws DaoException {
        if (id < 1){
            return ParamError();
        }
        Video video = videoManager.find(id);
        videoManager.delete(id);
        return jsonUtil.setResultSuccess().toJsonString();
    }
    
    @RequestMapping(value = "/admin/medicine/get/detail", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_getMedicineDetail(
	    HttpSession httpSession,
	    @RequestParam(value = "id", required = false, defaultValue = "-1") int id,
	    @RequestParam(value = "type", required = false, defaultValue = "-1") int type) throws DaoException {
	    Object jsonObject = null;
	    if(type == Medicine.CHINESE){	
		ChineseMedicine medicine = chineseMedicineManager.find(id);
		    jsonObject = new ChineseMedicineBean(medicine);
	    }
	    else if (type == Medicine.WEST){	
		WestMedicine medicine = westMedicineManager.find(id);
		    jsonObject = new WestMedicineBean(medicine);
	    }else {
		return ParamError();
	    }
	    return jsonUtil.setResultSuccess().setJsonObject("entity", jsonObject).toJsonString("/admin/medicine/get/detail");
    }
    
    @RequestMapping(value = "/admin/password/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String admin_updatePassword(HttpSession httpSession,
	    String oldPassword, String newPassword) {
	String account = getAccount(httpSession);
	if(userManager.updatePassword(account, oldPassword, newPassword)){
	    return jsonUtil.setResultSuccess().toJsonString();
	}
	return jsonUtil.setResultFail().toJsonString();
    }
 

}
