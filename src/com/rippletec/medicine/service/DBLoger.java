package com.rippletec.medicine.service;

import java.io.IOException;

import com.rippletec.medicine.model.DBLog;

public interface DBLoger extends IManager<DBLog>{
    
    public static final String NAME = "DBLoger";

    Integer getVersion() throws IOException;
    
    Integer updateVersion() throws IOException;
    
    Integer uniqueSave(DBLog dbLog);

}
