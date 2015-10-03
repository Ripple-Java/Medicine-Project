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
@Table(name  = EnterNews.TABLE_NAME)
public class EnterNews extends BaseModel {

    private static final long serialVersionUID = -7272069046229112374L;
    
    public static final String TABLE_NAME = "enternews";
    public static final String CLASS_NAME = "EnterNews";
    
    public static final String ENTERPRISE_ID = "enterprise_id";
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = ENTERPRISE_ID)
    private Enterprise enterprise;
    
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;
    
    @Column(name = "viewCount", nullable= false)
    private Integer viewCount;
    
    public EnterNews() {
    }

    public EnterNews(Enterprise enterprise, Date date, String name,
	    String content, Integer viewCount) {
	super();
	this.enterprise = enterprise;
	this.date = date;
	this.name = name;
	this.content = content;
	this.viewCount = viewCount;
    }

    @Override
    public String toString() {
	return "EnterNews [id=" + id + ", date=" + date + ", name=" + name
		+ ", content=" + content + ", viewCount=" + viewCount + "]";
    }

    public Integer getId() {
        return id;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
    
    
    
    

}
