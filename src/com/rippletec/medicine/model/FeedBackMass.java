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
import org.springframework.stereotype.Repository;

@Repository
@Entity
@Table(name=FeedBackMass.TABLE_NAME)
public class FeedBackMass extends BaseModel {

    private static final long serialVersionUID = 8388753053148224238L;
    
    public static final String CLASSE_NAME = "FeedBackMass";
    public static final String TABLE_NAME = "feedbackmass";
    
    public static final String USER_ID = "user_id";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="content",columnDefinition="TEXT", nullable=false)
    private String content;
    
    @Column(name="time",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name=USER_ID)
    private User user;
    
    @Column(name="status", length=1, nullable=false)
    private Integer status;
    
    @Column(name="phone", length=20, nullable=true)
    private String phone;
    
    @Column(name="qqNumber", length=15, nullable=true)
    private String qqNumber;
    
    
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
