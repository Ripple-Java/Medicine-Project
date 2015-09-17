package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.LivenessDao;
import com.rippletec.medicine.model.Liveness;

@Repository(LivenessDao.NAME)
public class LivenessDaoImpl extends BaseDaoImpl<Liveness> implements LivenessDao{

    @Override
    public String getClassName() {
	return Liveness.CLASS_NAME;
    }

    @Override
    public Class<Liveness> getPersistClass() {
	return Liveness.class;
    }

}
