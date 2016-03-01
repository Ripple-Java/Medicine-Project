package com.rippletec.medicine.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;



/**
 * 审核信息Model类
 * @author Liuyi
 *
 */
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
    public static final int TYPE_MEDICINE_CHINESE = 2;
    public static final int TYPE_MEDICINE_WEST = 3;
    public static final int TYPE_MEETING = 4;
    public static final int TYPE_VIDEO = 5;
  
    public static final String ENTERPRISE_ID = "enterpriese_id";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    
    
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(0)
    private Integer id;
    
    @Column(name = "objectName", nullable = false)
    private String objectName;
    
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
    
    // 关联企业
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = ENTERPRISE_ID)
    private Enterprise enterprise;
    
    
    public CheckData(Enterprise enterprise ,String objectName,Integer object_id, Integer type, String path,
	    Date uploadDate, Integer status) {
	super();
	this.object_id = object_id;
	this.objectName = objectName; 
	this.type = type;
	this.path = path;
	this.uploadDate = uploadDate;
	this.status = status;
	this.enterprise = enterprise;
    }

    public CheckData() {
    }

 
    @Override
    public String toString() {
	return "CheckData [id=" + id + ", object_id=" + object_id + ", type="
		+ type + ", path=" + path + ", uploadDate=" + uploadDate
		+ ", status=" + status + "]";
    }
    
    


    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
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

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
    
    
    
    
    
    
    
}
