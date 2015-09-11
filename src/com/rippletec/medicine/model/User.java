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
@Table(name="user")
public class User extends BaseModel {

	private static final long serialVersionUID = -1649785273161092567L;
	
	public static final String CLASS_NAME="User";
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
	
	public static final String DEFAULT_CERTIFICATEIMG = "/certificateImg/default.png";
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="user")
	@Cascade(CascadeType.ALL)
	private Set<Student> students = new HashSet<Student>();
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="user")
	@Cascade(CascadeType.ALL)
	private Set<Doctor> doctors = new HashSet<Doctor>();
	
	//用户密码
	@Column(name = "password",length=60, nullable= false)
	private String password;
	
	//用户账号
	@Column(name="account",length=11,nullable=false)
	private String account;
	
	//用户名
	@Column(name = "username", length = 255, nullable = false)
	private String username;
	
	
	//用户类型（0代表普通用户，1代表学生，2代表医师，3代表企业，4代表管理员）
	@Column(name = "type", length=10, nullable=false)
	private int type;
	
	//性别（0代表女，1代表男）
	@Column(name = "sex", length=1, nullable=true)
	private int sex;
	
	//生日
	@Column(name = "birthday", nullable = true)
	private Date birthday;
	
	//电话
	@Column(name = "cellphone", length=50, nullable=false)
	private String cellphone;
	
	//邮箱
	@Column(name = "email", length = 255, nullable = true)
	private String email;
	
	//学历（0代表小学，1代表初中，2代表高中，3代表专科，4代表本科，5代表硕士，6代表博士）
	@Column(name = "degree", length=1, nullable = true)
	private int degree;
	
	//
	@Column(name = "avatar", length = 255, nullable = true)
	private String avatar;
	
	//用户头像
	@Column(name = "certificateImg", length = 255, nullable = true)
	private String certificateImg;
	
	//注册时间
	@Column(name = "regeditTime", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date regeditTime;
	
	public User() {
		super();
	}
	
	public User(String password,String username,String account, int type,
		String cellphone, String certificateImg, Date regeditTime) {
	    super();
	    this.password = password;
	    this.username = username;
	    this.account = account;
	    this.type = type;
	    this.cellphone = cellphone;
	    this.certificateImg = certificateImg;
	    this.regeditTime = regeditTime;
	}



	public String getAccount() {
	    return account;
	}
	public String getAvatar() {
		return avatar;
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
	public String getUsername() {
		return username;
	}
	public void setAccount(String account) {
	    this.account = account;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
	    return "User [id=" + id + ", password=" + password + 
		    ", username=" + username + ", type=" + type + ", sex=" + sex + ", birthday="
		    + birthday + ", cellphone=" + cellphone + ", email="
		    + email + ", degree=" + degree + ", avatar=" + avatar
		    + ", certificateImg=" + certificateImg + ", regeditTime="
		    + regeditTime + "]";
	}
	
	

	
}
