package com.rippletec.medicine.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.BackGroundMedicineTypeDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.BackGroundMedicineType;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.service.BackGroundMedicineTypeManager;


@Service(BackGroundMedicineTypeManager.NAME)
public class BackGroundMedicineTypeManagerImpl extends BaseManager<BackGroundMedicineType> implements BackGroundMedicineTypeManager{

    @Resource(name=BackGroundMedicineTypeDao.NAME)
    private BackGroundMedicineTypeDao backGroundMedicineTypeDao;
    
    
    @Override
    protected FindAndSearchDao<BackGroundMedicineType> getDao() {
	return this.backGroundMedicineTypeDao;
    }


    @Override
    public Integer uniqueSave(BackGroundMedicineType backGroundMedicineType) {
	List<BackGroundMedicineType> backGroundMedicineTypes = findByParam("firstType_id", backGroundMedicineType.getFirstType_id());
	if(backGroundMedicineTypes != null && backGroundMedicineTypes.size() > 0){
	    System.out.println(backGroundMedicineTypes.size());
	    for (BackGroundMedicineType backGroundMedicineType_temp : backGroundMedicineTypes) {
		
		if(backGroundMedicineType.equals(backGroundMedicineType_temp))
		    return backGroundMedicineType.getId();
	    }
	}
	return save(backGroundMedicineType);
    }


}
