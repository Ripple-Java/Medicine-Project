package com.rippletec.medicine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "path", length = 255, nullable = false)
    private String path;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = ENTERPRISE_ID)
    private Enterprise enterprise;
    
    @Column(name="length",nullable = false)
    private Integer length;
    
    public Video() {
    }

    public Video(String path, Enterprise enterprise, Integer length) {
	super();
	this.path = path;
	this.enterprise = enterprise;
	this.length = length;
    }

    @Override
    public String toString() {
	return "Video [id=" + id + ", path=" + path + ", length=" + length
		+ "]";
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
    
    
    

}
