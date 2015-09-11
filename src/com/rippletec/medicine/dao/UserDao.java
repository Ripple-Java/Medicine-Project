package com.rippletec.medicine.dao;

import com.rippletec.medicine.model.User;

public interface UserDao extends FindAndSearchDao<User>{
	public static final String NAME="UserDao";
	
	int getCount();
}
