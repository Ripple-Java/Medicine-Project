package com.rippletec.medicine.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.DBLogDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.DBLog;
import com.rippletec.medicine.service.DBLoger;

@Repository(DBLoger.NAME)
public class DBLogerImpl extends BaseManager<DBLog> implements DBLoger{
    
    @Resource(name = DBLogDao.NAME)
    private DBLogDao dbLogDao;

    @Override
    protected FindAndSearchDao<DBLog> getDao() {
	return this.dbLogDao;
    }

    @Override
    public Integer getVersion() {
	int count = dbLogDao.getCount();
	return count==0 ? DEFAULT_VERSION : count+DEFAULT_VERSION;
    }

    @Override
    public Integer uniqueSave(DBLog dbLog) {
	List<DBLog> dbLogs = findByParam(DBLog.OBJCET_ID, dbLog.getObject_id());
	if(dbLogs != null && dbLogs.size()>0){
	    DBLog old = dbLogs.get(0);
	    old.setAction(dbLog.getAction());
	    old.setDate(dbLog.getDate());
	    old.setDbTable(dbLog.getDbTable());
	    old.setDbVersion(dbLog.getDbVersion());
	    update(old);
	    return old.getId();
	}
	return save(dbLog);
    }

}
