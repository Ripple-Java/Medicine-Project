package com.rippletec.medicine.service;

import java.util.List;

import com.rippletec.medicine.model.UserFavorite;

public interface UserFavoriteManager extends IManager<UserFavorite> {
    
    public static final String NAME = "UserFavoriteManager";
    
    boolean addUserFavorite(String account,UserFavorite userFavorite);

    List<UserFavorite> findByAccount(String account);

}
