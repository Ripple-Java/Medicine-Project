package com.rippletec.medicine.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.Dao;
import com.rippletec.medicine.dao.IFindByPage;
import com.rippletec.medicine.dao.ISearchDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.support.PlusHibernateSupport;
import com.rippletec.medicine.utils.ErrorCode;
import com.rippletec.medicine.utils.StringUtil;

public abstract class BaseDaoImpl<T> extends PlusHibernateSupport<T> implements IFindByPage<T>, Dao<T> ,ISearchDao<T>{
    
    @Resource
    public void _setSessionFactory(SessionFactory sessionFactory) {
	super.setSessionFactory(sessionFactory);
    }
    
    @Override
    public boolean delete(Integer id) throws DaoException {
	if(id < 1){
	    throw new DaoException(ErrorCode.DB_ID_ERROR);
	}
	T model = find(id);
	if(model == null) {
	    throw new DaoException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	getHibernateTemplate().delete(model);
	return true;
    }
    
    @Override
    public T find(Integer id) throws DaoException {
	if(id < 1){
	    throw new DaoException(ErrorCode.DB_ID_ERROR);
	}
	T object = getHibernateTemplate().get(getPersistClass(), id);
	if(object == null){
	    throw new DaoException(ErrorCode.DB_NO_ENITY_ERROR);
	}
	return object;
    }
    
    @Override
    public void update(T model) {
	getHibernateTemplate().update(model);
    }
    
    @Override
    public Integer save(T model) throws DaoException {
	int id = (Integer) getHibernateTemplate().save(model);
	if(id < 1){
	    throw new DaoException(ErrorCode.DB_SAVE_ERROR);
	}
	return id;
    }

    @Override
    public List<T> findAll() throws DaoException {
        List<T> objects =  getHibernateTemplate().find(StringUtil.getSelectHql(getClassName()));
        if(objects == null || objects.size() < 1){
            throw new DaoException(ErrorCode.DB_NO_ENITY_ERROR);
        }
        return objects;
    }

    @Override
    public List<T> findByPage(Map<String, Object> paramMap,
	    PageBean page) throws DaoException {
	String[] params = paramMap.keySet().toArray(new String[]{});
	Object[] values = paramMap.values().toArray();
	String hql = StringUtil.getSelectHql(getClassName(), params);
	List<T> objects = findByPage(hql, params, values, page.offset, page.pageSize);
	return objects;
    }
    
    @Override
    public List<T> findByPage(PageBean page) throws DaoException {
	if(page == null)
	    findAll();
	List<T> objects =  findByPage(StringUtil.getSelectHql(getClassName()), page.offset, page.pageSize);
	return objects;
    }
    
    @Override
    public List<T> findByPage(String param, Object value, PageBean page) throws DaoException {
	if(param == null || value == null)
	    return findByPage(page);
	String hql = StringUtil.getSelectHql(getClassName(), param);
	List<T> objects =  findByPage(hql, param, value, page.offset, page.pageSize);
	return objects;
    }

    @Override
    public List<T> findByParam(String param, Object value) throws DaoException {
	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put(param, value);
	return findByParam(paramMap);
    }
    
    @Override
    public List<T> findByParam(Map<String, Object> paramMap) throws DaoException {
	String[] params = paramMap.keySet().toArray(new String[]{});
	Object[] values = paramMap.values().toArray();
	String hql = StringUtil.getSelectHql(getClassName(), params);
	return findByParam(hql, params, values);
    }

    @Override
    public List<T> findBySql(String tableName, Map<String, Object> paramMap) throws DaoException {
	String[] params = paramMap.keySet().toArray(new String[]{});
	Object[] values = paramMap.values().toArray();
	String sql = StringUtil.getSelectSql(tableName, params);
	return findBySql(getPersistClass(), sql, params, values);
    }

    @Override
    public List<T> findBySql(String tableName, String param, Object value) throws DaoException {
	String sql = StringUtil.getSelectSql(tableName, param);
	return findBySql(getPersistClass(),sql,value);
    }

    @Override
    public List<T> findBySql(String tableName, String param, Object value,
	    PageBean page) throws DaoException {
	if(param == null || value == null)
	    return findByPage(page);
	String sql = StringUtil.getSelectSql(tableName,param);
	if(page == null)
	    return findBySql(getPersistClass(), sql, value);
	return findBySql(getPersistClass(), sql, value, page.offset, page.pageSize);
    }
   
    /**
     * 模板方法，子类实现即可具有分页查找功能
     * @return 持久化类类名
     */
    public abstract String getClassName();
    
    /**
     * 模板方法，子类实现即可具有增删查改功能
     * @return 持久化类class
     */
    public abstract Class<T> getPersistClass();
    

    @Override
    public List<T> search(Map<String, Object> paramMap) throws DaoException {
	String[] params = paramMap.keySet().toArray(new String[]{});
	Object[] values = paramMap.values().toArray();
	String hql = StringUtil.getSearchHql(getClassName(), params);
	return Search(hql, params, values);
    }

    @Override
    public List<T> search(String param, Object value) throws DaoException {
	String hql = StringUtil.getSearchHql(getClassName(), param);
	return Search(hql, param, value);
    }
    
    @Override
    public List<T> search(String tableName, Map<String, Object> fieldMap, Map<String, Object> paramMap) throws DaoException {
	String[] fields = fieldMap.keySet().toArray(new String[]{});
	Object[] keyworlds = fieldMap.values().toArray();
	String[] params = paramMap.keySet().toArray(new String[]{});
	Object[] values = paramMap.values().toArray();
	String hql = StringUtil.getSearchSql(tableName, fields ,params);
	return Search(getPersistClass(),hql, fields, keyworlds, params, values);
    }
    
    @Override
    public List<T> search(String tableName, String field, Object keyword, String param , Object value) throws DaoException {
	String hql = StringUtil.getSearchSql(tableName, field , param);
	return Search(getPersistClass(), hql, field, keyword,param, value);
    }

    @Override
    public int getCount(String tableName) throws NumberFormatException, DaoException {
	return getCount(tableName, new String[]{}, new Object[]{});
    }

    @Override
    public int getCount(String tableName,String param, Object value) throws NumberFormatException, DaoException {
	return getCount(tableName, new String[]{param}, new Object[]{ value});
    }
    
    @Override
    public int getCount(String tableName,String[] param, Object[] value) throws NumberFormatException, DaoException {
	return Integer.valueOf(findCount(StringUtil.getCountSql(tableName, param),param,value).listIterator().next()+"");
    }
    
    @Override
    public int getCount(String tableName,String whereParam, Object whereValue,String inParam, Object[] inValue) throws NumberFormatException, DaoException {
	return Integer.valueOf(findCount(StringUtil.getCountInSql(tableName, whereParam,inParam, inValue),whereParam,whereValue, inParam, inValue).listIterator().next()+"");
    }

    @Override
    public HibernateTemplate getDaoHibernateTemplate() {
	return getHibernateTemplate();
    }

}
