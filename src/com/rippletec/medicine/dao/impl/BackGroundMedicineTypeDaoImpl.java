package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.BackGroundMedicineTypeDao;
import com.rippletec.medicine.model.BackGroundMedicineType;

@Repository(BackGroundMedicineTypeDao.NAME)
public class BackGroundMedicineTypeDaoImpl extends BaseDaoImpl<BackGroundMedicineType> implements
	BackGroundMedicineTypeDao {

    @Override
    public String getClassName() {
	return BackGroundMedicineType.CLASS_NAME;
    }

    @Override
    public Class<BackGroundMedicineType> getPersistClass() {
	return BackGroundMedicineType.class;
    }

}
