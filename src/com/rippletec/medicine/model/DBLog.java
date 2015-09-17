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
    
    public static final String VERSION = "version";
    public static final String ACTION = "action";
    
    public static final int TYPE_UPDATE = 2;
    public static final int TYPE_DELETE = 3;
    public static final int TYPE_SAVE = 1;
    
    
    public DBLog() {
    }
    
    
    

    public DBLog(Integer version, Integer type, String action, Date date) {
	super();
	this.version = version;
	this.type = type;
	this.action = action;
	this.date = date;
    }




    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name=VERSION, length=255, nullable=false)
    private Integer version;
    
    @Column(name = "type", length = 1, nullable = false)
    private Integer type;
    
    @Column(name=ACTION,columnDefinition="TEXT",nullable=false)
    private String action;
    
    @Column(name="date",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    

    

    @Override
    public String toString() {
	return "DBLog [id=" + id + ", version=" + version + ", type=" + type
		+ ", action=" + action + ", date=" + date + "]";
    }


    public Integer getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getAction() {
        return action;
    }

    public Date getDate() {
        return date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Integer getType() {
        return type;
    }


    public void setType(Integer type) {
        this.type = type;
    }
    
    	
    

}
