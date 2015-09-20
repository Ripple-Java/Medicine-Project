package com.rippletec.medicine.service;

import com.rippletec.medicine.model.DBLog;

public interface DBLoger extends IManager<DBLog>{
    
    public static final String NAME = "DBLoger";
    
    public static final int DEFAULT_VERSION = 100000;

    Integer getVersion();
    
    Integer uniqueSave(DBLog dbLog);

}
