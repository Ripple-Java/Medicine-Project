package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.SubjectDao;
import com.rippletec.medicine.model.Subject;

@Repository(SubjectDao.NAME)
public class SubjectDaoImpl extends BaseDaoImpl<Subject> implements SubjectDao{

    @Override
    public String getClassName() {
	return Subject.CLASS_NAME;
    }

    @Override
    public Class<Subject> getPersistClass() {
	return Subject.class;
    }
    

}
