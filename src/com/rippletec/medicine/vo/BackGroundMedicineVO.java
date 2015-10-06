package com.rippletec.medicine.vo;

import com.rippletec.medicine.model.BackGroundMedicineType;
import com.rippletec.medicine.model.BaseModel;

public class BackGroundMedicineVO {
    
    public static final String CLASS_NAME = "BackGroundMedicineVO";
    
    public BackGroundMedicineType type;
    
    public String name;
    
    public String enterpriseName;
    
    public Integer id;
    
    public BackGroundMedicineVO(BackGroundMedicineType type, String name,
	    String enterpriseName, int id) {
	super();
	this.type = type;
	this.name = name;
	this.enterpriseName = enterpriseName;
	this.id = id;
    }

    public BackGroundMedicineVO() {
    }

    public BackGroundMedicineType getType() {
        return type;
    }

    public void setType(BackGroundMedicineType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
 
    

}
