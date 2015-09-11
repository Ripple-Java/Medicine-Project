package com.rippletec.medicine.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.rippletec.medicine.model.User;

public interface UserManager extends IManager<User> {
    public static final String NAME = "UserManager";

    User findByAccount(String acount);
    
    int getCount();

    boolean isExist(String phoneNumber);

    boolean isLogined(String acount, HttpSession httpSession);

    boolean register(String phoneNumber, String password);

    boolean updatePassword(String account, String password);

    void updateUserInfo(String account, int sex, Date birthday, int degree,
	    String email);

    boolean userLogin(String account, String password, HttpSession httpSession);

}