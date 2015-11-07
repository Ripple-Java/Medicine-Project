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

@Repository
@Entity
@Table(name = "user")
public class User extends BaseModel {

    private static final long serialVersionUID = -1649785273161092567L;

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
    private int id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cascade(CascadeType.ALL)
    private Set<Student> students = new HashSet<Student>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cascade(CascadeType.ALL)
    private Set<Doctor> doctors = new HashSet<Doctor>();
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cascade(CascadeType.ALL)
    private Set<Liveness> livenesses = new HashSet<Liveness>();
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cascade(CascadeType.ALL)
    private Set<FeedBackMass> feedBackMasses = new HashSet<FeedBackMass>();

    // 用户密码
    @Column(name = "password", length = 65, nullable = false)
    private String password;

    // 用户账号
    @Column(name = "account", length = 30, nullable = false)
    private String account;


    // 用户类型（0代表普通用户，1代表学生，2代表医师，3代表企业，4代表管理员）
    @Column(name = "type", length = 10, nullable = false)
    private int type;

    // 性别（0代表女，1代表男）
    @Column(name = "sex", length = 1, nullable = true)
    private int sex;

    // 生日
    @Column(name = "birthday", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date birthday;

    // 电话
    @Column(name = "phone", length = 50, nullable = true)
    private String cellphone;

    // 邮箱
    @Column(name = "email", length = 255, nullable = true)
    private String email;

    // 学历（0空，1小学，2初中，3高中，4专科，5本科，6硕士，7博士）
    @Column(name = "degree", length = 1, nullable = true)
    private int degree;


    // 用户头像
    @Column(name = "certificateImg", length = 255, nullable = true)
    private String certificateImg;

    // 注册时间
    @Column(name = "regeditTime", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date regeditTime;

    // 最后一次登录设备
    @Column(name = "device", length = 1, nullable = true)
    private Integer device;

    // 最后一次登录时间
    @Column(name = "lastLogin", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    
    // 用户状态
    @Column(length=1, nullable=false)
    private int status;

    public User() {
	super();
    }

    public User(String password, String account, int type,
	    String cellphone, String certificateImg, Date regeditTime) {
	super();
	this.password = password;
	this.account = account;
	this.type = type;
	this.cellphone = cellphone;
	this.certificateImg = certificateImg;
	this.regeditTime = regeditTime;
    }
    
    

    public User(String password, String account, int type, String email,
	    Date regeditTime, int status) {
	super();
	this.password = password;
	this.account = account;
	this.type = type;
	this.email = email;
	this.regeditTime = regeditTime;
	this.status = status;
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
    
    

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
	return "User [id=" + id + ", password=" + password + ", type=" + type + ", sex=" + sex + ", birthday="
		+ birthday + ", cellphone=" + cellphone + ", email=" + email
		+ ", degree=" + degree +", certificateImg=" + certificateImg + ", regeditTime="
		+ regeditTime + "]";
    }

}
