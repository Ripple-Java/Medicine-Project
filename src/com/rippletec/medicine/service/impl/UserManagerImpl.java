package com.rippletec.medicine.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.dao.CheckDataDao;
import com.rippletec.medicine.dao.EnterpriseDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.UserDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.ServiceException;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.service.LivenessManager;
import com.rippletec.medicine.service.UserManager;
import com.rippletec.medicine.utils.EmailUtil;
import com.rippletec.medicine.utils.ErrorCode;
import com.rippletec.medicine.utils.MD5Util;
import com.rippletec.medicine.utils.ParamMap;
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
    public User findByAccount(String account) throws DaoException {
	List<User> users = userDao.findByParam(User.ACCOUNT, account);
	return users.get(0);
	
    }
    
    @Override
    protected FindAndSearchDao<User> getDao() {
	return this.userDao;
    }

    @Override
    public boolean isExist(String phoneNumber) {
	 try {
	    findByPage(User.ACCOUNT, phoneNumber, new PageBean(0, 10));
	} catch (DaoException e) {
	    return false;
	}
	 return true;
    }

    @Override
    public boolean isLogined(HttpSession httpSession) {
	Object accountAttr = httpSession.getAttribute(User.ACCOUNT);
	Object isLoginedAttr = httpSession.getAttribute(User.IS_LOGINED);
	if(accountAttr == null || isLoginedAttr == null)
	    return false;
	String sessionAc = (String) accountAttr;
	int islogined = (int) isLoginedAttr;
	return StringUtil.hasText(sessionAc) && islogined == 1;
    }

    @Override
    public void register(String phoneNumber, String password) throws DaoException, ServiceException {
	String securityPassword = "";
	try {
	    securityPassword = MD5Util.getEncryptedPwd(password);
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    Logger.getLogger(UserManagerImpl.class).error(StringUtil.getLoggerInfo(ErrorCode.INTENAL_ERROR, "register() 密码加密失败"));
	    throw new ServiceException(ErrorCode.INTENAL_ERROR);
	}
	User user = new User(securityPassword, phoneNumber ,phoneNumber, User.TYPE_USER, phoneNumber, User.DEFAULT_CERTIFICATEIMG, new Date(), new Date());
	user.setBirthday(null);
	user.setDegree(0);
	user.setEmail(null);
	user.setSex(0);
	user.setStatus(User.STATUS_NORMAL);
	int userid = userDao.save(user);
	livenessManager.save(find(userid));
    }

    @Override
    public void updatePassword(String account, String oldPassword,String newPassword) throws DaoException, ServiceException {
	User user = findByAccount(account);
	String securityPassword = "";
	try {   
	    if (!MD5Util.validPasswd(oldPassword, user.getPassword())){
		throw new ServiceException(ErrorCode.USER_PASSWORD_ERROR);
	    }
	    securityPassword  = MD5Util.getEncryptedPwd(newPassword);
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    Logger.getLogger(UserManagerImpl.class).error(StringUtil.getLoggerInfo(ErrorCode.INTENAL_ERROR, "updatePassword() 密码加密失败"));
	    throw new ServiceException(ErrorCode.INTENAL_ERROR);
	}
	user.setPassword(securityPassword);
	userDao.update(user);
    }
    

    @Override
    public void updateUserInfo(String account,String name, int sex, Date birthday,
	    int degree, String email) throws DaoException {
	User user = findByAccount(account);
	user.setSex(sex);
	user.setName(name);
	user.setBirthday(birthday);
	user.setDegree(degree);
	user.setEmail(email);
	user.setUpdateTime(new Date());
	userDao.update(user);
    }

    @Override
    public void userLogin(String account, String password, Integer device,
	    HttpSession httpSession) throws ServiceException, DaoException {
	User user = findByAccount(account);
	String securityPassword = user.getPassword();
	try {
	    if (MD5Util.validPasswd(password, securityPassword)) {
		Date time = new Date();
		user.setLastLogin(time);
		switch (device) {
		case User.DRVICE_ANDROID:
		    user.setDevice(User.DRVICE_ANDROID);
		    break;
		case User.DRVICE_IPHONE:
		    user.setDevice(User.DRVICE_IPHONE);
		    break;
		default:
		    user.setDevice(User.DRVICE_OTHER);
		    break;
		}
		update(user);
		livenessManager.updateLogin(user, time);
		httpSession.setAttribute(User.ACCOUNT, account);
		httpSession.setAttribute(User.TYPE, User.TYPE_USER);
		httpSession.setAttribute(User.IS_LOGINED, 1);
	    }else {
		throw new ServiceException(ErrorCode.USER_PASSWORD_ERROR);
	    }
	} 
	catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    Logger.getLogger(UserManagerImpl.class).error(StringUtil.getLoggerInfo(ErrorCode.INTENAL_ERROR, "userLogin() 密码加密失败"));
	    throw new ServiceException(ErrorCode.INTENAL_ERROR);
	}
    }

    @Override
    public void loginOut(String account, HttpSession httpSession) throws DaoException {
	httpSession.removeAttribute(User.ACCOUNT);
	httpSession.removeAttribute(User.TYPE);
	httpSession.removeAttribute(User.IS_LOGINED);
	User user = findByAccount(account);
	user.setDeviceId(null);
	userDao.update(user);
    }

    @Override
    public void getBackPassword(String account, String newPassword) throws DaoException, ServiceException {
	User user = findByAccount(account);
	String securityPassword = "";
	try {   	
	    securityPassword  = MD5Util.getEncryptedPwd(newPassword);
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    Logger.getLogger(UserManagerImpl.class).error(StringUtil.getLoggerInfo(ErrorCode.INTENAL_ERROR, "getBackPassword() 密码加密失败"));
	    throw new ServiceException(ErrorCode.INTENAL_ERROR);
	}
	user.setPassword(securityPassword);
	user.setDeviceId(null);
	userDao.update(user);
    }

    @Override
    public void adminLogin(String account, String password,
	    HttpSession httpSession) throws ServiceException, DaoException {
	ParamMap paramMap = new ParamMap().put(User.ACCOUNT, account)
					  .put(User.STATUS, User.STATUS_NORMAL);
	List<User> users  = userDao.findByParam(paramMap);
	User user = users.get(0);
	String dbPassword = user.getPassword();
	try {
	    if(MD5Util.validPasswd(password, dbPassword)){
		user.setLastLogin(new Date());
		update(user);
		httpSession.setAttribute(User.ACCOUNT, account);
		httpSession.setAttribute(User.TYPE, user.getType());
		httpSession.setAttribute(User.IS_LOGINED, 1);
	    }else {
		throw new ServiceException(ErrorCode.USER_PASSWORD_ERROR);
	    }
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    Logger.getLogger(UserManagerImpl.class).error(StringUtil.getLoggerInfo(ErrorCode.INTENAL_ERROR, "adminLogin() 密码加密失败"));
	    throw new ServiceException(ErrorCode.INTENAL_ERROR);
	}
    }

    @Override
    public boolean verifyPassword(String account, String oldPassword) throws DaoException, ServiceException {
	User user = findByAccount(account);
	String dbPassword = user.getPassword();
	try {
	    return MD5Util.validPasswd(oldPassword, dbPassword);
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    Logger.getLogger(UserManagerImpl.class).error(StringUtil.getLoggerInfo(ErrorCode.INTENAL_ERROR, "verifyPassword() 密码加密失败"));
	    throw new ServiceException(ErrorCode.INTENAL_ERROR);
	}
    }

    @Override
    public void registerEnterprise(String email, String password,
	    HttpSession httpSession) throws DaoException, ServiceException {
	String securityPassword = "";
	try {
	    securityPassword = MD5Util.getEncryptedPwd(password);
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    Logger.getLogger(UserManagerImpl.class).error(StringUtil.getLoggerInfo(ErrorCode.INTENAL_ERROR, "registerEnterprise() 密码加密失败"));
	    throw new ServiceException(ErrorCode.INTENAL_ERROR);
	}
	Date registerDate = new Date();
	Enterprise enterprise = (Enterprise) httpSession.getAttribute(Enterprise.CLASS_NAME);
	User user = new User(securityPassword, email , enterprise.getName(), User.TYPE_ENTER, email, registerDate, new Date(),User.STATUS_VAlIDATING);
	enterprise.setUser(user);
	enterpriseDao.save(enterprise);
	if(!EmailUtil.sendEmail(email, StringUtil.RegisterContent(email, securityPassword), "医药汇注册验证")){
	    throw new ServiceException(ErrorCode.USER_SEDN_EMAIL_ERROR);
	}
    }

    @Override
    public void activeUser(String account) throws DaoException {
	User user = findByAccount(account);
	user.setStatus(User.STATUS_NORMAL);
	userDao.update(user);
    }

    @Override
    public void initPassword(String account) throws ServiceException, DaoException {
	String newPassword = StringUtil.generateCode(8);
	String securityword = "";
	try {
	    securityword = MD5Util.getEncryptedPwd(newPassword);
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    Logger.getLogger(UserManagerImpl.class).error(StringUtil.getLoggerInfo(ErrorCode.INTENAL_ERROR, "initPassword() 密码加密失败"));
	    throw new ServiceException(ErrorCode.INTENAL_ERROR);
	}
	User user = findByAccount(account);
	user.setPassword(securityword);
	userDao.update(user);
    }

    @Override
    public void validUser(String account) throws DaoException {
	User user = findByAccount(account);
	user.setStatus(User.STATUS_CHECKING);
	userDao.update(user);
    }

    @Override
    public void appUserLogin(String account, String password, int device,
	    String deviceId, HttpSession httpSession) throws DaoException, ServiceException {
	User user = findByAccount(account);
	String securityPassword = user.getPassword();
	try {
	    if (MD5Util.validPasswd(password, securityPassword)) {
		Date time = new Date();
		user.setLastLogin(time);
		user.setDeviceId(deviceId);
		switch (device) {
		case User.DRVICE_ANDROID:
		    user.setDevice(User.DRVICE_ANDROID);
		    break;
		case User.DRVICE_IPHONE:
		    user.setDevice(User.DRVICE_IPHONE);
		    break;
		default:
		    user.setDevice(User.DRVICE_OTHER);
		    break;
		}
		update(user);
		livenessManager.updateLogin(user, time);
		httpSession.setAttribute(User.ACCOUNT, account);
		httpSession.setAttribute(User.TYPE, User.TYPE_USER);
		httpSession.setAttribute(User.IS_LOGINED, 1);
	    } else {
		throw new ServiceException(ErrorCode.USER_PASSWORD_ERROR);
	    }
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    Logger.getLogger(UserManagerImpl.class).error(StringUtil.getLoggerInfo(ErrorCode.INTENAL_ERROR, "appUserLogin() 密码加密失败"));
	    throw new ServiceException(ErrorCode.INTENAL_ERROR);
	}
    }

 
    @Override
    public List<User> getNormalUser(final String Param, final List<Object> values,
	    final PageBean pBean, final String orderStr) throws ServiceException, DaoException {
	if(pBean.currentPage < 0 || pBean.offset < 0 || pBean.pageSize <0){
	    throw new DaoException(ErrorCode.PARAM_ERROR);
	}
	 String hql = "from "+User.CLASS_NAME+" q where q."+Param+" in (";
	for (int i = 0; i < values.size(); i++) {
	    hql += " ?,";
	}
	hql = hql.substring(0,hql.length()-1);
	final String excuHql = hql + ") and q."+User.STATUS+" in ("+User.STATUS_NORMAL+","+User.STATUS_BLOCKED+") order by "+orderStr;
	List<User> users = getDaoHibernateTemplate().execute(new HibernateCallback<List<User>>() {

	    @Override
	    public List<User> doInHibernate(Session session)
		    throws HibernateException, SQLException {
		Query query =  session.createQuery(excuHql);
		for (int i = 0; i < values.size(); i++) {
		    query.setParameter(i, values.get(i));
		}
		if(pBean == null){
		    return query.list();
		}
		return query.setFirstResult(pBean.offset).setMaxResults(pBean.pageSize).list();
	    }
	});
	if(users == null || users.size() <1){
	    throw new ServiceException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	return users;
    } 
    
    
    @Override
    public void block(int id) throws DaoException {
	User user = userDao.find(id);
	user.setStatus(User.STATUS_BLOCKED);
	userDao.update(user);
    }

    @Override
    public void unblock(int id) throws DaoException {
	User user = userDao.find(id);
	user.setStatus(User.STATUS_NORMAL);
	userDao.update(user);
    }

    @Override
    public void loginOutEnterprise(String account, HttpSession httpSession) throws DaoException {
	httpSession.removeAttribute(User.ACCOUNT);
	httpSession.removeAttribute(User.TYPE);
	httpSession.removeAttribute(User.IS_LOGINED);
	httpSession.removeAttribute(EnterChineseMedicine.ENTERPRISE_ID);
	User user = findByAccount(account);
	userDao.update(user);
    }
    
}
