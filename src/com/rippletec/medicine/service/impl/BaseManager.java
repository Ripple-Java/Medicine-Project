package com.rippletec.medicine.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.ServiceException;
import com.rippletec.medicine.service.IManager;
import com.rippletec.medicine.utils.ErrorCode;

public abstract class BaseManager<T> implements IManager<T> {
    
    public Logger logger = Logger.getLogger("controllerLog");
    

    @Override
    public boolean delete(Integer id) throws DaoException {
	return getDao().delete(id);
    }

    @Override
    public T find(Integer id) throws DaoException {
	return getDao().find(id);
    }

    @Override
    public List<T> findAll() throws DaoException {
        return getDao().findAll();
    }

    @Override
    public List<T> findByPage(Map<String, Object> paramMap, PageBean page) throws DaoException {
	return getDao().findByPage(paramMap, page);
    }

    @Override
    public List<T> findByPage(PageBean page) throws DaoException {
	return getDao().findByPage(page);
    }
    
    @Override
    public List<T> findByPage(String param, Object value, PageBean page) throws DaoException {
	return getDao().findByPage(param, value, page);
    }

    @Override
    public List<T> findByParam(String param, Object value) throws DaoException {
	return getDao().findByParam(param, value);
    }
    
    @Override
    public List<T> findByParam(Map<String, Object> paramMap) throws DaoException {
	return getDao().findByParam(paramMap);
    }
    

    @Override
    public List<T> findBySql(String tableName, Map<String, Object> paramMap) throws DaoException {
	return getDao().findBySql(tableName, paramMap);
    }
    
    @Override
    public List<T> findBySql(String tableName, Map<String, Object> paramMap, PageBean pageBean) throws DaoException {
	return getDao().findBySql(tableName, paramMap, pageBean);
    }

    @Override
    public List<T> findBySql(String tableName, String param, Object value) throws DaoException {
	return getDao().findBySql(tableName, param, value);
    }

    @Override
    public List<T> findBySql(String tableName, String param, Object value,
	    PageBean page) throws DaoException {
	return getDao().findBySql(tableName, param, value, page);
    }

    protected abstract FindAndSearchDao<T> getDao();

    @Override
    public Integer save(T model) throws DaoException {
	return getDao().save(model);
    }
     
    @Override
    public List<T> search(Map<String, Object> paramMap) throws DaoException {
	return getDao().search(paramMap);
    }

    @Override
    public List<T> search(String param, Object value) throws DaoException {
	return getDao().search(param, value);
    }

    @Override
    public void update(T model) {
	getDao().update(model);
    }

    @Override
    public int getCount(String tableName) throws NumberFormatException, DaoException {
	return getDao().getCount(tableName);
    }

    @Override
    public int getCount(String tableName, String param, Object value) throws NumberFormatException, DaoException {
	return getDao().getCount(tableName,param,value);
    }

    @Override
    public int getCount(String tableName, String[] param, Object[] value) throws NumberFormatException, DaoException {
	return getDao().getCount(tableName, param, value);
    }
   

    @Override
    public int getCount(String tableName, String whereParam, Object whereValue,
	    String inParam, Object[] inValue) throws NumberFormatException, DaoException {
	return getDao().getCount(tableName, whereParam, whereValue, inParam, inValue);
    }

    @Override
    public HibernateTemplate getDaoHibernateTemplate() {
	return getDao().getDaoHibernateTemplate();
    }

    @Override
    public List<T> search(String tableName, String field, Object keyword, String param,
	    Object value) throws DaoException {
	return getDao().search(tableName,field, keyword, param, value);
    }

    @Override
    public List<T> search(String tableName, Map<String, Object> fieldMap,
	    Map<String, Object> paramMap) throws DaoException {
	return getDao().search(tableName,fieldMap, paramMap);
    }
    

    
}
