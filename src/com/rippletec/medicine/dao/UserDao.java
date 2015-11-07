package com.rippletec.medicine.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mysql.fabric.xmlrpc.base.Value;
import com.rippletec.medicine.model.User;

public interface UserDao extends FindAndSearchDao<User>{
	public static final String NAME="UserDao";

	HibernateTemplate getDaoHibernateTemplate();
	
}
