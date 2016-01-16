package com.rippletec.medicine.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
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

    @Override
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<DBLog> getModifiedData(int type, int version, int serverVersion) {
	String sql = "select * from "+DBLog.TABLE_NAME+" where action=? and db_version <=? and db_version >?"; 
	SQLQuery q = getSession().createSQLQuery(sql).addEntity(DBLog.class);
	q.setParameter(0, type)
	 .setParameter(1, serverVersion)
	 .setParameter(2, version);
	return q.list();
    }

}
