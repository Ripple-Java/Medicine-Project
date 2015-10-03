package com.rippletec.medicine.service;

import com.rippletec.medicine.model.BackGroundMedicineType;

public interface BackGroundMedicineTypeManager extends IManager<BackGroundMedicineType> {
    
    public static final String NAME = "BackGroundMedicineTypeManager";
    
    Integer uniqueSave(BackGroundMedicineType backGroundMedicineType);

}
