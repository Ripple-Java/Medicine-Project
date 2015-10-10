package com.rippletec.medicine.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.rippletec.medicine.model.User;

public interface UserManager extends IManager<User> {
    public static final String NAME = "UserManager";

    User findByAccount(String acount);
    
    int getCount();

    boolean isExist(String phoneNumber);

    boolean isLogined( HttpSession httpSession);

    boolean register(String phoneNumber, String password);

    boolean updatePassword(String account, String oldpassword, String newPassword);

    void updateUserInfo(String account, int sex, Date birthday, int degree,
	    String email);


    boolean userLogin(String account, String password, Integer device,
	    HttpSession httpSession);

    boolean loginOut(String account, HttpSession httpSession);

    boolean getBackPassword(String account, String newPassword);

    boolean adminLogin(String account, String password, HttpSession httpSession);

    boolean verifyPassword(String account, String oldPassword);

    boolean registerEnterprise(String email, String password,
	    HttpSession httpSession);

    void activeUser(String account);

    boolean initPassword(String account);

    void validUser(String account);


}
