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

@Repository
@Entity
@Table(name = Doctor.TABLE_NAME)
public class Doctor  extends BaseModel{
	
    	private static final long serialVersionUID = -8661762262076840236L;
	public static final String CLASS_NAME="Doctor";
	public static final String TABLE_NAME = "doctor";
	
	public static final String USER_ID = "user_id";
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = USER_ID)
	private User user;
	
	//姓名
	@Column(name="name",length=100, nullable=false)
	private String name;
	
	//医院名称
	@Column(name="hospital", length=255, nullable=false)
	private String hospital;
	
	//科室
	@Column(name="office",length=255,nullable=false)
	private String office;
	
	//职称
	@Column(name="profession", length=255,nullable=false)
	private String profession;
	
	//科室号码
	@Column(name="officePhone",length=255,nullable=false)
	private String officePhone;
	
	
	
	
	public Doctor() {
		super();
	}




	public Doctor(User user, String name, String hospital, String office,
		String profession, String officePhone) {
	    super();
	    this.user = user;
	    this.name = name;
	    this.hospital = hospital;
	    this.office = office;
	    this.profession = profession;
	    this.officePhone = officePhone;
	}
	
	


	public String getHospital() {
		return hospital;
	}


	public Integer getId() {
	    return id;
	}


	public String getName() {
	    return name;
	}


	public String getOffice() {
		return office;
	}
	public String getOfficePhone() {
		return officePhone;
	}
	public String getProfession() {
		return profession;
	}
	public User getUser() {
	    return user;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public void setId(Integer id) {
	    this.id = id;
	}
	public void setName(String name) {
	    this.name = name;
	}
	public void setOffice(String office) {
		this.office = office;
	}


	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}


	public void setProfession(String profession) {
		this.profession = profession;
	}


	public void setUser(User user) {
	    this.user = user;
	}


	@Override
	public String toString() {
	    return "Doctor [id=" + id + ", hospital=" + hospital + ", office="
		    + office + ", profession=" + profession + ", officePhone="
		    + officePhone + "]";
	}

	
	
	
}
