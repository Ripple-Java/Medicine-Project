package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.UserFavoriteDao;
import com.rippletec.medicine.model.UserFavorite;

@Repository(UserFavoriteDao.NAME)
public class UserFavoriteDaoImpl extends BaseDaoImpl<UserFavorite> implements UserFavoriteDao{

    @Override
    public String getClassName() {
	return UserFavorite.CLASS_NAME;
    }

    @Override
    public Class<UserFavorite> getPersistClass() {
	return UserFavorite.class;
    }

}
