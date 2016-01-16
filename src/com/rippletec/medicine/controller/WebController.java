package com.rippletec.medicine.controller;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.*;
import com.rippletec.medicine.utils.FileUtil;
import com.rippletec.medicine.utils.StringUtil;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpSession;


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

    @RequestMapping(value = "/user/getCount", method = RequestMethod.GET ,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getCount(
            @RequestParam(value = "type", required = false, defaultValue = "0") int type) {
        if (type <= 0)
            return jsonUtil.setResultFail("type参数必须大于0").toJsonString();
        int count = 0;

        switch (type) {
            case 11:
                count = userManager.getCount(User.TABLE_NAME, User.TYPE, User.TYPE_USER, User.STATUS, new Object[]{User.STATUS_NORMAL, User.STATUS_BLOCKED});
                break;
            case 12:
                count = userManager.getCount(User.TABLE_NAME, new String[]{User.TYPE, User.STATUS}, new Object[]{User.TYPE_ENTER, User.STATUS_NORMAL});
                break;
            case 21:
                count = chineseMedicineManager.getCount(ChineseMedicine.TABLE_NAME);
                break;
            case 22:
                count = westMedicineManager.getCount(WestMedicine.TABLE_NAME);
                break;
            case 23:
                count = enterChineseMedicineManager.getCount(EnterChineseMedicine.TABLE_NAME, EnterChineseMedicine.STATUS, EnterChineseMedicine.ON_PUBLISTH);
                break;
            case 24:
                count = enterWestMedicineManager.getCount(EnterWestMedicine.TABLE_NAME, EnterWestMedicine.STATUS, EnterWestMedicine.ON_PUBLISTH);
                break;
            case 31:
                count = meetingManager.getCount(Meeting.TABLE_NAME, Meeting.STATUS, Meeting.ON_PUBLISTH);
                break;
            case 41:
                count = videoManager.getCount(Video.TABLE_NAME, Video.STATUS, Video.ON_PUBLISTH);
                break;
            default:
                break;
        }
        return jsonUtil.setResultSuccess().setJsonObject("count", count).toJsonString();
    }

    @RequestMapping(value = "/user/getPPTImageCount", method = RequestMethod.GET ,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getPPTImageCount(
            @RequestParam(value = "meetingID", required = false, defaultValue = "0") int meetingID) throws DaoException {
        Meeting meeting = meetingManager.find(meetingID);
        String path = FileUtil.getRootPath() + meeting.getPPT();
        File pptFiles = new File(path);
        if (!pptFiles.exists() || !pptFiles.isDirectory())
            return jsonUtil.setResultFail("ppt文件不存在").toJsonString();
        return jsonUtil.setJsonObject("imageCount", pptFiles.listFiles().length).setResultSuccess().toJsonString();
    }

    @RequestMapping(value = "/user/getPPT", method = RequestMethod.GET)
    @ResponseBody
    public String user_getPPT(
            @RequestParam(value = "meetingID", required = false, defaultValue = "0") int meetingID) throws DaoException {
        Meeting meeting = meetingManager.find(meetingID);
        String path = FileUtil.getRootPath() + meeting.getPPT();
        File pptFiles = new File(path);
        if (!pptFiles.exists() || !pptFiles.isDirectory())
            return jsonUtil.setResultFail("ppt文件不存在").toJsonString();
        List<String> fileNames = new ArrayList<String>();
        File[] files = pptFiles.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                int file1Index = new Integer(FileUtil.getSimpleFilename(file1.getName()));
                int file2Index = new Integer(FileUtil.getSimpleFilename(file2.getName()));
                return file1Index - file2Index;
            }
        });
        for (int i = 0; i < files.length; i++) {
            String url = meeting.getPPT() + "/" + files[i].getName();
            fileNames.add(url);
        }
        return jsonUtil.setResultSuccess().setJsonObject("images", fileNames).setJsonObject("count", files.length).toJsonString();
    }

    @RequestMapping(value = "/user/getAllMedicineType", method = RequestMethod.GET ,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getAllMedicineType() {
        String typeJson = medicineTypeManager.getTypeJson();
        if (typeJson != null && typeJson.length() > 0)
            return typeJson;
        return jsonUtil.setResultFail().toJsonString();
    }
    
    
    @RequestMapping(value = "/user/medicine/name/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String enterprise_getMedicineName(HttpSession httpSession,
	    @RequestParam(value = "catagoryId", defaultValue = "-1", required = false) int catagoryId) throws DaoException {
	MedicineType medicineType = medicineTypeManager.find(catagoryId);
	List res = null;
	if(medicineType.getGib_type() == MedicineType.CHINESE){
	    if(medicineType.getFlag()){
		res = chineseMedicineManager.findBySql(ChineseMedicine.TABLE_NAME, ChineseMedicine.MEDICINE_TYPE_ID, medicineType.getId());
	    }else {
		List<MedicineType> medicineTypes = medicineTypeManager.getAllChild(medicineType);
		res = new LinkedList<ChineseMedicine>();
		for (MedicineType medicineTypeChild : medicineTypes) {
		    res.addAll(chineseMedicineManager.findBySql(ChineseMedicine.TABLE_NAME, ChineseMedicine.MEDICINE_TYPE_ID, medicineTypeChild.getId()));
		}
		
	    }
	    
	}else if(medicineType.getGib_type() == Medicine.WEST){
	    if(medicineType.getFlag()){
		res = westMedicineManager.findBySql(WestMedicine.TABLE_NAME, WestMedicine.MEDICINE_TYPE_ID, medicineType.getId());
	    }else {
		List<MedicineType> medicineTypes = medicineTypeManager.getAllChild(medicineType);
		for (MedicineType medicineTypeChild : medicineTypes) {
		    res.addAll(westMedicineManager.findBySql(WestMedicine.TABLE_NAME, WestMedicine.MEDICINE_TYPE_ID, medicineTypeChild.getId()));
		}
	    }
	}
	if(StringUtil.isEmpty(res)){
	    return jsonUtil.setResultFail("该药品不存在").toJsonString();
	}
	return jsonUtil.setResultSuccess().setJsonObject("medicines", res).toJsonString("/user/medicine/name/get");

    }
 
    
    @RequestMapping(value = "/user/getSubject", method = RequestMethod.GET ,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String user_getSubject() {
       List<ProjectConfig> resConfigs = projectConfigManager.findByParam(ProjectConfig.KEY, ProjectConfig.SUBJECT_RESPONSE_JSON);
       if(StringUtil.isEmpty(resConfigs)){
	   return jsonUtil.setResultFail().toJsonString();
       }
       return resConfigs.get(0).getCon_value();
    }


}
