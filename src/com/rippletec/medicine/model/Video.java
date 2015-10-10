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
import javax.security.auth.Subject;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;


@Entity
@Repository
@Table(name = Video.TABLE_NAME)
public class Video extends BaseModel {
    
    private static final long serialVersionUID = -4889812268996211983L;
    public static final String CLASS_NAME = "Video";
    public static final String TABLE_NAME = "video";
    
    public static final String ENTERPRISE_ID = "enterprise_id";
    public static final String NAME = "name";
    public static final String PASS_DATE = "passDate";
    public static final String STATUS = "status";
    
    public static final int ON_PUBLISTH = 1;
    public static final int ON_CHECKING = 2;
    public static final int ON_RECHECKING = 3;
    public static final int ON_CLOSE = 4;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name", length=255, nullable = false)
    private String name;
    
    @Column(name = "path", length = 255, nullable = false)
    private String path;
    
    @Column(name = "speaker", length = 40, nullable = true)
    private String speaker;
    
    @Column(name = "subject", length = 40, nullable = true)
    private String subject;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = ENTERPRISE_ID)
    private Enterprise enterprise;
    
    @Column(name="length",nullable = false)
    private Integer length;
    
    @Column(name=STATUS,nullable=false)
    private Integer status;
    
    @Column(name=PASS_DATE,nullable=false)
    private Date passDate;
    
    public Video() {
    }
 
    public Video(String name, String path, String speaker, String subject,
	    Enterprise enterprise, Integer length, Integer status, Date passDate) {
	super();
	this.name = name;
	this.path = path;
	this.speaker = speaker;
	this.subject = subject;
	this.enterprise = enterprise;
	this.length = length;
	this.status = status;
	this.passDate = passDate;
    }


    @Override
    public String toString() {
	return "Video [id=" + id + ", name=" + name + ", path=" + path
		+ ", length=" + length + "]";
    }

    public Integer getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public Integer getLength() {
        return length;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }



    public String getSpeaker() {
        return speaker;
    }



    public String getSubject() {
        return subject;
    }



    public Date getPassDate() {
        return passDate;
    }



    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }



    public void setSubject(String subject) {
        this.subject = subject;
    }



    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }
    
    
    

}
