package com.rippletec.medicine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Repository;


/**
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name=Student.TABLE_NAME)
public class Student extends BaseModel{
	
	private static final long serialVersionUID = 579248047375931809L;
	
	public static final String CLASS_NAME="Student";
	public static final String TABLE_NAME = "student";
	public static final String USER_ID = "user_id";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = USER_ID)
	private User user;
	
	//姓名
	@Column(name="name",length=100, nullable=false)
	private String name;
	
	@Column(name="school",length=255,nullable=true)
	private String school;
	
	@Column(name="major",length=255,nullable=true)
	private String major;
	
	
	
	public Student() {
		super();
	}



	public Student(User user, String name, String school, String major) {
	    super();
	    this.user = user;
	    this.name = name;
	    this.school = school;
	    this.major = major;
	}
	
	

	public Integer getId() {
	    return id;
	}

	public String getMajor() {
		return major;
	}

	public String getName() {
	    return name;
	}
	public String getSchool() {
		return school;
	}
	public User getUser() {
	    return user;
	}
	public void setId(Integer id) {
	    this.id = id;
	}
	

	public void setMajor(String major) {
		this.major = major;
	}

	public void setName(String name) {
	    this.name = name;
	}
	
	

	public void setSchool(String school) {
		this.school = school;
	}

	public void setUser(User user) {
	    this.user = user;
	}

	@Override
	public String toString() {
		return "Student [userId=" + ", school=" + school + ", major="
				+ major + "]";
	}
	
}
