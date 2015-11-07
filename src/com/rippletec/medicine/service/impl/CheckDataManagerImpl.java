package com.rippletec.medicine.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.CheckDataDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.service.CheckDataManager;

@Service(CheckDataManager.NAME)
public class CheckDataManagerImpl extends BaseManager<CheckData> implements CheckDataManager{

    
    @Resource(name = CheckDataDao.NAME)
    private CheckDataDao checkDataDao;
    
    @Override
    protected FindAndSearchDao<CheckData> getDao() {
	return checkDataDao;
    }

    @Override
    public List<CheckData> findResCheckData(final String type, final List<Object> values,
	    final PageBean pBean) {
	 String hql = "from "+CheckData.CLASS_NAME+" q where q.type in (";
	for (int i = 0; i < values.size(); i++) {
	    hql += " ?,";
	}
	hql = hql.substring(0,hql.length()-1);
	final String excuHql = hql + ")";
	return getDaoHibernateTemplate().execute(new HibernateCallback<List<CheckData>>() {

	    @Override
	    public List<CheckData> doInHibernate(Session session)
		    throws HibernateException, SQLException {
		Query query =  session.createQuery(excuHql);
		for (int i = 0; i < values.size(); i++) {
		    query.setParameter(i, values.get(i));
		}
		return query.setMaxResults(pBean.offset).setMaxResults(pBean.offset+pBean.pageSize).list();
	    }
	});
    } 
}
