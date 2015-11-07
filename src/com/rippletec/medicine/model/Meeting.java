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

import com.rippletec.medicine.vo.web.MeetingVo;


@Entity
@Repository
@Table(name = Meeting.TABLE_NAME)
public class Meeting extends BaseModel {

    protected static final long serialVersionUID = 3239548865591119786L;
    
    public static final String TABLE_NAME = "meeting";
    public static final String CLASS_NAME = "Meeting";
    
    public static final String ENTERPRISE_ID = "enterprise_id";
    public static final String MEDICINE_ID = "medicine_id";
    
    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String SUBJECT = "subject";
    
    public static final int ON_PUBLISTH = 1;
    public static final int ON_CHECKING = 2;
    public static final int ON_RECHECKING = 3;
    public static final int ON_CLOSE = 4;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = ENTERPRISE_ID)
    protected Enterprise enterprise;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = MEDICINE_ID)
    protected Medicine medicine;
    
    @Column(name = "name", length = 50, nullable = false)
    protected String name;
    
    @Column(name = "speaker", length = 20, nullable =true)
    protected String speaker;
    
    @Column(name = "tag", length = 255, nullable = true)
    protected String tag;
    
    @Column(name = "date",nullable = false)
    @Temporal(TemporalType.DATE)
    protected Date date;
    
    @Column(name = "commitDate",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date commitDate;
    
    @Column(name = "PPT", length = 200, nullable = true)
    protected String PPT;
    
    @Column(name = "video", length = 255, nullable = true)
    protected String video;
    
    @Column(name = "content", columnDefinition= "TEXT", nullable =true)
    protected String content;
    
    @Column(name = "subject", length=50, nullable = true)
    protected String subject;
    
    @Column(name = STATUS, length=1, nullable = false)
    protected Integer status;
    
    @Column(name = "enterpriseName", length=50, nullable = true)
    protected String enterpriseName;
    
    @Column(name = "pageUrl", length= 100, nullable=true)
    protected String pageUrl;
    
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
    
    public Meeting(Enterprise enterprise, MeetingVo vo) {
	super();
	this.enterprise = enterprise;
	this.name = vo.getName();
	this.speaker = vo.getSpeaker();
	this.tag = vo.getTag();
	this.date = vo.getDate();
	PPT = vo.getPPT();
	this.video = vo.getVideo();
	this.content = vo.getContent();
	this.subject = vo.getSubject();
	this.enterpriseName = enterprise.getName();
	this.pageUrl = vo.getPageUrl();
    }

    @Override
    public String toString() {
	return "Meeting [id=" + id + ", name=" + name + ", speaker=" + speaker
		+ ", tag=" + tag + ", date=" + date + ", PPT=" + PPT
		+ ", video=" + video + "]";
    }
    
    

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
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

    public Medicine getMedicine() {
        return medicine;
    }

    public String getContent() {
        return content;
    }

    public String getSubject() {
        return subject;
    }

    public Integer getStatus() {
        return status;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    
    
    
    
    
    
}
