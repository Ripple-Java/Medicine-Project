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
@Table(name = CheckData.TABLE_NAME)
public class CheckData extends BaseModel {

    private static final long serialVersionUID = -2672091910137983730L;
    
    public static final String CLASS_NAME = "CheckData";
    public static final String TABLE_NAME = "checkdata";
    
    public static final int CHECKING = 1;
    public static final int FAILED = 3;
    
    public static final int TYPE_ENTERPRISE = 1;
    public static final int TYPE_MEDICINE = 2;
    public static final int TYPE_MEETING = 3;
    public static final int TYPE_VIDEO = 4;
    
    
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "object_id", nullable = false)
    private Integer object_id;
    
    @Column(name = "type", length = 1, nullable = false)
    private Integer type;
    
    @Column(name = "path", length = 200, nullable = false)
    private String path;

    @Column(name = "uploadDate",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;
    
    @Column(name = "status",length = 1, nullable = false)
    private Integer status;

    
    
    public CheckData(Integer object_id, Integer type, String path,
	    Date uploadDate, Integer status) {
	super();
	this.object_id = object_id;
	this.type = type;
	this.path = path;
	this.uploadDate = uploadDate;
	this.status = status;
    }

    public CheckData() {
    }

 
    @Override
    public String toString() {
	return "CheckData [id=" + id + ", object_id=" + object_id + ", type="
		+ type + ", path=" + path + ", uploadDate=" + uploadDate
		+ ", status=" + status + "]";
    }

    public Integer getId() {
        return id;
    }

    public Integer getObject_id() {
        return object_id;
    }

    public Integer getType() {
        return type;
    }

    public String getPath() {
        return path;
    }


    public Integer getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setObject_id(Integer object_id) {
        this.object_id = object_id;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
    
    
    
    
    
}
