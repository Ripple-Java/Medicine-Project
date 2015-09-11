package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.UserDao;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.utils.StringUtil;

@Repository(UserDao.NAME)
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public String getClassName() {
		return User.CLASS_NAME;
	}

	@Override
	public int getCount() {
	    return Integer.valueOf(getHibernateTemplate().find(StringUtil.getCountSql(User.CLASS_NAME)).listIterator().next()+"");
	}

	@Override
	public Class<User> getPersistClass() {
		return User.class;
	}
	
}
