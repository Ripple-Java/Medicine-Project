package com.rippletec.medicine.model;

import java.net.Inet4Address;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

/**
 * @author Liuyi
 *
 */
@Repository
@Entity
@Table(name=BackGroundMedicineType.TABLE_NAME)
public class BackGroundMedicineType extends BaseModel{

    private static final long serialVersionUID = 6287473661450061632L;
    
    public static final String CLASS_NAME = "BackGroundMedicineType";

    public static final String TABLE_NAME = "background_medicinetype";
    
    public static final int TYPE_NORMAL = 1;
    
    public static final int  TYPE_ENTERPRISE = 2;
    
    public BackGroundMedicineType() {
    }
    
    
    public BackGroundMedicineType(MedicineType firsType, MedicineType secondType, MedicineType thirdType, MedicineType forthtType) {
	super();
	this.type = TYPE_NORMAL;
	this.firstType = firsType.getName();
	this.firstType_id = firsType.getId();
	this.secondType = secondType.getName();
	this.secondType_id = secondType.getId();
	this.thirdType = thirdType.getName();
	this.thirdType_id = thirdType.getId();
	this.forthType = forthtType.getName();
	this.forthType_id = forthtType.getId();
    }
    

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length=1, nullable=false)
    private Integer type;
    
    @Column(length=200, nullable=true)
    private String firstType;
    @Column(nullable=true)
    private Integer firstType_id;
    
    @Column(length=200, nullable=true)
    private String secondType;
    @Column(nullable=true)
    private Integer secondType_id;
    
    @Column(length=200, nullable=true)
    private String thirdType;
    @Column(nullable=true)
    private Integer thirdType_id;
    
    @Column(length=200, nullable=true)
    private String forthType;
    @Column(nullable=true)
    private Integer forthType_id;  

    @Override
    public String toString() {
	return "BackGroundMedicineType [id=" + id + ", firstType=" + firstType
		+ ", firstType_id=" + firstType_id + ", secondType="
		+ secondType + ", secondType_id=" + secondType_id
		+ ", thirdTyep=" + thirdType+ ", thirdType_id=" + thirdType_id
		+ ", forthType=" + forthType + ", forthType_id=" + forthType_id
		+ "]";
    }
    public Integer getId() {
        return id;
    }
    public String getFirstType() {
        return firstType;
    }
    public Integer getFirstType_id() {
        return firstType_id;
    }
    public String getSecondType() {
        return secondType;
    }
    public Integer getSecondType_id() {
        return secondType_id;
    }
    public String getThirdType() {
        return thirdType;
    }
    public Integer getThirdType_id() {
        return thirdType_id;
    }
    public String getForthType() {
        return forthType;
    }
    public Integer getForthType_id() {
        return forthType_id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setFirstType(String firstType) {
        this.firstType = firstType;
    }
    public void setFirstType_id(Integer firstType_id) {
        this.firstType_id = firstType_id;
    }
    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }
    public void setSecondType_id(Integer secondType_id) {
        this.secondType_id = secondType_id;
    }
    public void setThirdTyep(String thirdType) {
        this.thirdType = thirdType;
    }
    public void setThirdType_id(Integer thirdType_id) {
        this.thirdType_id = thirdType_id;
    }
    public void setForthType(String forthType) {
        this.forthType = forthType;
    }
    public void setForthType_id(Integer forthType_id) {
        this.forthType_id = forthType_id;
    }


    public Integer getType() {
        return type;
    }


    public void setType(Integer type) {
        this.type = type;
    }


    public void setMedicineType(int index, int type_id, String typeString) {
	switch (index) {
	case 1:
	    this.firstType = typeString;
	    this.firstType_id = type_id;
	    break;
	    
	case 2:
	    this.secondType = typeString;
	    this.secondType_id = type_id;
	    break;
	    
	case 3:
	    this.thirdType = typeString;
	    this.thirdType_id = type_id;
	    break;

	case 4:
	    this.forthType = typeString;
	    this.forthType_id = type_id;
	    break;
	    
	default:
	    break;
	}
    }
    
    @Override
    public boolean equals(Object obj) {
	if(obj == null)
	    return false;
	if(obj instanceof BackGroundMedicineType){
	    BackGroundMedicineType bType = (BackGroundMedicineType)obj;
	    if(this.firstType_id.equals(bType.getFirstType_id()) && this.secondType_id.equals(bType.getSecondType_id()) &&
	       this.thirdType_id.equals(bType.getThirdType_id()) && this.forthType_id.equals(bType.getForthType_id()) &&
	       this.type.equals(bType.getType()))
		return true;
	}
	
	return false;
    }
    
    
    
}
