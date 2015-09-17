package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.DBLogDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.DBLog;
import com.rippletec.medicine.service.DBLoger;

@Repository(DBLoger.NAME)
public class DBLogerImpl extends BaseManager<DBLog>{
    
    @Resource(name = DBLogDao.NAME)
    private DBLogDao dbLogDao;

    @Override
    protected FindAndSearchDao<DBLog> getDao() {
	return this.dbLogDao;
    }

}
