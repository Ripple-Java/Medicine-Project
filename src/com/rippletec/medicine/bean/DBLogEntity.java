package com.rippletec.medicine.bean;

public class DBLogEntity {
    
    public static final String CLASS_NAME = "DBLogEntity";
    
    private String tableName;
    
    private Object entity;
    
    DBLogEntity(){};
    

    public DBLogEntity(String tableName, Object entity) {
	super();
	this.tableName = tableName;
	this.entity = entity;
    }
    

    @Override
    public String toString() {
	return "DBLogEntity [tableName=" + tableName + ", entity=" + entity
		+ "]";
    }


    public String getTableName() {
        return tableName;
    }

    public Object getEntity() {
        return entity;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
    
    

}
