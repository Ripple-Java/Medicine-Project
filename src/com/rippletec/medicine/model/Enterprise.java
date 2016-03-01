/**
 * 
 */
package com.rippletec.medicine.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.web.EnterpriseInfoVO;

/**
 * 企业信息Model
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name = Enterprise.TABLE_NAME)
public class Enterprise extends BaseModel {

    protected static final long serialVersionUID = -2404813734314317948L;
    
    public static final String CLASS_NAME = "Enterprise";
    public static final String TABLE_NAME = "enterprise";
    public static final String NAME = "name";
    public static final String LOGO = "logo";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String TYPE = "type";
    public static final String ID = "id";
    
    public static final int ON_PUBLISTH = 1;
    public static final int ON_CHECKING = 2;
    public static final int ON_RECHECKING = 3;
    public static final int ON_CLOSE = 4;
    public static final int ON_VAlIDATING = 5;
    
    public static final String USER_ID = "user_id";
    public static final String STATUS = "status";
    
    /**
     * 外资
     */
    public static final int FOREIGN = 1;
    /**
     * 合资
     */
    public static final int JOINT = 2;    
    /**
     * 内资
     */
    public static final int DOMESTIC = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    // 关联企业分类表
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enterprise")
    @Cascade(CascadeType.ALL)
    protected Set<EnterpriseMedicineType> medicineTypeEnterprises = new LinkedHashSet<EnterpriseMedicineType>();
    
    // 关联企业视频
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enterprise")
    @Cascade(CascadeType.ALL)
    protected Set<Video> videos = new LinkedHashSet<Video>();
    
    // 关联企业视频
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enterprise")
    @Cascade(CascadeType.ALL)
    protected Set<Meeting> meetings = new LinkedHashSet<Meeting>();
    
    // 关联企业审核数据
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enterprise")
    @Cascade(CascadeType.ALL)
    protected Set<CheckData> checkDatas = new LinkedHashSet<CheckData>();
    
    // 关联企业账号
    @OneToOne(fetch=FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = USER_ID)
    protected User user;

    // 企业类型type：1表示外资，2表示合资，0表示内资
    @Column(name = "type", length = 1, nullable = false)
    protected Integer type;

    // 企业名称
    @Column(name = "name", length = 100, nullable = false)
    protected String name;

    // 企业logo
    @Column(name = "logo", length = 255, nullable = true)
    protected String logo;

    // 企业联系电话,可为null
    @Column(name = "phone", length = 100, nullable = true)
    protected String phone;

    // 企业邮箱,可为null
    @Column(name = "email", length = 100, nullable = true)
    protected String email;
    
    // 企业主页
    @Column(name="enterpriseUrl", length=100, nullable=true)
    protected String enterpriseUrl;
    
    //企业执照号
    @Column(name = "enterpriseNumber", length=15, nullable=false)
    protected String enterpriseNumber;
    
    //企业简介
    @Column(name = "content", columnDefinition="TEXT", nullable=true)
    protected String content;
    
    //企业执照审核图片
    @Column(name = "checkImg", length=200 , nullable=false)
    protected String checkImg;
    
    @Column(name = STATUS, length = 1, nullable=false)
    protected Integer status;
    

    public Enterprise() {
    }
    
    public Enterprise(Integer type, String name, String enterpriseNumber, String enterpriseUrl, String checkImg, Integer status) {
	super();
	this.type = type;
	this.name = name;
	this.enterpriseNumber = enterpriseNumber;
	this.enterpriseUrl = enterpriseUrl;
	this.checkImg = checkImg;
	this.status = status;
    }
    
    

    public void setUpdate(EnterpriseInfoVO vo) {
	    this.content = vo.getContent();
	    this.enterpriseUrl = vo.getEnterpriseUrl();
	    this.phone = vo.getPhone();
    }
    
    

    public String getEnterpriseUrl() {
        return enterpriseUrl;
    }

    public void setEnterpriseUrl(String enterpriseUrl) {
        this.enterpriseUrl = enterpriseUrl;
    }

    public String getEmail() {
	return email;
    }

    public Integer getId() {
	return id;
    }

    public String getLogo() {
	return logo;
    }

    public Set<EnterpriseMedicineType> getMedicineTypeEnterprises() {
	return medicineTypeEnterprises;
    }

    public String getName() {
	return name;
    }

    public String getPhone() {
	return phone;
    }

    public Integer getType() {
	return type;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public void setLogo(String logo) {
	this.logo = logo;
    }

    public void setMedicineTypeEnterprises(
	    Set<EnterpriseMedicineType> medicineTypeEnterprises) {
	this.medicineTypeEnterprises = medicineTypeEnterprises;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public void setType(Integer type) {
	this.type = type;
    }
    

    public Set<Video> getVideos() {
        return videos;
    }



    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }
    
    
    

    public String getEnterpriseNumber() {
        return enterpriseNumber;
    }

    public void setEnterpriseNumber(String enterpriseNumber) {
        this.enterpriseNumber = enterpriseNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
    

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }

    public Integer getStatus() {
        return status;
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

    public Set<CheckData> getCheckDatas() {
        return checkDatas;
    }

    public void setCheckDatas(Set<CheckData> checkDatas) {
        this.checkDatas = checkDatas;
    }

    public String getCheckImg() {
        return checkImg;
    }

    public void setCheckImg(String checkImg) {
        this.checkImg = checkImg;
    }

    @Override
    public String toString() {
	return "Enterprise [id=" + id + ", type=" + type + ", name=" + name
		+ ", logo=" + logo + ", phone=" + phone + ", email=" + email
		+ "]";
    }

 

}
