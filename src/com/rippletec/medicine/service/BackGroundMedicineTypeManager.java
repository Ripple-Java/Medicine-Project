package com.rippletec.medicine.service;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.BackGroundMedicineType;

public interface BackGroundMedicineTypeManager extends IManager<BackGroundMedicineType> {
    
    public static final String NAME = "BackGroundMedicineTypeManager";
    
    Integer uniqueSave(BackGroundMedicineType backGroundMedicineType) throws DaoException;

}
