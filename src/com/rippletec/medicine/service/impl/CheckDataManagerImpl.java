package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.CheckDataDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.service.CheckDataManager;

@Service(CheckDataManager.NAME)
public class CheckDataManagerImpl extends BaseManager<CheckData> implements CheckDataManager{

    
    @Resource(name = CheckDataDao.NAME)
    private CheckDataDao checkDataDao;
    
    @Override
    protected FindAndSearchDao<CheckData> getDao() {
	return null;
    } 
}
