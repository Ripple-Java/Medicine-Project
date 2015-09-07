package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.ChineseMedicineDao;
import com.rippletec.medicine.dao.FindByPageDao;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.service.ChineseMedicineManager;

@Service(ChineseMedicineManager.NAME)
public class ChineseMedicineManagerImpl extends BaseManager<ChineseMedicine> implements
	ChineseMedicineManager {
    
    @Resource(name=ChineseMedicineDao.NAME)
    private ChineseMedicineDao chineseMedicineDao;

    @Override
    protected FindByPageDao<ChineseMedicine> getDao() {
	return this.chineseMedicineDao;
    }

}
