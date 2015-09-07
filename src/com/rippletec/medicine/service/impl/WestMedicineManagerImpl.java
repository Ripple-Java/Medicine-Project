package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.FindByPageDao;
import com.rippletec.medicine.dao.WestMedicineDao;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.WestMedicineManager;

@Service(WestMedicineManager.NAME)
public class WestMedicineManagerImpl extends BaseManager<WestMedicine> implements WestMedicineManager{

    @Resource(name=WestMedicineDao.NAME)
    private WestMedicineDao westMedicineDao;
    
    @Override
    protected FindByPageDao<WestMedicine> getDao() {
	return null;
    }

}
