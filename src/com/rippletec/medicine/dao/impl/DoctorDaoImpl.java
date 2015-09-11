package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.DoctorDao;
import com.rippletec.medicine.model.Doctor;

@Repository(DoctorDao.NAME)
public class DoctorDaoImpl extends BaseDaoImpl<Doctor> implements DoctorDao{

	@Override
	public String getClassName() {
		return Doctor.CLASS_NAME;
	}

	@Override
	public Class<Doctor> getPersistClass() {
		return Doctor.class;
	}

}
