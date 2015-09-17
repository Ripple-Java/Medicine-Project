package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.EnterNewsDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.EnterNews;
import com.rippletec.medicine.service.EnterNewsManager;

@Repository(EnterNewsManager.NAME)
public class EnterNewsManagerImpl extends BaseManager<EnterNews> {

    @Resource(name = EnterNewsDao.NAME)
    private EnterNewsDao enterNewsDao;
    
    @Override
    protected FindAndSearchDao<EnterNews> getDao() {
	return this.enterNewsDao;
    }
    

}
