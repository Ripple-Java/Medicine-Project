package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.FeedBackMassDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.FeedBackMass;
import com.rippletec.medicine.service.FeedBackMassManager;

@Service(FeedBackMassManager.NAME)
public class FeedBackMassManagerImpl extends BaseManager<FeedBackMass> implements FeedBackMassManager{
    
    @Resource(name=FeedBackMassDao.NAME)
    private FeedBackMassDao feedBackMassDao;

    @Override
    protected FindAndSearchDao<FeedBackMass> getDao() {
	return this.feedBackMassDao;
    }

}
