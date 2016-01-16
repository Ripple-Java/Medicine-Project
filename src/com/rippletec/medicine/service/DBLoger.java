package com.rippletec.medicine.service;

import java.io.IOException;
import java.util.List;

import com.rippletec.medicine.bean.DBLogEntity;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.DBLog;

public interface DBLoger extends IManager<DBLog>{
    
    public static final String NAME = "DBLoger";
    
    public static final int DEFAULT_VERSION = 99999;

    Integer getVersion() throws IOException;
    
    Integer updateVersion() throws IOException;
    
    Integer uniqueSave(DBLog dbLog);

    List<DBLog> getDatas(int type, int version, int serverVersion);
    
    List<DBLogEntity> getUpdates(int version, int serverVersion) throws DaoException;
    
    List<DBLogEntity> getDeletes(int version, int serverVersion);
    
    List<DBLogEntity> getSaves(int version, int serverVersion) throws DaoException;
   
}
