package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.model.EnterChineseMedicine;

@Repository(EnterChineseMedicineDao.NAME)
public class EnterChineseMedicineDaoImpl extends BaseDaoImpl<EnterChineseMedicine> implements EnterChineseMedicineDao{

    @Override
    public String getClassName() {
	return EnterChineseMedicine.CLASS_NAME;
    }

    @Override
    public Class<EnterChineseMedicine> getPersistClass() {
	return EnterChineseMedicine.class;
    }

    @Override
    public EnterChineseMedicine findByMedicineId(Integer id) {
	return findBySql(EnterChineseMedicine.TABLE_NAME, EnterChineseMedicine.MEDICINE_ID, id).get(0);
    }

}
