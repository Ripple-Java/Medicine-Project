package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.UserFavoriteDao;
import com.rippletec.medicine.model.UserFavorite;
import com.rippletec.medicine.service.UserFavoriteManager;


@Service(UserFavoriteManager.NAME)
public class UserFavoriteManagerImpl extends BaseManager<UserFavorite> implements UserFavoriteManager{
    
    @Resource(name = UserFavoriteDao.NAME)
    private UserFavoriteDao userFavoriteDao;

    @Override
    protected FindAndSearchDao<UserFavorite> getDao() {
	return this.userFavoriteDao;
    }
    
   
}
    
   
