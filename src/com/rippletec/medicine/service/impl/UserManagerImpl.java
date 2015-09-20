package com.rippletec.medicine.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.UserDao;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.LivenessManager;
import com.rippletec.medicine.service.UserManager;
import com.rippletec.medicine.utils.MD5Util;

@Service(UserManager.NAME)
public class UserManagerImpl extends BaseManager<User> implements UserManager{

    @Resource(name=UserDao.NAME)
    private UserDao userDao;
    @Resource(name = LivenessManager.NAME)
    private LivenessManager livenessManager;
    
    @Override
    public User findByAccount(String account) {
	List<User> users = userDao.findByPage(User.ACCOUNT, account, new PageBean(0, 100));
	if (users != null && users.size() > 0)
	    return users.get(0);
	return null;
	
    }
    
    @Override
    public int getCount() {
	return userDao.getCount();
    }

    @Override
    protected FindAndSearchDao<User> getDao() {
	return this.userDao;
    }

    @Override
    public boolean isExist(String phoneNumber) {
	 return findByPage(User.ACCOUNT, phoneNumber, new PageBean(0, 10)).size() > 0 ? true : false; 
    }

    @Override
    public boolean isLogined(String account, HttpSession httpSession) {
	String sessionAc = (String) httpSession.getAttribute(User.ACCOUNT);
	if(sessionAc != null && account.equals(sessionAc))
	    return true;
	return false;
    }

    @Override
    public boolean register(String phoneNumber, String password) {
	String securityPassword = "";
	try {
	    securityPassword = MD5Util.getEncryptedPwd(password);
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    return false;
	}
	User user = new User(securityPassword,phoneNumber, User.TYPE_USER, phoneNumber, User.DEFAULT_CERTIFICATEIMG, new Date());
	user.setBirthday(null);
	user.setDegree(0);
	user.setEmail(null);
	user.setSex(0);
	int userid = userDao.save(user);
	livenessManager.save(find(userid));
	return true;
    }

    @Override
    public boolean updatePassword(String account, String oldPassword,String newPassword) {
	 User user = findByAccount(account);
	 String securityPassword = "";
	try {   
		if (user == null || !MD5Util.validPasswd(oldPassword, user.getPassword()))
		    return false;
	    securityPassword  = MD5Util.getEncryptedPwd(newPassword);
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    e.printStackTrace();
	    return false;
	}
	user.setPassword(securityPassword);
	userDao.update(user);
	return true;
    }

    @Override
    public void updateUserInfo(String account, int sex, Date birthday,
	    int degree, String email) {
	User user = findByAccount(account);
	if(user == null)
	    return;
	user.setSex(sex);
	user.setBirthday(birthday);
	user.setDegree(degree);
	user.setEmail(email);
	userDao.update(user);
    }

    @Override
    public boolean userLogin(String account, String password, Integer device,
	    HttpSession httpSession) {
	User user = findByAccount(account);
	if(user != null){
	    String securityPassword = user.getPassword();
	    try {
		if(MD5Util.validPasswd(password, securityPassword)){
		    Date time = new Date();
		    user.setLastLogin(time);
		    switch (device) {
		    case User.DRVICE_ANDROID: user.setDevice(User.DRVICE_ANDROID);break;
		    case User.DRVICE_IPHONE:  user.setDevice(User.DRVICE_IPHONE);break;
		    default: user.setDevice(User.DRVICE_OTHER);
			break;
		    }
		    update(user);
		    livenessManager.updateLogin(user, time);
		    httpSession.setAttribute(User.ACCOUNT, account);
		    httpSession.setAttribute(User.TYPE, User.TYPE_USER);
		    return true;
		}
	    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
		e.printStackTrace();
		return false;
	    }
	}
	   
	return false;
    }

    @Override
    public boolean loginOut(String account, HttpSession httpSession) {
	httpSession.removeAttribute(User.ACCOUNT);
	httpSession.removeAttribute(User.TYPE);
	return true;
    }
    
}
