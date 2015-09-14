package com.rippletec.medicine.dao.impl;

import com.rippletec.medicine.dao.MedicineFavoriteDao;
import com.rippletec.medicine.model.MedicineFavorite;

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
