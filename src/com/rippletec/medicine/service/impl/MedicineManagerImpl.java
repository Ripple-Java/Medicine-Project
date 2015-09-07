package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.FindByPageDao;
import com.rippletec.medicine.dao.MedicineDao;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.service.MedicineManager;

@Service(MedicineManager.NAME)
public class MedicineManagerImpl extends BaseManager<Medicine> implements MedicineManager{
    
    @Resource(name=MedicineDao.NAME)
    private MedicineDao medicineDao;

    @Override
    protected FindByPageDao<Medicine> getDao() {
	return this.medicineDao;
    }
    
}
