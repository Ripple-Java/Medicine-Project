package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.DBLogDao;
import com.rippletec.medicine.model.DBLog;

@Repository(DBLogDao.NAME)
public class DBLogDaoImpl extends BaseDaoImpl<DBLog> implements DBLogDao{

    @Override
    public String getClassName() {
	return DBLog.CLASS_NAME;
    }

    @Override
    public Class<DBLog> getPersistClass() {
	return DBLog.class;
    }
   

}
