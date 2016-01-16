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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.web.VideoVO;


/**
 * 医药视频信息Mdoel
 * @author Liuyi
 *
 */
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
    public static final String ID = "id";
    public static final String MODIFYTIME = "modifyTime";
    
    public static final int ON_PUBLISTH = 1;
    public static final int ON_CHECKING = 2;
    public static final int ON_RECHECKING = 3;
    public static final int ON_CLOSE = 4;
    public static final String SUBJECT_ID = "subject_id";
    private static final String ENTERPRISE_NAME = "enterpriseName";
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name", length=255, nullable = false)
    private String name;
    
    @Column(name = "path", length = 255, nullable = true)
    private String path;
    
    @Column(name = "speaker", length = 40, nullable = true)
    private String speaker;
    
    @Column(name = "pageUrl", length = 100, nullable = false)
    private String pageUrl;
    
    @Column(name = "imgUrl", length = 100, nullable = true)
    private String imgUrl;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = ENTERPRISE_ID)
    private Enterprise enterprise;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = SUBJECT_ID)
    private Subject subject;
    
    @Column(name="length",nullable = true)
    private Integer length;
    
    @Column(name=STATUS,nullable=false)
    private Integer status;
    
    @Column(name=PASS_DATE,nullable=false)
    private Date passDate;
    
    @Column(name=MODIFYTIME,nullable=false)
    private Date modifyTime;
    
    @Column(name = ENTERPRISE_NAME, nullable=false)
    private String enterpriseName;
    
    public Video() {
    }
    
    public void setUpdate(VideoVO video, Subject subject) {
	this.subject = subject;
	this.name = video.getName();
	this.pageUrl = video.getPageUrl();
	this.speaker = video.getSpeaker();

    }
    
    public Video(Enterprise enterprise, VideoVO video, Subject subject, Integer status) {
	this.enterprise = enterprise;
	this.enterpriseName = enterprise.getName();
	this.subject = subject;
	this.speaker = video.getSpeaker();
	this.pageUrl = video.getPageUrl();
	this.name = video.getName();
	this.status = status;
	this.modifyTime = new Date();
    }
 
    public Video(String name, String path, String speaker, Subject subject,
	    Enterprise enterprise, Integer length, Integer status, Date passDate, String pageUrl, String imgUrl) {
	super();
	this.name = name;
	this.path = path;
	this.speaker = speaker;
	this.subject = subject;
	this.enterprise = enterprise;
	this.enterpriseName = enterprise.getName();
	this.length = length;
	this.status = status;
	this.passDate = passDate;
	this.pageUrl = pageUrl;
	this.imgUrl = imgUrl;
    }


    @Override
    public String toString() {
	return "Video [id=" + id + ", name=" + name + ", path=" + path
		+ ", length=" + length + "]";
    }
    
    

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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



    public Date getPassDate() {
        return passDate;
    }



    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }



    public void setPassDate(Date passDate) {
        this.passDate = passDate;
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

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    
    
    
    
    

}
