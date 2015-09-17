package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.LivenessDao;
import com.rippletec.medicine.model.Liveness;
import com.rippletec.medicine.service.LivenessManager;


@Service(LivenessManager.NAME)
public class LivenessManagerImpl extends BaseManager<Liveness> implements LivenessManager{
    
    @Resource(name=LivenessDao.NAME)
    private LivenessDao livenessDao;

    @Override
    protected FindAndSearchDao<Liveness> getDao() {
	return this.livenessDao;
    }
    

}
