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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Repository;

/**
 * 用户反馈Model
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name=FeedBackMass.TABLE_NAME)
public class FeedBackMass extends BaseModel {

    protected static final long serialVersionUID = 8388753053148224238L;
    
    public static final String CLASS_NAME = "FeedBackMass";
    public static final String TABLE_NAME = "feedbackmass";
    
    public static final String USER_ID = "user_id";
    public static final String STATUS = "status";
    public static final int  READED = 1;
    public static final int  NO_READ = 2;
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Integer id;
    
    @Column(name="content",columnDefinition="TEXT", nullable=false)
    @NotEmpty
    protected String content;
    
    @Column(name="time",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date time;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name=USER_ID)
    protected User user;
    
    @Column(name="status", length=1, nullable=false)
    protected Integer status;
    
    @Column(name="phone", length=20, nullable=true)
    protected String phone;
    
    @Column(name="qqNumber", length=15, nullable=true)
    protected String qqNumber;
    
    
    public FeedBackMass() {
    }


    public FeedBackMass(String content, Date time, User user, Integer status, String phone, String qqNumber) {
	super();
	this.content = content;
	this.time = time;
	this.user = user;
	this.status = status;
	this.phone = phone;
	this.qqNumber = qqNumber;
    }


    

    @Override
    public String toString() {
	return "FeedBackMass [id=" + id + ", content=" + content + ", time="
		+ time + ", status=" + status + ", phone=" + phone
		+ ", qqNumber=" + qqNumber + "]";
    }


    public Integer getId() {
        return id;
    }


    public Date getTime() {
        return time;
    }


    public User getUser() {
        return user;
    }


    public Integer getStatus() {
        return status;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public void setTime(Date time) {
        this.time = time;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getPhone() {
	    return phone;
	}


	public String getQqNumber() {
	    return qqNumber;
	}


	public void setPhone(String phone) {
	    this.phone = phone;
	}


	public void setQqNumber(String qqNumber) {
	    this.qqNumber = qqNumber;
	}



}
