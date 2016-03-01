package com.rippletec.medicine.vo.app;

import com.rippletec.medicine.model.BackGroundMedicineType;

public class EnterTypesVO {
    
    public String firstType;
    
    public int firstTypeID;
    
    public String secondType;
    
    public int secondTypeID;
    
    public EnterTypesVO() {
    }

    public EnterTypesVO(BackGroundMedicineType backGroundMedicineType) {
	super();
	this.firstType = backGroundMedicineType.getSecondType();
	this.firstTypeID = backGroundMedicineType.getSecondType_id();
	this.secondType = backGroundMedicineType.getThirdType();
	this.secondTypeID = backGroundMedicineType.getThirdType_id();
    }



    public String getFirstType() {
        return firstType;
    }

    public int getFirstTypeID() {
        return firstTypeID;
    }

    public String getSecondType() {
        return secondType;
    }

    public int getSecondTypeID() {
        return secondTypeID;
    }

    public void setFirstType(String firstType) {
        this.firstType = firstType;
    }

    public void setFirstTypeID(int firstTypeID) {
        this.firstTypeID = firstTypeID;
    }

    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }

    public void setSecondTypeID(int secondTypeID) {
        this.secondTypeID = secondTypeID;
    }
    
    
}
