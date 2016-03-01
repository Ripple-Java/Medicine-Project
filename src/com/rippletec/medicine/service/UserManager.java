package com.rippletec.medicine.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.ServiceException;
import com.rippletec.medicine.model.User;

public interface UserManager extends IManager<User> {
    public static final String NAME = "UserManager";

    User findByAccount(String acount) throws DaoException;

    boolean isExist(String phoneNumber);

    boolean isLogined( HttpSession httpSession);

    void register(String phoneNumber, String password) throws DaoException, ServiceException;

    void updatePassword(String account, String oldpassword, String newPassword) throws DaoException, ServiceException;

    void updateUserInfo(String account, String name, int sex, Date birthday, int degree,
	    String email) throws DaoException;


    void userLogin(String account, String password, Integer device,
	    HttpSession httpSession) throws ServiceException, DaoException;

    void loginOut(String account, HttpSession httpSession) throws DaoException;

    void getBackPassword(String account, String newPassword) throws DaoException, ServiceException;

    void adminLogin(String account, String password, HttpSession httpSession) throws ServiceException, DaoException;

    boolean verifyPassword(String account, String oldPassword) throws DaoException, ServiceException;

    void registerEnterprise(String email, String password,
	    HttpSession httpSession) throws DaoException, ServiceException;

    void activeUser(String account) throws DaoException;

    void initPassword(String account) throws ServiceException, DaoException;

    void validUser(String account) throws DaoException;

    void appUserLogin(String account, String password, int device,
	    String deviceId, HttpSession httpSession) throws DaoException, ServiceException;
    
    List<User> getNormalUser(final String param, final List<Object> values, final PageBean pBean, String orderStr) throws ServiceException, DaoException;

    void block(int id) throws DaoException;

    void unblock(int id) throws DaoException;

    void loginOutEnterprise(String account, HttpSession httpSession) throws DaoException;


}
