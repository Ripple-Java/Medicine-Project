package com.rippletec.medicine.controller;

import java.net.Authenticator.RequestorType;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.model.BaseModel;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineDocument;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.service.EnterpriseMedicineTypeManager;
import com.rippletec.medicine.utils.JsonUtil;

/**
 * @author Liuyi
 *
 */
@Controller
@RequestMapping("/App")
public class AppController extends BaseController {
    
    @RequestMapping(value = "/stu/getEnterprise", method=RequestMethod.GET)
    @ResponseBody
    public String stu_getEnterprise(@RequestParam(value = "type" , required=false, defaultValue="0") int type,
	    @RequestParam(value = "size" , required=false, defaultValue="0") int size,
	    @RequestParam(value = "currentPage" , required=false, defaultValue="0") int currentPage) {
	if (type >0 && size > 0 && currentPage>0 ) {
	    List<Enterprise> enterprises = enterpriseManager.getEnterprise(size,type,currentPage);
	    return jsonUtil.setModelList(enterprises).toJsonString("/stu/getEnterprise");
	}
	return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/stu/getEnterMedicineType", method=RequestMethod.GET)
    @ResponseBody
    public String stu_getEnterpriseMedicineType(@RequestParam(value="id", required=false, defaultValue="-1") int id) {
	if(id >= 0){
	    List<EnterpriseMedicineType> enterpriseMedicineTypes = enterpriseMedicineTypeManager.getTypesByEnterpriseId(id);
	    return jsonUtil.setModelList(enterpriseMedicineTypes).toJsonString("/stu/getEnterMedicineType");
	}
	return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/stu/getEnterMedicine", method=RequestMethod.GET)
    @ResponseBody
    public String stu_getEnterpriseMedicine(@RequestParam(value="typeId", required=false, defaultValue="-1") int typeId,
	    @RequestParam(value = "size" , required=false, defaultValue="0") int size,
	    @RequestParam(value = "currentPage" , required=false, defaultValue="0") int currentPage) {
	if(typeId >= 0){
	    Map<String, Object> res = enterpriseMedicineTypeManager.getMedicinesByTypeId(typeId, size, currentPage);
	    if(res == null)
		return "{}";
	    return jsonUtil.setModelList((List) res.get("medicines")).setJsonObject("type", res.get("type")).toJsonString("/stu/getEnterMedicine");
	}
	return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/stu/getMedicineType", method=RequestMethod.GET)
    @ResponseBody
    public String stu_getMedicineType(@RequestParam(value="parentId", required=false, defaultValue="-1") int parentId) {
	if (parentId >= -1) {
	    List<MedicineType> medicineTypes = medicineTypeManager.getTypeByParentId(parentId);
	    return jsonUtil.setModelList(medicineTypes).toJsonString("/stu/getMedicineType");
	}
	return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/stu/getMedicine", method=RequestMethod.GET)
    @ResponseBody
    public String stu_getMedicine(@RequestParam(value="typeId", required=false, defaultValue="0") int typeId,
	    @RequestParam(value = "size" , required=false, defaultValue="0") int size,
	    @RequestParam(value = "currentPage" , required=false, defaultValue="0") int currentPage) {
	if (typeId >= 0) {
	    Map<String, Object> res = medicineTypeManager.getMedicineByTypeId(typeId, new PageBean(currentPage, 0, size));
	    if(res == null)
		return "{}";
	    return jsonUtil.setModelList((List) res.get("medicines")).setJsonObject("type", res.get("type")).toJsonString("/stu/getMedicine");
	}
	return jsonUtil.setResultFail().toJsonString();
    }
    
    @RequestMapping(value = "/stu/getMedicineDom", method=RequestMethod.GET)
    @ResponseBody
    public String stu_getMedicineDom(@RequestParam(value = "medicineId" , required=false, defaultValue="0") int medicineId,
	    @RequestParam(value = "type" , required=false, defaultValue="0") int type) {
	if (type >0 && medicineId >0) {
	   List<MedicineDocument> medicineDocuments = medicineDocumentManager.getDocument(medicineId, type);
	   return jsonUtil.setModelList(medicineDocuments).toJsonString("/stu/getMedicineDom");
	}
	return jsonUtil.setResultFail().toJsonString();
    }
    
    
}
