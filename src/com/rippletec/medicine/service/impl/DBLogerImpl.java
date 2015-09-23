package com.rippletec.medicine.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
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
    public Integer getVersion() throws IOException {
	org.springframework.core.io.Resource resource = new ClassPathResource("/config.properties");
	Properties properties = PropertiesLoaderUtils.loadProperties(resource);
	String ver = properties.getProperty("databaseVersion");
	return Integer.valueOf(ver);
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

    @Override
    public Integer updateVersion() throws IOException {
	org.springframework.core.io.Resource resource = new ClassPathResource("/config.properties");
	Properties properties = PropertiesLoaderUtils.loadProperties(resource);
	Integer ver = Integer.valueOf(properties.getProperty("databaseVersion"));
	ver++;
	properties.setProperty("databaseVersion", ver+"");
	FileOutputStream fos = new FileOutputStream(resource.getFile());
	properties.store(fos, null);
	return ver;
    }

}
