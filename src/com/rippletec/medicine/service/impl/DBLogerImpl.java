package com.rippletec.medicine.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Repository;

import com.rippletec.medicine.bean.DBLogEntity;
import com.rippletec.medicine.dao.DBLogDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.ServiceException;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.DBLog;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.ChineseMedicineManager;
import com.rippletec.medicine.service.DBLoger;
import com.rippletec.medicine.service.MedicineTypeManager;
import com.rippletec.medicine.service.WestMedicineManager;
import com.rippletec.medicine.utils.ErrorCode;
import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.app.ChineseMedicineVO;
import com.rippletec.medicine.vo.app.WestMedicineVO;

@Repository(DBLoger.NAME)
public class DBLogerImpl extends BaseManager<DBLog> implements DBLoger{
    
    @Resource(name = DBLogDao.NAME)
    private DBLogDao dbLogDao;
    
    @Resource(name = ChineseMedicineManager.NAME)
    private ChineseMedicineManager chineseMedicineManager;
    
    @Resource(name = WestMedicineManager.NAME)
    private WestMedicineManager westMedicineManager;
    
    @Resource(name = MedicineTypeManager.NAME)
    private MedicineTypeManager medicineTypeManager;

    @Override
    protected FindAndSearchDao<DBLog> getDao() {
	return this.dbLogDao;
    }

    @Override
    public Integer getVersion() throws ServiceException{
	org.springframework.core.io.Resource resource = new ClassPathResource("/config.properties");
	Properties properties = null;
	try {
	    properties = PropertiesLoaderUtils.loadProperties(resource);
	} catch (IOException e) {
	    Logger.getLogger(DBLogerImpl.class).error(StringUtil.getLoggerInfo(ErrorCode.FILE_REDA_ERROR, "读取数据库版本失败!"));
	    throw new ServiceException(ErrorCode.INTENAL_ERROR);
	}
	String ver = properties.getProperty("databaseVersion");
	return Integer.valueOf(ver);
    }

    @Override
    public Integer uniqueSave(DBLog dbLog) throws DaoException {
	List<DBLog> dbLogs;
	Integer id = -1;
	try {
	    dbLogs = findByParam(DBLog.OBJCET_ID, dbLog.getObject_id());
	    DBLog old = dbLogs.get(0);
	    old.setAction(dbLog.getAction());
	    old.setDate(dbLog.getDate());
	    old.setDbTable(dbLog.getDbTable());
	    old.setDbVersion(dbLog.getDbVersion());
	    update(old);
	    id = old.getId();
	} catch (DaoException e) {
	    id = save(dbLog);
	}	    
	return id;
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

    @Override
    public List<DBLog> getDatas(int type, int version, int serverVersion) throws DaoException {
	return dbLogDao.getModifiedData(type, version, serverVersion);
    }

    @Override
    public List<DBLogEntity> getUpdates(int version, int serverVersion) throws DaoException {
	List<DBLog> logs = dbLogDao.getModifiedData(DBLog.ACTION_UPDATE, version, serverVersion);
	List<DBLogEntity> dbLogEntities = new ArrayList<DBLogEntity>();
	for (DBLog dbLog : logs) {
	    Object object = null;
	    String dbTable = dbLog.getDbTable();
	    int object_id = dbLog.getObject_id();
	    if (dbTable.equals(ChineseMedicine.TABLE_NAME))
		object = new ChineseMedicineVO(chineseMedicineManager.find(object_id));
	    else if(dbTable.equals(WestMedicine.TABLE_NAME))
		object = new WestMedicineVO(westMedicineManager.find(object_id));
	    else if (dbTable.equals(MedicineType.TABLE_NAME))
		object =  medicineTypeManager.find(object_id);
	    DBLogEntity dbLogEntity = new DBLogEntity(dbTable,object); 
	    dbLogEntities.add(dbLogEntity);
	}
	return dbLogEntities;
    }

    @Override
    public List<DBLogEntity> getDeletes(int version, int serverVersion) throws DaoException {
	List<DBLog> logs = dbLogDao.getModifiedData(DBLog.ACTION_DELETE, version, serverVersion);
	List<DBLogEntity> dbLogEntities = new ArrayList<DBLogEntity>();
	for (DBLog dbLog : logs) {
	    DBLogEntity dbLogEntity = new DBLogEntity(dbLog.getDbTable(),dbLog.getObject_id()); 
	    dbLogEntities.add(dbLogEntity);
	}
	return dbLogEntities;	
    }

    @Override
    public List<DBLogEntity> getSaves(int version, int serverVersion) throws DaoException {
	List<DBLog> logs = dbLogDao.getModifiedData(DBLog.ACTION_SAVE, version, serverVersion);
	List<DBLogEntity> dbLogEntities = new ArrayList<DBLogEntity>();
	for (DBLog dbLog : logs) {
	    Object object = null;
	    String dbTable = dbLog.getDbTable();
	    int object_id = dbLog.getObject_id();
	    if (dbTable.equals(ChineseMedicine.TABLE_NAME))
		object = new ChineseMedicineVO(chineseMedicineManager.find(object_id));
	    else if(dbTable.equals(WestMedicine.TABLE_NAME))
		object = new WestMedicineVO(westMedicineManager.find(object_id));
	    else if (dbTable.equals(MedicineType.TABLE_NAME))
		object = medicineTypeManager.find(object_id);
	    DBLogEntity dbLogEntity = new DBLogEntity(dbTable,object); 
	    dbLogEntities.add(dbLogEntity);
	}
	return dbLogEntities;
    }



    
}
