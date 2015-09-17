package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.service.EnterChineseMedicineManager;

@Repository(EnterChineseMedicineManager.NAME)
public class EnterChineseMedicineManagerImpl extends BaseManager<EnterChineseMedicine> implements EnterChineseMedicineManager{

    @Resource(name = EnterChineseMedicineDao.NAME)
    private EnterChineseMedicineDao enterChineseMedicineDao;
    
    @Override
    protected FindAndSearchDao<EnterChineseMedicine> getDao() {
	return this.enterChineseMedicineDao;
    }

}
