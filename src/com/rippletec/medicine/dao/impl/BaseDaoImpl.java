package com.rippletec.medicine.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.ui.Model;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.Dao;
import com.rippletec.medicine.dao.IFindByPage;
import com.rippletec.medicine.dao.ISearchDao;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.support.PlusHibernateSupport;
import com.rippletec.medicine.utils.StringUtil;

public abstract class BaseDaoImpl<T> extends PlusHibernateSupport<T> implements IFindByPage<T>, Dao<T> ,ISearchDao<T>{
    
    @Resource
    public void _setSessionFactory(SessionFactory sessionFactory) {
	super.setSessionFactory(sessionFactory);
    }
    
    @Override
    public boolean delete(Integer id) {
	T model = find(id);
	if(model == null) 
	    return false;
	getHibernateTemplate().delete(model);
	return true;
    }
    
    @Override
    public T find(Integer id) {
	return getHibernateTemplate().get(getPersistClass(), id);
    }
    
    @Override
    public void update(T model) {
	getHibernateTemplate().update(model);
    }
    
    @Override
    public Integer save(T model) {
	return (Integer) getHibernateTemplate().save(model);
    }

    @Override
    public List<T> findByPage(Map<String, Object> paramMap,
	    PageBean page) {
	String[] params = paramMap.keySet().toArray(new String[]{});
	Object[] values = paramMap.values().toArray();
	String hql = StringUtil.getSelectHql(getClassName(), params);
	return findByPage(hql, params, values, page.offset, page.pageSize);
    }
    
    @Override
    public List<T> findByPage(PageBean page) {
	return findByPage(StringUtil.getSelectHql(getClassName()), page.offset, page.pageSize);
    }
    
    @Override
    public List<T> findByPage(String param, Object value, PageBean page) {
	if(param == null || value == null)
	    return findByPage(page);
	String hql = StringUtil.getSelectHql(getClassName(), param);
	return findByPage(hql, param, value, page.offset, page.pageSize);
    }

    @Override
    public List<T> findByParam(String param, Object value) {
	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put(param, value);
	return findByParam(paramMap);
    }
    
    @Override
    public List<T> findByParam(Map<String, Object> paramMap) {
	String[] params = paramMap.keySet().toArray(new String[]{});
	Object[] values = paramMap.values().toArray();
	String hql = StringUtil.getSelectHql(getClassName(), params);
	return findByParam(hql, params, values);
    }

    @Override
    public List<T> findBySql(String tableName, Map<String, Object> paramMap) {
	String[] params = paramMap.keySet().toArray(new String[]{});
	Object[] values = paramMap.values().toArray();
	String sql = StringUtil.getSelectSql(tableName, params);
	return findBySql(getPersistClass(), sql, params, values);
    }

    @Override
    public List<T> findBySql(String tableName, String param, Object value) {
	String sql = StringUtil.getSelectSql(tableName, param);
	return findBySql(getPersistClass(),sql,value);
    }

    @Override
    public List<T> findBySql(String tableName, String param, Object value,
	    PageBean page) {
	if(param == null || value == null)
	    return findByPage(page);
	String sql = StringUtil.getSelectSql(tableName,param);
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
    public List<T> search(Map<String, Object> paramMap) {
	String[] params = paramMap.keySet().toArray(new String[]{});
	Object[] values = paramMap.values().toArray();
	String hql = StringUtil.getSearchHql(getClassName(), params);
	return Search(hql, params, values);
    }

    @Override
    public List<T> search(String param, Object value) {
	String hql = StringUtil.getSearchHql(getClassName(), param);
	return Search(hql, param, value);
    }

    @Override
    public int getCount(String tableName) {
	return getCount(tableName, new String[]{}, new Object[]{});
    }

    @Override
    public int getCount(String tableName,String param, Object value) {
	return getCount(tableName, new String[]{param}, new Object[]{ value});
    }
    
    @Override
    public int getCount(String tableName,String[] param, Object[] value) {
	return Integer.valueOf(findCount(StringUtil.getCountSql(tableName, param),param,value).listIterator().next()+"");
    }

    @Override
    public HibernateTemplate getDaoHibernateTemplate() {
	return getHibernateTemplate();
    }
    
    
   

}
