package com.rippletec.medicine.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.rippletec.medicine.model.User;

public interface UserDao extends FindAndSearchDao<User>{
	public static final String NAME="UserDao";

	@Override
	HibernateTemplate getDaoHibernateTemplate();
	
}
