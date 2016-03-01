package com.rippletec.medicine.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用户Model,包括普通用户，管理员，和企业用户
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name = "user")
public class User extends BaseModel {

    protected static final long serialVersionUID = -1649785273161092567L;

    public static final String CLASS_NAME = "User";
    public static final String TABLE_NAME = "user";

    public static final int SEX_MAN = 1;
    public static final int SEX_WOMAN = 0;
    public static final int TYPE_USER = 0;
    public static final int TYPE_STU = 1;
    public static final int TYPE_DOC = 2;
    public static final int TYPE_ENTER = 3;
    public static final int TYPE_ADMIN = 4;

    public static final String STUDENT_ID = "student_id";
    public static final String DOCTOR_ID = "doctor_id";
    public static final String ACCOUNT = "account";
    public static final String TYPE = "type";
    public static final String STATUS = "status";
    public static final String REGEDITTIME = "regeditTime";
    public static final String LASTLOGIN = "lastLogin";
    public static final String NAME = "name";
    public static final String IS_LOGINED = "is_logined";

    public static final int DRVICE_ANDROID = 1;
    public static final int DRVICE_IPHONE = 2;
    public static final int DRVICE_OTHER = 0;
    
    public static final int STATUS_NORMAL = 1;
    public static final int STATUS_VAlIDATING = 2;
    public static final int STATUS_CHECKING = 4;
    public static final int STATUS_BLOCKED = 3;

    public static final String DEFAULT_CERTIFICATEIMG = "images/defaultUser.png";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cascade(CascadeType.ALL)
    protected Set<Student> students = new HashSet<Student>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cascade(CascadeType.ALL)
    protected Set<Doctor> doctors = new HashSet<Doctor>();
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cascade(CascadeType.ALL)
    protected Set<Liveness> livenesses = new HashSet<Liveness>();
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cascade(CascadeType.ALL)
    protected Set<FeedBackMass> feedBackMasses = new HashSet<FeedBackMass>();

    // 用户密码
    @Column(name = "password", length = 65, nullable = false)
    protected String password;

    // 用户账号
    @Column(name = "account", length = 30, nullable = false)
    protected String account;
    
    @Column(name = "name", length = 256, nullable = false)
    protected String name;


    // 用户类型（0代表普通用户，1代表学生，2代表医师，3代表企业，4代表管理员）
    @Column(name = "type", length = 10, nullable = false)
    protected int type;

    // 性别（0代表女，1代表男）
    @Column(name = "sex", length = 1, nullable = true)
    protected int sex;

    // 生日
    @Column(name = "birthday", nullable = true)
    @Temporal(TemporalType.DATE)
    protected Date birthday;

    // 电话
    @Column(name = "phone", length = 50, nullable = true)
    protected String cellphone;

    // 邮箱
    @Column(name = "email", length = 255, nullable = true)
    protected String email;

    // 学历（0空，1小学，2初中，3高中，4专科，5本科，6硕士，7博士）
    @Column(name = "degree", length = 1, nullable = true)
    protected int degree;


    // 用户头像
    @Column(name = "certificateImg", length = 255, nullable = true)
    protected String certificateImg;

    // 注册时间
    @Column(name = "regeditTime", nullable = false)
    @Temporal(TemporalType.DATE)
    protected Date regeditTime;

    // 最后一次登录设备
    @Column(name = "device", length = 1, nullable = true)
    protected Integer device;
    
    // 最后一次登录设备标识
    @Column(name = "deviceId", length = 100, nullable = true)
    protected String deviceId;

    // 最后一次登录时间
    @Column(name = "lastLogin", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastLogin;
    
    // 用户状态
    @Column(length=1, nullable=false)
    protected int status;
    
    // 最后一次修改用户信息的时间
    @Column(name = "updateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField (format="yyyy-MM-dd HH:mm:ss")  
    protected Date updateTime;

    public User() {
	super();
    }

    public User(String password, String account, String name,int type,
	    String cellphone, String certificateImg, Date regeditTime, Date updateTime) {
	super();
	this.password = password;
	this.account = account;
	this.name = name;
	this.type = type;
	this.cellphone = cellphone;
	this.certificateImg = certificateImg;
	this.regeditTime = regeditTime;
	this.updateTime = updateTime;
    }
    
    

    public User(String password, String account, String name,int type, String email,
	    Date regeditTime,Date updateTime, int status) {
	super();
	this.name = name;
	this.password = password;
	this.account = account;
	this.type = type;
	this.email = email;
	this.regeditTime = regeditTime;
	this.status = status;
	this.updateTime = updateTime;
    }

    public String getAccount() {
	return account;
    }


    public Date getBirthday() {
	return birthday;
    }

    public String getCellphone() {
	return cellphone;
    }

    public String getCertificateImg() {
	return certificateImg;
    }

    public int getDegree() {
	return degree;
    }

    public Set<Doctor> getDoctors() {
	return doctors;
    }

    public String getEmail() {
	return email;
    }

    public int getId() {
	return id;
    }

    public String getPassword() {
	return password;
    }

    public Date getRegeditTime() {
	return regeditTime;
    }

    public int getSex() {
	return sex;
    }

    public Set<Student> getStudents() {
	return students;
    }

    public int getType() {
	return type;
    }

    public void setAccount(String account) {
	this.account = account;
    }

    public void setBirthday(Date birthday) {
	this.birthday = birthday;
    }

    public void setCellphone(String cellphone) {
	this.cellphone = cellphone;
    }

    public void setCertificateImg(String certificateImg) {
	this.certificateImg = certificateImg;
    }

    public void setDegree(int degree) {
	this.degree = degree;
    }

    public void setDoctors(Set<Doctor> doctors) {
	this.doctors = doctors;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public void setId(int id) {
	this.id = id;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public void setRegeditTime(Date regeditTime) {
	this.regeditTime = regeditTime;
    }

    public void setSex(int sex) {
	this.sex = sex;
    }

    public void setStudents(Set<Student> students) {
	this.students = students;
    }

    public void setType(int type) {
	this.type = type;
    }



    public Integer getDevice() {
        return device;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setDevice(Integer device) {
        this.device = device;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
    

    public Set<Liveness> getLivenesses() {
        return livenesses;
    }

    public Set<FeedBackMass> getFeedBackMasses() {
        return feedBackMasses;
    }

    public void setLivenesses(Set<Liveness> livenesses) {
        this.livenesses = livenesses;
    }

    public void setFeedBackMasses(Set<FeedBackMass> feedBackMasses) {
        this.feedBackMasses = feedBackMasses;
    }
    
    
    
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
	return "User [id=" + id + ", password=" + password + ", type=" + type + ", sex=" + sex + ", birthday="
		+ birthday + ", cellphone=" + cellphone + ", email=" + email
		+ ", degree=" + degree +", certificateImg=" + certificateImg + ", regeditTime="
		+ regeditTime + "]";
    }

}
