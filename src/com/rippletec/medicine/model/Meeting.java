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


@Entity
@Repository
@Table(name = Meeting.TABLE_NAME)
public class Meeting extends BaseModel {

    private static final long serialVersionUID = 3239548865591119786L;
    
    public static final String TABLE_NAME = "meeting";
    public static final String CLASS_NAME = "Meeting";
    
    public static final String ENTERPRISE_ID = "enterprise_id";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = ENTERPRISE_ID)
    private Enterprise enterprise;
    
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    
    @Column(name = "speaker", length = 20, nullable =false)
    private String speaker;
    
    @Column(name = "tag", length = 255, nullable = false)
    private String tag;
    
    @Column(name = "date",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(name = "PPT", length = 200, nullable = false)
    private String PPT;
    
    @Column(name = "video", length = 255, nullable = false)
    private String video;
    
    public Meeting() {
    }

    public Meeting(Enterprise enterprise, String name, String speaker,
	    String tag, Date date, String pPT, String video) {
	super();
	this.enterprise = enterprise;
	this.name = name;
	this.speaker = speaker;
	this.tag = tag;
	this.date = date;
	PPT = pPT;
	this.video = video;
    }

    @Override
    public String toString() {
	return "Meeting [id=" + id + ", name=" + name + ", speaker=" + speaker
		+ ", tag=" + tag + ", date=" + date + ", PPT=" + PPT
		+ ", video=" + video + "]";
    }

    public Integer getId() {
        return id;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public String getName() {
        return name;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getTag() {
        return tag;
    }

    public Date getDate() {
        return date;
    }

    public String getPPT() {
        return PPT;
    }

    public String getVideo() {
        return video;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPPT(String pPT) {
        PPT = pPT;
    }

    public void setVideo(String video) {
        this.video = video;
    }
    
    
}
