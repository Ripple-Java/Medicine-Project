package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.CheckDataDao;
import com.rippletec.medicine.model.CheckData;

@Repository(CheckDataDao.NAME)
public class CheckDataDaoImpl extends BaseDaoImpl<CheckData> implements CheckDataDao{

    @Override
    public String getClassName() {
	return CheckData.CLASS_NAME;
    }

    @Override
    public Class<CheckData> getPersistClass() {
	return CheckData.class;
    }

}
