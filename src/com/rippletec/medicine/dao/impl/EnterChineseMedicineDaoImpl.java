package com.rippletec.medicine.dao.impl;

import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.model.EnterChineseMedicine;

public class EnterChineseMedicineDaoImpl extends BaseDaoImpl<EnterChineseMedicine> implements EnterChineseMedicineDao{

    @Override
    public String getClassName() {
	return EnterChineseMedicine.CLASS_NAME;
    }

    @Override
    public Class<EnterChineseMedicine> getPersistClass() {
	return EnterChineseMedicine.class;
    }

}
