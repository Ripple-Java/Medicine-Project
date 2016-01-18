package com.rippletec.medicine.dao;

import java.util.List;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.DBLog;

public interface DBLogDao extends FindAndSearchDao<DBLog> {
    
    public static final String NAME = "DBLogDao";
    
    int getCount();
    
    List<DBLog> getModifiedData(int type , int version, int serverVersion) throws DaoException;

}
