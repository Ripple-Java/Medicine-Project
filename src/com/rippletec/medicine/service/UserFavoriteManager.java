package com.rippletec.medicine.service;

import java.util.List;

import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.ServiceException;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.UserFavorite;

public interface UserFavoriteManager extends IManager<UserFavorite> {
    
    public static final String NAME = "UserFavoriteManager";
    
    int addUserFavorite(String account,UserFavorite userFavorite) throws DaoException, ServiceException;

    List<UserFavorite> findByAccount(String account) throws DaoException;

    List<UserFavorite> getMedicineFavorites(User user) throws DaoException;

    boolean isExist(Integer type, Integer objectId, User user);

}
