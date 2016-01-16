package com.rippletec.medicine.service;

import java.util.List;

import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.UserFavorite;

public interface UserFavoriteManager extends IManager<UserFavorite> {
    
    public static final String NAME = "UserFavoriteManager";
    
    Result addUserFavorite(String account,UserFavorite userFavorite) throws DaoException;

    List<UserFavorite> findByAccount(String account);

    List<UserFavorite> getMedicineFavorites(User user);

}
