package com.rippletec.medicine.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.User;

public interface UserManager extends IManager<User> {
    public static final String NAME = "UserManager";

    User findByAccount(String acount);

    boolean isExist(String phoneNumber);

    boolean isLogined( HttpSession httpSession);

    boolean register(String phoneNumber, String password) throws DaoException;

    boolean updatePassword(String account, String oldpassword, String newPassword);

    void updateUserInfo(String account, String name, int sex, Date birthday, int degree,
	    String email);


    boolean userLogin(String account, String password, Integer device,
	    HttpSession httpSession);

    boolean loginOut(String account, HttpSession httpSession);

    boolean getBackPassword(String account, String newPassword);

    Result adminLogin(String account, String password, HttpSession httpSession);

    boolean verifyPassword(String account, String oldPassword);

    boolean registerEnterprise(String email, String password,
	    HttpSession httpSession);

    void activeUser(String account);

    boolean initPassword(String account);

    void validUser(String account);

    Result appUserLogin(String account, String password, int device,
	    String deviceId, HttpSession httpSession);
    
    List<User> getNormalUser(final String param, final List<Object> values, final PageBean pBean, String orderStr);

    Result block(int id) throws DaoException;

    Result unblock(int id) throws DaoException;

    boolean loginOutEnterprise(String account, HttpSession httpSession);


}
