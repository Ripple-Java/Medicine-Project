package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.EnterNewsDao;
import com.rippletec.medicine.model.EnterNews;


@Repository(EnterNewsDao.NAME)
public class EnterNewsDaoImpl extends BaseDaoImpl<EnterNews> implements EnterNewsDao{

    @Override
    public String getClassName() {
	return EnterNews.CLASS_NAME;
    }

    @Override
    public Class<EnterNews> getPersistClass() {
	return EnterNews.class;
    }
    

}
