package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.FeedBackMassDao;
import com.rippletec.medicine.model.FeedBackMass;


@Repository(FeedBackMassDao.NAME)
public class FeedBackMassDaoImpl extends BaseDaoImpl<FeedBackMass> implements FeedBackMassDao{

    @Override
    public String getClassName() {
	return FeedBackMass.CLASSE_NAME;
    }

    @Override
    public Class<FeedBackMass> getPersistClass() {
	return FeedBackMass.class;
    }

}
