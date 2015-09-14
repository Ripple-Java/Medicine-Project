package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.dao.EnterWestMedicineDao;
import com.rippletec.medicine.model.EnterWestMedicine;

@Repository(EnterWestMedicineDao.NAME)
public class EnterWestMedicineDaoImpl extends BaseDaoImpl<EnterWestMedicine> implements EnterWestMedicineDao{

    @Override
    public String getClassName() {
	return EnterWestMedicine.CLASS_NAME;
    }

    @Override
    public Class<EnterWestMedicine> getPersistClass() {
	return EnterWestMedicine.class;
    }

}
