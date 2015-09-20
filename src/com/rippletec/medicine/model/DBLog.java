package com.rippletec.medicine.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

@Repository
@Entity
@Table(name=DBLog.TABLE_NAME)
public class DBLog extends BaseModel{
    
    private static final long serialVersionUID = -6565450137834133467L;
    public static final String TABLE_NAME = "db_log";
    public static final String CLASS_NAME = "DBLog";
    
    public static final String DB_VERSION = "db_version";
    public static final String ACTION = "action";
    public static final String OBJCET_ID = "object_id";
    
    public static final int ACTION_DELETE = 3;
    public static final int ACTION_UPDATE = 2;
    public static final int ACTION_SAVE = 1;
    
    
    public DBLog() {
    }
    
    
    

    public DBLog(Integer object_id, Integer dbVersion, String dbTable,
	    Integer action, Date date) {
	super();
	this.object_id = object_id;
	this.dbVersion = dbVersion;
	this.dbTable = dbTable;
	this.action = action;
	this.date = date;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    //对象id
    @Column(name=OBJCET_ID, nullable = false)
    private Integer object_id;
    
    //版本号
    @Column(name=DB_VERSION, length=12, nullable=false)
    private Integer dbVersion;
    
    //对象表
    @Column(name = "DB_table", length = 50, nullable = false)
    private String dbTable;
    
    //对象操作
    @Column(name=ACTION, length =1 ,nullable=false)
    private Integer action;
    
    //最后修改日期
    @Column(name="date",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    




    @Override
    public String toString() {
	return "DBLog [id=" + id + ", object_id=" + object_id + ", dbVersion="
		+ dbVersion + ", dbTable=" + dbTable + ", action=" + action
		+ ", date=" + date + "]";
    }


    public Integer getId() {
        return id;
    }


    public Integer getObject_id() {
        return object_id;
    }



    public Integer getAction() {
        return action;
    }


    public Date getDate() {
        return date;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public void setObject_id(Integer object_id) {
        this.object_id = object_id;
    }




    public void setAction(Integer action) {
        this.action = action;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public Integer getDbVersion() {
        return dbVersion;
    }


    public void setDbVersion(Integer dbVersion) {
        this.dbVersion = dbVersion;
    }


    public String getDbTable() {
        return dbTable;
    }


    public void setDbTable(String dbTable) {
        this.dbTable = dbTable;
    }
    
    
   
    

   
    

}
