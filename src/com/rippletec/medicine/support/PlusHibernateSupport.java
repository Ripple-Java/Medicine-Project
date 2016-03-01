package com.rippletec.medicine.support;

import java.sql.SQLException;
import java.util.List;













import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.utils.ErrorCode;

/**
 * @author Liuyi
 * HibernateTemplate装饰类，为HibernateTemplate提供额外的功能
 * @param <T>
 */
public class PlusHibernateSupport<T> extends HibernateDaoSupport{

 
    public List<T> findByPage(final String hql, int offset, int pageSize) throws DaoException {
	return findByPage(hql,new String[]{}, new Object[]{}, offset, pageSize);
    }
    
    public List<T> findByPage(final String hql,final String param,final Object value, int offset, int pageSize) throws DaoException {
	return findByPage(hql,new String[]{param}, new Object[]{value}, offset,pageSize);
    }
    
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<T> findByPage(final String hql,final String[] params,final Object[] values, final int offset, final int pageSize) throws DaoException {
	if(offset < 0 || pageSize < 0){
	    throw new DaoException(ErrorCode.PARAM_ERROR);
	}
	List<T> items = getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {

	    @Override
	    public List<T> doInHibernate(Session session)
		    throws HibernateException, SQLException {
		Query q = session.createQuery(hql);
		for (int i = 0; i < params.length; i++) {
		    q.setParameter(params[i], values[i]);
		}
		List<T> result = q.setFirstResult(offset).setMaxResults(pageSize).list();
		
		return result;
	    }
	    
	});
	if(items == null || items.size() <1){
	    throw new DaoException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	return items;
    }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<T> findByParam(final String hql,final String[] params ,final Object[] values) throws DaoException {
	List<T> items = getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {

	    @Override
	    public List<T> doInHibernate(Session session)
		    throws HibernateException, SQLException {
		Query q = session.createQuery(hql);
		for (int i = 0; i < params.length; i++) {
		    q.setParameter(params[i], values[i]);
		}
		List<T> result = q.list();
		return result;
	    }
	    
	});
	if(items == null || items.size() <1){
	    throw new DaoException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	return items;
      }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<T> findBySql(final Class<T> enityClass, final String sql,final Object value, final int offset, final int pageSize) throws DaoException {
	if(offset < 0 || pageSize < 0){
	    throw new DaoException(ErrorCode.PARAM_ERROR);
	}
	List<T> items = getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {

	    @Override
	    public List<T> doInHibernate(Session session)
		    throws HibernateException, SQLException {
		Query q = session.createSQLQuery(sql).addEntity(enityClass);
		q.setParameter(0, value);
		List<T> result = q.setFirstResult(offset).setMaxResults(pageSize).list();
		return result;
	    }
	    
	});
	if(items == null || items.size() <1){
	    throw new DaoException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	return items;
      }
    
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<T> findBySql(final Class<T> enityClass, final String sql, final String[] params,final Object[] values) throws DaoException {
	List<T> items = getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {

	    @Override
	    public List<T> doInHibernate(Session session)
		    throws HibernateException, SQLException {
		Query q = session.createSQLQuery(sql).addEntity(enityClass);
		for (int i = 0; i < params.length; i++) {
		    q.setParameter(i, values[i]);
		}
		List<T> result = q.list();
		return result;
	    }
	    
	});
	if(items == null || items.size() <1){
	    throw new DaoException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	return items;
      }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<T> findBySql(final Class<T> enityClass, final String sql, final String[] params,final Object[] values, final int offset, final int pageSize) throws DaoException {
	List<T> items = getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {

	    @Override
	    public List<T> doInHibernate(Session session)
		    throws HibernateException, SQLException {
		Query q = session.createSQLQuery(sql).addEntity(enityClass);
		for (int i = 0; i < params.length; i++) {
		    q.setParameter(i, values[i]);
		}
		if(offset == 0 && pageSize == 0){
		    return q.list();
		}else {
		    return q.setFirstResult(offset).setMaxResults(pageSize).list();
		}
	    }
	    
	});
	if(items == null || items.size() <1){
	    throw new DaoException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	return items;
      }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<T> findCount(final String sql, final String[] params,final Object[] values) throws DaoException {
	List<T> items = getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {

	    @Override
	    public List<T> doInHibernate(Session session)
		    throws HibernateException, SQLException {
		Query q = session.createSQLQuery(sql);
		if(values != null && values.length > 0){
        		for (int i = 0; i < params.length; i++) {
        		    q.setParameter(i, values[i]);
        		}
		}
		List<T> result = q.list();
		return result;
	    }
	    
	});
	if(items == null || items.size() <1){
	    throw new DaoException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	return items;
      }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<T> findCount(final String sql, final String params,final Object whereValue, String inParam, final Object[] values) throws DaoException {
	List<T> items = getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {

	    @Override
	    public List<T> doInHibernate(Session session)
		    throws HibernateException, SQLException {
		Query q = session.createSQLQuery(sql);
		if(values != null && values.length > 0){
		    	int i = 0;
        		for (; i < values.length; i++) {
        		    q.setParameter(i, values[i]);
        		}
        		if(whereValue != null)
        		    q.setParameter(i, whereValue);
		}
		
		List<T> result = q.list();
		return result;
	    }
	    
	});
	if(items == null || items.size() <1){
	    throw new DaoException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	return items;
      }
    
    public List<T> Search(final String hql,final String param ,final Object value) throws DaoException {
	return Search(hql, new String[]{param}, new Object[]{value});
    }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<T> Search(final String hql,final String[] params ,final Object[] values) throws DaoException {
	List<T> items = getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {

	    @Override
	    public List<T> doInHibernate(Session session)
		    throws HibernateException, SQLException {
		System.out.println(hql);
		Query q = session.createQuery(hql);
		for (int i = 0; i < params.length; i++) {
		    q.setParameter(params[i], "%"+values[i]+"%");
		}
		List<T> result = q.list();
		return result;
	    }
	    
	});
	if(items == null || items.size() <1){
	    throw new DaoException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	return items;
    }
    
    public List<T> Search(final Class<T> enityClass,final String hql,final String field ,final Object keyword ,final String param ,final Object value) throws DaoException {
 	return Search(enityClass,hql, new String[]{field}, new Object[]{keyword}, new String[]{param}, new Object[]{value});
     }
     
     @SuppressWarnings({ "unchecked", "deprecation" })
     public List<T> Search(final Class<T> enityClass, final String hql,final String[] fields ,final Object[] keywords, final String[] params ,final Object[] values) throws DaoException {
 	List<T> items = getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {

 	    @Override
 	    public List<T> doInHibernate(Session session)
 		    throws HibernateException, SQLException {
 		System.out.println(hql);
 		int i = 0;
 		Query q = session.createSQLQuery(hql).addEntity(enityClass);
 		for (i = 0; i < fields.length; i++) {
 		    q.setParameter(i,"%"+ keywords[i]+"%");
 		}
 		for (int n=0; n < params.length; n++) {
 		    q.setParameter(i+n, values[n]);
 		}
 		List<T> result = q.list();
 		return result;
 	    }
 	    
 	});
 	if(items == null || items.size() <1){
	    throw new DaoException(ErrorCode.DB_NO_ENITY_ERROR);
	}
 	return items;
     }
       

}
