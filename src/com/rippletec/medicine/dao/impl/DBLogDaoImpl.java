package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.DBLogDao;
import com.rippletec.medicine.model.DBLog;
import com.rippletec.medicine.utils.StringUtil;

@Repository(DBLogDao.NAME)
public class DBLogDaoImpl extends BaseDaoImpl<DBLog> implements DBLogDao{

    @Override
    public String getClassName() {
	return DBLog.CLASS_NAME;
    }

    @Override
    public Class<DBLog> getPersistClass() {
	return DBLog.class;
    }

    @Override
    public int getCount() {
	return ((Long)getHibernateTemplate().find(StringUtil.getCountSql(DBLog.CLASS_NAME)).listIterator().next()).intValue();
    }
      

}
