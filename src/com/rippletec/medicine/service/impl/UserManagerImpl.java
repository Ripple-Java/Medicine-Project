package com.rippletec.medicine.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Id;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.CheckDataDao;
import com.rippletec.medicine.dao.EnterpriseDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.UserDao;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.LivenessManager;
import com.rippletec.medicine.service.UserManager;
import com.rippletec.medicine.utils.EmailUtil;
import com.rippletec.medicine.utils.MD5Util;
import com.rippletec.medicine.utils.StringUtil;

@Service(UserManager.NAME)
public class UserManagerImpl extends BaseManager<User> implements UserManager{

    @Resource(name=UserDao.NAME)
    private UserDao userDao;
    @Resource(name = LivenessManager.NAME)
    private LivenessManager livenessManager;
    
    @Resource(name=EnterpriseDao.NAME)
    private EnterpriseDao enterpriseDao;
    
    @Resource(name=CheckDataDao.NAME)
    private CheckDataDao checkDataDao;
     
    @Override
    public User findByAccount(String account) {
	List<User> users = userDao.findByPage(User.ACCOUNT, account, new PageBean(0, 100));
	if (users != null && users.size() > 0)
	    return users.get(0);
	return null;
	
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
    public boolean isLogined(HttpSession httpSession) {
	Object accountAttr = httpSession.getAttribute(User.ACCOUNT);
	if(accountAttr == null)
	    return false;
	String sessionAc = (String) accountAttr;
	return StringUtil.hasText(sessionAc);
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
	user.setStatus(User.STATUS_NORMAL);
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

    @Override
    public boolean getBackPassword(String account, String newPassword) {
	User user = findByAccount(account);
	String securityPassword = "";
	if (user == null)
	    return false;
	try {   	
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
    public boolean adminLogin(String account, String password,
	    HttpSession httpSession) {
	User user  = findByAccount(account);
	if(user == null)
	   return false;
	String dbPassword = user.getPassword();
	try {
	    if(MD5Util.validPasswd(password, dbPassword)){
		httpSession.setAttribute(User.ACCOUNT, account);
		httpSession.setAttribute(User.TYPE, user.getType());
		return true;
	    }
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    e.printStackTrace();
	    return false;
	}
	return false;
    }

    @Override
    public boolean verifyPassword(String account, String oldPassword) {
	User user = findByAccount(account);
	if(user != null){
	    String dbPassword = user.getPassword();
	    try {
		return MD5Util.validPasswd(oldPassword, dbPassword);
	    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
		e.printStackTrace();
		return false;
	    }
	    
	}
	return false;
    }

    @Override
    public boolean registerEnterprise(String email, String password,
	    HttpSession httpSession) {
	String securityPassword = "";
	try {
	    securityPassword = MD5Util.getEncryptedPwd(password);
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    e.printStackTrace();
	    return false;
	}
	Date registerDate = new Date();
	User user = new User(securityPassword, email, User.TYPE_ENTER, email, registerDate, User.STATUS_VAlIDATING);
	Enterprise enterprise = (Enterprise) httpSession.getAttribute(Enterprise.CLASS_NAME);
	CheckData checkData = (CheckData) httpSession.getAttribute(CheckData.CLASS_NAME);
	enterprise.setUser(user);
	int enterprise_id = enterpriseDao.save(enterprise);
	checkData.setObject_id(enterprise_id);
	checkDataDao.save(checkData);
	if(!EmailUtil.sendEmail(email, StringUtil.RegisterContent(email, securityPassword), "医药汇注册验证"))
	    return false;
	return true;
    }

    @Override
    public void activeUser(String account) {
	User user = findByAccount(account);
	user.setStatus(User.STATUS_NORMAL);
	userDao.update(user);
    }

    @Override
    public boolean initPassword(String account) {
	String newPassword = StringUtil.generateCode(8);
	String securityword = "";
	try {
	    securityword = MD5Util.getEncryptedPwd(newPassword);
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    e.printStackTrace();
	    return false;
	}
	User user = findByAccount(account);
	if(user == null )
	    return false;
	user.setPassword(securityword);
	userDao.update(user);
	return true;
    }

    @Override
    public void validUser(String account) {
	User user = findByAccount(account);
	user.setStatus(User.STATUS_CHECKING);
	userDao.update(user);
    }
    
}
