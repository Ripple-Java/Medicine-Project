package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.StudentDao;
import com.rippletec.medicine.model.Student;

@Repository(StudentDao.NAME)
public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao{

	@Override
	public String getClassName() {
		return Student.CLASS_NAME;
	}

	@Override
	public Class<Student> getPersistClass() {
		return Student.class;
	}

}
