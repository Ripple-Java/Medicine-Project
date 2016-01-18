package com.rippletec.medicine.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.ChineseMedicineDao;
import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.dao.EnterWestMedicineDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.MedicineTypeDao;
import com.rippletec.medicine.dao.WestMedicineDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.UtilException;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.MedicineTypeManager;
import com.rippletec.medicine.utils.ErrorCode;
import com.rippletec.medicine.utils.JsonUtil;
import com.rippletec.medicine.utils.ParamMap;
import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;

@Service(MedicineTypeManager.NAME)
public class MedicineTypeManagerImpl extends BaseManager<MedicineType> implements MedicineTypeManager{

    @Resource(name=MedicineTypeDao.NAME)
    private MedicineTypeDao medicineTypeDao;
    @Resource(name=ChineseMedicineDao.NAME)
    private ChineseMedicineDao chineseMedicineDao;
    @Resource(name=WestMedicineDao.NAME)
    private WestMedicineDao westMedicineDao;
    @Resource(name=EnterChineseMedicineDao.NAME)
    private EnterChineseMedicineDao enterChineseMedicineDao;
    @Resource(name=EnterWestMedicineDao.NAME)
    private EnterWestMedicineDao enterWestMedicineDao;
    @Resource(name=JsonUtil.NAME)
    private JsonUtil jsonUtil;
    
    @Override
    protected FindAndSearchDao<MedicineType> getDao() {
	return this.medicineTypeDao;
    }
    
    

    @Override
    public Integer save(MedicineType model) throws DaoException{
	int id =  super.save(model);
	try {
	    if(flushJsonString())
	        return id;
	} catch (UtilException e) {
	    Logger.getLogger(MedicineTypeManagerImpl.class).error(StringUtil.getLoggerInfo(e.getErrorCode(), e.getInfo()));
	}
	return 0;
    }



    @Override
    public Map<String, Object> getMedicineByTypeId(int typeId, PageBean pageBean) throws DaoException {
	MedicineType medicineType = medicineTypeDao.find(typeId);
	Map<String, Object> res = new HashMap<String, Object>();
	int type = medicineType.getGib_type();
	if( type == MedicineType.CHINESE){
	    List<ChineseMedicine> medicines = chineseMedicineDao.findBySql(ChineseMedicine.TABLE_NAME, ChineseMedicine.MEDICINE_TYPE_ID, typeId, pageBean);
	    res.put("medicines", medicines);
	}
	else{
	    List<WestMedicine> medicines = westMedicineDao.findBySql(WestMedicine.TABLE_NAME, WestMedicine.MEDICINE_TYPE_ID, typeId,pageBean);
	    res.put("medicines", medicines);
	}
	res.put("type", type);
	return res;
    }

    @Override
    public List<MedicineType> getTypeByParentId(int parentId) throws DaoException {
	return medicineTypeDao.findByParam(MedicineType.PARENT_TYPE_ID, parentId);
    }

    @Override
    public MedicineType isExist(MedicineType medicineType) throws DaoException {
	List<MedicineType> medicineTypes = findByParam(MedicineType.NAME, medicineType.getName());
	for (MedicineType medicineTypeTemp : medicineTypes) {
	    if (medicineType.equals(medicineTypeTemp))
		return medicineTypeTemp;
	}
	return null;
	
    }

    @Override
    public Integer uniqueSave(MedicineType medicineType) throws DaoException {
	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put(MedicineType.NAME, medicineType.getName());
	paramMap.put(MedicineType.PARENT_TYPE_ID, medicineType.getParent_type_id());
	List<MedicineType> medicineTypes = null;
	try {
	    medicineTypes = findByParam(paramMap);
	} catch (DaoException e) {
	    for (MedicineType medicineType_temp : medicineTypes) {
		if(medicineType.equals(medicineType_temp))
		    return medicineType_temp.getId();
	    }
	}
	return save(medicineType);
    }



    @Override
    public Boolean flushJsonString() throws UtilException, DaoException {
	List<MedicineType> firstType = medicineTypeDao.findByParam(MedicineType.PARENT_TYPE_ID, MedicineType.DEFAULT_PARENT_ID);
	Map<String, Object> firstMap = new LinkedMap();
	List<MedicineType> tampList = null;
	firstMap.put("superType", firstType);
	jsonUtil.setObject("first", firstMap);
	List<MedicineType> secondType = new ArrayList<MedicineType>();
	Map<String, Object> secondMap = new LinkedMap();
	for (MedicineType medicineType : firstType) {
	    tampList = medicineTypeDao.findByParam(MedicineType.PARENT_TYPE_ID, medicineType.getId());
	    secondMap.put(medicineType.getName(),tampList);
	    secondType.addAll(tampList);
	}
	jsonUtil.setObject("second", secondMap);
	List<MedicineType> thirdType = new ArrayList<MedicineType>();
	Map<String, Object> thirdMap = new LinkedMap();
	for (MedicineType medicineType : secondType) {
	    tampList = medicineTypeDao.findByParam(MedicineType.PARENT_TYPE_ID, medicineType.getId());
	    thirdMap.put(medicineType.getName(),tampList);
	    thirdType.addAll(tampList);
	}
	jsonUtil.setObject("third", thirdMap);
	List<MedicineType> forthType = new ArrayList<MedicineType>();
	Map<String, Object> forthMap = new LinkedMap();
	for (MedicineType medicineType : thirdType) {
	    forthMap.put(medicineType.getName(),medicineTypeDao.findByParam(MedicineType.PARENT_TYPE_ID, medicineType.getId()));
	}
	jsonUtil.setObject("forth", forthMap);	
	org.springframework.core.io.Resource resource = new ClassPathResource("/dataTemp.properties");
	Properties properties;
	FileOutputStream fos = null;
	try {
	    properties = PropertiesLoaderUtils.loadProperties(resource);
	    properties.setProperty("medicine.typeJson", jsonUtil.setSuccessRes().toJson("/user/getAllMedicineType"));
	    fos = new FileOutputStream(resource.getFile());
	    properties.store(fos, null);
	    return true;
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
	finally{
	    if(fos != null){
		try {
		    fos.close();
		} catch (IOException e) {
		    e.printStackTrace();
		    return false;
		}
	    }
	}
    }



    @Override
    public String getTypeJson() {
	org.springframework.core.io.Resource resource = new ClassPathResource("/dataTemp.properties");
	Properties properties;
	try {
	    properties = PropertiesLoaderUtils.loadProperties(resource);
	    return properties.getProperty("medicine.typeJson");
	} catch (IOException e) {
	    e.printStackTrace();
	    return null;
	}
    }



    @Override
    public List<MedicineType> getAllChild(MedicineType medicineType) throws DaoException {
	List<MedicineType> aList = new ArrayList<MedicineType>();
	List<MedicineType> medicineTypes = medicineTypeDao.findByParam(MedicineType.PARENT_TYPE_ID, medicineType.getId());
    	for (MedicineType medicineTypeTemp : medicineTypes) {
    	    if(medicineTypeTemp.getFlag()){
    		aList.add(medicineTypeTemp);
    		continue;
    	    }
    	    aList.addAll(medicineTypeDao.findByParam(MedicineType.PARENT_TYPE_ID, medicineTypeTemp.getId()));
    	}
	return aList;
    }
    
    @Override
    public List<BackGroundMedicineVO> getBackGroundMedicineVO(MedicineType medicineType){
	List<BackGroundMedicineVO> res = new LinkedList<BackGroundMedicineVO>();
	if(medicineType.getGib_type() == MedicineType.CHINESE){
		Set<ChineseMedicine> chineseMedicines = medicineType.getChineseMedicines();
		for (ChineseMedicine chineseMedicine : chineseMedicines) {
		    res.add(new BackGroundMedicineVO(medicineType.getBackGroundMedicineType(), chineseMedicine.getName(), null, chineseMedicine.getId(), null));
		}
		Set<EnterChineseMedicine> enterChineseMedicines = medicineType.getEnterChineseMedicines();
		if(enterChineseMedicines == null || enterChineseMedicines.size() < 1)
		    return res;
		for (EnterChineseMedicine enterChineseMedicine : enterChineseMedicines) {
		    res.add(new BackGroundMedicineVO(medicineType.getBackGroundMedicineType(), enterChineseMedicine.getName(), enterChineseMedicine.getEnterprise_name(), enterChineseMedicine.getId(), enterChineseMedicine.getUpdateTime()));
		}
	    }else{
		Set<WestMedicine> westMedicines = medicineType.getWestMedicines();
		for (WestMedicine westMedicine : westMedicines) {
		    res.add(new BackGroundMedicineVO(medicineType.getBackGroundMedicineType(), westMedicine.getName(), null, westMedicine.getId(), null));
		}
		Set<EnterWestMedicine> enterWestMedicines = medicineType.getEnterWestMedicines();
		if(enterWestMedicines == null || enterWestMedicines.size() < 1)
		    return res;
		for (EnterWestMedicine enterWestMedicine : enterWestMedicines) {
		    res.add(new BackGroundMedicineVO(medicineType.getBackGroundMedicineType(), enterWestMedicine.getName(), enterWestMedicine.getEnterprise_name(), enterWestMedicine.getId(), enterWestMedicine.getUpdateTime()));
		}
	    }
	return res;
    }



    @Override
    public List<BackGroundMedicineVO> searchBackGroundVO(String keyword) throws DaoException {
	List<BackGroundMedicineVO> backGroundMedicineVOs = new LinkedList<BackGroundMedicineVO>();
	List<MedicineType> medicineTypes = search(MedicineType.NAME, keyword);
	for (MedicineType medicineType : medicineTypes) {
	    if (medicineType.hasChild()) {
		List<MedicineType> chiildMedicineTypes = getAllChild(medicineType);
		for (MedicineType childMedicineType : chiildMedicineTypes) {
		    backGroundMedicineVOs
			    .addAll(getBackGroundMedicineVO(childMedicineType));
		}
	    } else {
		backGroundMedicineVOs
			.addAll(getBackGroundMedicineVO(medicineType));
	    }
	}
	return backGroundMedicineVOs;
    }



    @Override
    public List<BackGroundMedicineVO> getEnterBackGroundMedicineVO(
	    MedicineType medicineType, int enterpriseId) throws DaoException {
	List<BackGroundMedicineVO> res = new LinkedList<BackGroundMedicineVO>();
	if(medicineType.getGib_type() == MedicineType.CHINESE){
	    	ParamMap paramMap = new ParamMap().put(EnterChineseMedicine.MEDICINE_TYPE_ID, medicineType.getId())
	    				          .put(EnterChineseMedicine.ENTERPRISE_ID, enterpriseId);
		List<EnterChineseMedicine> enterChineseMedicines = enterChineseMedicineDao.findBySql(EnterChineseMedicine.TABLE_NAME, paramMap);
		for (EnterChineseMedicine enterChineseMedicine : enterChineseMedicines) {
		    res.add(new BackGroundMedicineVO(medicineType.getBackGroundMedicineType(), enterChineseMedicine.getName(), enterChineseMedicine.getEnterprise_name(), enterChineseMedicine.getId(), enterChineseMedicine.getUpdateTime()));
		}
	    }else{
		ParamMap paramMap = new ParamMap().put(EnterWestMedicine.MEDICINE_TYPE_ID, medicineType.getId())
			          .put(EnterWestMedicine.ENTERPRISE_ID, enterpriseId);
		List<EnterWestMedicine> enterWestMedicines = enterWestMedicineDao.findBySql(EnterWestMedicine.TABLE_NAME, paramMap);
		for (EnterWestMedicine enterWestMedicine : enterWestMedicines) {
		    res.add(new BackGroundMedicineVO(medicineType.getBackGroundMedicineType(), enterWestMedicine.getName(), enterWestMedicine.getEnterprise_name(), enterWestMedicine.getId(), enterWestMedicine.getUpdateTime()));
		}
	    }
	return res;
    }



    @Override
    public MedicineType findType(int medicineType_id, int gibType) throws DaoException {
	ParamMap paramMap = new ParamMap().put(MedicineType.GIB_TYPE, gibType)
					  .put(MedicineType.ID, medicineType_id);
	List<MedicineType> medicineTypes = medicineTypeDao.findByParam(paramMap);
	return medicineTypes.get(0);
    }

}
