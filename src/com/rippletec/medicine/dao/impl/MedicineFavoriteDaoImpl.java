package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.MedicineFavoriteDao;
import com.rippletec.medicine.model.MedicineFavorite;

@Repository(MedicineFavoriteDao.NAME)
public class MedicineFavoriteDaoImpl extends BaseDaoImpl<MedicineFavorite> implements MedicineFavoriteDao{

    @Override
    public String getClassName() {
	return MedicineFavorite.CLASS_NAME;
    }

    @Override
    public Class<MedicineFavorite> getPersistClass() {
	return MedicineFavorite.class;
    }

}
