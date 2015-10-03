package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.EnterWestMedicineDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.service.EnterWestMedicineManager;

@Repository(EnterWestMedicineManager.NAME)
public class EnterWestMedicineManagerImpl extends BaseManager<EnterWestMedicine> implements EnterWestMedicineManager{
    
    @Resource(name = EnterWestMedicineDao.NAME)
    private EnterWestMedicineDao enterWestMedicineDao;

    @Override
    protected FindAndSearchDao<EnterWestMedicine> getDao() {
	return this.enterWestMedicineDao;
    }

    
}
