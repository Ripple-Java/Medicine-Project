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

import com.rippletec.medicine.utils.StringUtil;
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
    public static final String ID = "id";


    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String SUBJECT_ID = "subject_id";
    public static final String MODIFYTIME = "modifyTime";
    
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = SUBJECT_ID)
    protected Subject subject;
    
    @Column(name = "name", length = 50, nullable = false)
    protected String name;
    
    @Column(name = "speaker", length = 20, nullable =true)
    protected String speaker;
    
    
    @Column(name = "date",nullable = false)
    @Temporal(TemporalType.DATE)
    protected Date date;
    
    @Column(name = "commitDate",nullable = false)
    @Temporal(TemporalType.DATE)
    protected Date commitDate;
    
    @Column(name = "PPT", length = 200, nullable = true)
    protected String PPT;
    
    @Column(name = "video", length = 255, nullable = true)
    protected String video;
    
    @Column(name = "content", columnDefinition= "TEXT", nullable =true)
    protected String content;
    
    
    @Column(name = STATUS, length=1, nullable = false)
    protected Integer status;
    
    @Column(name = "enterpriseName", length=50, nullable = true)
    protected String enterpriseName;
    
    @Column(name = "pageUrl", length= 100, nullable=true)
    protected String pageUrl;
    
    @Column(name = MODIFYTIME, nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyTime;
    
    public Meeting() {
    }

    public Meeting(Enterprise enterprise, String name, String speaker, Date date, String pPT, String video) {
	super();
	this.enterprise = enterprise;
	this.name = name;
	this.speaker = speaker;
	this.date = date;
	PPT = pPT;
	this.video = video;
    }
    
    public Meeting(Enterprise enterprise, MeetingVo vo, Subject subject) {
	super();
	this.enterprise = enterprise;
	this.name = vo.getName();
	this.speaker = vo.getSpeaker();
	this.date = vo.getDate();
	this.content = vo.getContent();
	this.subject = subject;
	this.enterpriseName = enterprise.getName();
	this.pageUrl = vo.getPageUrl();
	this.modifyTime = new Date();
    }
    
    public Meeting(Meeting meeting) {
	this.commitDate = meeting.getCommitDate();
	this.content = meeting.getContent();
	this.date = meeting.getDate();
	this.enterprise = meeting.getEnterprise();
	this.enterpriseName = meeting.getEnterpriseName();
	this.id = meeting.getId();
	this.medicine = meeting.getMedicine();
	this.modifyTime = meeting.getModifyTime();
	this.name = meeting.getName();
	this.pageUrl = meeting.getPageUrl();
	this.PPT = meeting.getPPT();
	this.speaker = meeting.getSpeaker();
	this.status = meeting.getStatus();
	this.subject = meeting.getSubject();
	this.video = meeting.getVideo();
    }

    
    public void setUpdate(MeetingVo vo, Subject subject) {
	this.name = vo.getName();
	this.speaker = vo.getSpeaker();
	this.date = vo.getDate();
	PPT = vo.getPPT();
	this.video = vo.getVideo();
	this.content = vo.getContent();
	this.pageUrl = vo.getPageUrl();
	this.subject = subject;
    }
    

    @Override
    public String toString() {
	return "Meeting [id=" + id + ", name=" + name + ", speaker=" + speaker
		+ ", date=" + date + ", PPT=" + PPT
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


    public Integer getStatus() {
        return status;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    
    
    
    
    
    
}
