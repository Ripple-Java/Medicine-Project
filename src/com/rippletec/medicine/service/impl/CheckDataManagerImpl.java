package com.rippletec.medicine.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.CheckDataDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.ServiceException;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.service.CheckDataManager;
import com.rippletec.medicine.utils.ErrorCode;

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
	    final PageBean pBean) throws ServiceException{
	if(pBean.currentPage < 0 || pBean.pageSize < 0 || pBean.offset < 0){
	    throw new ServiceException(ErrorCode.PARAM_ERROR);
	}
	 String hql = "from "+CheckData.CLASS_NAME+" q where q.type in (";
	for (int i = 0; i < values.size(); i++) {
	    hql += " ?,";
	}
	hql = hql.substring(0,hql.length()-1);
	final String excuHql = hql + ")";
	List<CheckData> checkDatas = getDaoHibernateTemplate().execute(new HibernateCallback<List<CheckData>>() {
	    @Override
	    public List<CheckData> doInHibernate(Session session)
		    throws HibernateException, SQLException {
		Query query =  session.createQuery(excuHql);
		for (int i = 0; i < values.size(); i++) {
		    query.setParameter(i, values.get(i));
		}
		if(pBean == null){
		    return query.list();
		}
		return query.setMaxResults(pBean.offset).setMaxResults(pBean.offset+pBean.pageSize).list();
		
	    }
	});
	if(checkDatas == null || checkDatas.size() <1){
	    throw new ServiceException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	return checkDatas;
    } 
}
