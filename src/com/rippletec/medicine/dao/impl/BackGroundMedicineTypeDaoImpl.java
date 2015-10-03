package com.rippletec.medicine.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Repository;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.BackGroundMedicineTypeDao;
import com.rippletec.medicine.model.BackGroundMedicineType;

@Repository(BackGroundMedicineTypeDao.NAME)
public class BackGroundMedicineTypeDaoImpl extends BaseDaoImpl<BackGroundMedicineType> implements
	BackGroundMedicineTypeDao {

    @Override
    public String getClassName() {
	return BackGroundMedicineType.ClASS_NAME;
    }

    @Override
    public Class<BackGroundMedicineType> getPersistClass() {
	return BackGroundMedicineType.class;
    }

}
