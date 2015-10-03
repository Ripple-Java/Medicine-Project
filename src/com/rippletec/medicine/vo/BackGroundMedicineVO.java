package com.rippletec.medicine.vo;

import com.rippletec.medicine.model.BackGroundMedicineType;

public class BackGroundMedicineVO <T>{
    
    public static final String CLASS_NAME = "BackGroundMedicineVO";
    
    public T entity;
    
    public BackGroundMedicineType type;
    
    public BackGroundMedicineVO() {
    }
    public BackGroundMedicineVO(T medicine, BackGroundMedicineType type) {
	super();
	this.entity = medicine;
	this.type = type;
    }


    public BackGroundMedicineType getType() {
        return type;
    }

    public void setType(BackGroundMedicineType type) {
        this.type = type;
    }
    public T getEntity() {
        return entity;
    }
    public void setEntity(T entity) {
        this.entity = entity;
    }
    @Override
    public String toString() {
	return "BackGroundMedicineVO [entity=" + entity + ", type=" + type
		+ "]";
    }
    
    
  
    
    
    

}
