package com.rippletec.medicine.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.UserDao;
import com.rippletec.medicine.dao.UserFavoriteDao;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.UserFavorite;
import com.rippletec.medicine.service.UserFavoriteManager;


@Service(UserFavoriteManager.NAME)
public class UserFavoriteManagerImpl extends BaseManager<UserFavorite> implements UserFavoriteManager{
    
    @Resource(name = UserFavoriteDao.NAME)
    private UserFavoriteDao userFavoriteDao;

    @Resource(name=UserDao.NAME)
    private UserDao userDao;
    @Override
    protected FindAndSearchDao<UserFavorite> getDao() {
	return this.userFavoriteDao;
    }

	@Override
	public boolean addUserFavorite(String account, UserFavorite userFavorite) {
		// TODO Auto-generated method stub
		List<User> users=userDao.findByPage(User.ACCOUNT, account, new PageBean(0, 100));
		if (users.size()<=0) {
			return false;
		}
		userFavorite.setUser(users.get(0));
		userFavoriteDao.save(userFavorite);
		return true;
	}

	@Override
	public List<UserFavorite> findByAccount(String account) {
	    List<UserFavorite> userFavorites = new LinkedList<UserFavorite>();
	    List<User> user = userDao.findByParam(User.ACCOUNT, account);
	    if(user !=null && user.size()>0)
		userFavorites = findBySql(UserFavorite.TABLE_NAME, UserFavorite.USER_ID, account);
	    return userFavorites;
	}
    
   
}
    
   
