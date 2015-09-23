package com.rippletec.medicine.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.ChineseMedicineDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.MedicineTypeDao;
import com.rippletec.medicine.dao.WestMedicineDao;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.MedicineTypeManager;

@Service(MedicineTypeManager.NAME)
public class MedicineTypeManagerImpl extends BaseManager<MedicineType> implements MedicineTypeManager{

    @Resource(name=MedicineTypeDao.NAME)
    private MedicineTypeDao medicineTypeDao;
    @Resource(name=ChineseMedicineDao.NAME)
    private ChineseMedicineDao chineseMedicineDao;
    @Resource(name=WestMedicineDao.NAME)
    private WestMedicineDao westMedicineDao;
    
    @Override
    protected FindAndSearchDao<MedicineType> getDao() {
	return this.medicineTypeDao;
    }

    @Override
    public Map<String, Object> getMedicineByTypeId(int typeId, PageBean pageBean) {
	MedicineType medicineType = medicineTypeDao.find(typeId);
	Map<String, Object> res = new HashMap<String, Object>();
	if (medicineType == null)
	    return null;
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
    public List<MedicineType> getTypeByParentId(int parentId) {
	return medicineTypeDao.findByParam(MedicineType.PARENT_TYPE_ID, parentId);
    }

    @Override
    public MedicineType isExist(MedicineType medicineType) {
	List<MedicineType> medicineTypes = findByParam(MedicineType.NAME, medicineType.getName());
	if(medicineTypes == null || medicineTypes.size() ==0)
	    return null;
	for (MedicineType medicineTypeTemp : medicineTypes) {
	    if (medicineType.equals(medicineTypeTemp))
		return medicineTypeTemp;
	}
	return null;
	
    }

    @Override
    public Integer uniqueSave(MedicineType medicineType) {
	List<MedicineType> medicineTypes = findByParam(MedicineType.NAME, medicineType.getName());
	if(medicineTypes != null && medicineTypes.size() > 0){
	    for (MedicineType medicineType_temp : medicineTypes) {
		if(medicineType.equals(medicineType_temp))
		    return medicineType_temp.getId();
	    }
	}
	return save(medicineType);
    }

}
