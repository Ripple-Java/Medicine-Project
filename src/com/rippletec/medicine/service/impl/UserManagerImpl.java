package com.rippletec.medicine.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.service.LivenessManager;
import com.rippletec.medicine.service.UserManager;
import com.rippletec.medicine.utils.EmailUtil;
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
    public User findByAccount(String account) {
	List<User> users = userDao.findByParam(User.ACCOUNT, account);
	if (!StringUtil.isEmpty(users))
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
	Object isLoginedAttr = httpSession.getAttribute(User.IS_LOGINED);
	if(accountAttr == null || isLoginedAttr == null)
	    return false;
	String sessionAc = (String) accountAttr;
	int islogined = (int) isLoginedAttr;
	return StringUtil.hasText(sessionAc) && islogined == 1;
    }

    @Override
    public boolean register(String phoneNumber, String password) throws DaoException {
	String securityPassword = "";
	try {
	    securityPassword = MD5Util.getEncryptedPwd(password);
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    return false;
	}
	User user = new User(securityPassword, phoneNumber ,phoneNumber, User.TYPE_USER, phoneNumber, User.DEFAULT_CERTIFICATEIMG, new Date(), new Date());
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
    public void updateUserInfo(String account,String name, int sex, Date birthday,
	    int degree, String email) {
	User user = findByAccount(account);
	if(user == null)
	    return;
	user.setSex(sex);
	user.setName(name);
	user.setBirthday(birthday);
	user.setDegree(degree);
	user.setEmail(email);
	user.setUpdateTime(new Date());
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
		    httpSession.setAttribute(User.IS_LOGINED, 1);
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
	httpSession.removeAttribute(User.IS_LOGINED);
	User user = findByAccount(account);
	user.setDeviceId(null);
	userDao.update(user);
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
	user.setDeviceId(null);
	userDao.update(user);
	return true;
    }

    @Override
    public Result adminLogin(String account, String password,
	    HttpSession httpSession) {
	ParamMap paramMap = new ParamMap().put(User.ACCOUNT, account)
					  .put(User.STATUS, User.STATUS_NORMAL);
	List<User> users  = userDao.findByParam(paramMap);
	if(StringUtil.isEmpty(users)){
	    return new Result(false, "该用户不存在或未启用");
	}
	User user = users.get(0);
	String dbPassword = user.getPassword();
	try {
	    if(MD5Util.validPasswd(password, dbPassword)){
		user.setLastLogin(new Date());
		update(user);
		httpSession.setAttribute(User.ACCOUNT, account);
		httpSession.setAttribute(User.TYPE, user.getType());
		httpSession.setAttribute(User.IS_LOGINED, 1);
		return new Result(true);
	    }
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    e.printStackTrace();
	    return new Result(false, "登录失败");
	}
	return new Result(false, "用户名或密码错误");
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
	Enterprise enterprise = (Enterprise) httpSession.getAttribute(Enterprise.CLASS_NAME);
	User user = new User(securityPassword, email , enterprise.getName(), User.TYPE_ENTER, email, registerDate, new Date(),User.STATUS_VAlIDATING);
	enterprise.setUser(user);
	enterpriseDao.save(enterprise);
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

    @Override
    public Result appUserLogin(String account, String password, int device,
	    String deviceId, HttpSession httpSession) {
	User user = findByAccount(account);
	if(user != null){
	    String securityPassword = user.getPassword();
	    try {
		if(MD5Util.validPasswd(password, securityPassword)){
		    Date time = new Date();
		    user.setLastLogin(time);
		    user.setDeviceId(deviceId);
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
		    httpSession.setAttribute(User.IS_LOGINED, 1);
		    return new Result(true, "登录成功");
		}else {
		    return new Result(false, "登录失败，用户名或密码不正确");
		}
	    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
		e.printStackTrace();
		return new Result(false, "登录失败，用户名或密码不正确");
	    }
	}
	   
	return new Result(false, "此用户不存在");
    }

 
    @Override
    public List<User> getNormalUser(final String Param, final List<Object> values,
	    final PageBean pBean, final String orderStr) {
	 String hql = "from "+User.CLASS_NAME+" q where q."+Param+" in (";
	for (int i = 0; i < values.size(); i++) {
	    hql += " ?,";
	}
	hql = hql.substring(0,hql.length()-1);
	final String excuHql = hql + ") and q."+User.STATUS+" in ("+User.STATUS_NORMAL+","+User.STATUS_BLOCKED+") order by "+orderStr;
	return getDaoHibernateTemplate().execute(new HibernateCallback<List<User>>() {

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
    } 
    
    
    @Override
    public Result block(int id) throws DaoException {
	User user = userDao.find(id);
	user.setStatus(User.STATUS_BLOCKED);
	userDao.update(user);
	return new Result(true);
    }

    @Override
    public Result unblock(int id) throws DaoException {
	User user = userDao.find(id);
	user.setStatus(User.STATUS_NORMAL);
	userDao.update(user);
	return new Result(true);
    }

    @Override
    public boolean loginOutEnterprise(String account, HttpSession httpSession) {
	httpSession.removeAttribute(User.ACCOUNT);
	httpSession.removeAttribute(User.TYPE);
	httpSession.removeAttribute(User.IS_LOGINED);
	httpSession.removeAttribute(EnterChineseMedicine.ENTERPRISE_ID);
	User user = findByAccount(account);
	userDao.update(user);
	return false;
    }
    
}
