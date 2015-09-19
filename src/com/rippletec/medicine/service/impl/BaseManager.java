package com.rippletec.medicine.service.impl;

import java.util.List;
import java.util.Map;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.service.IManager;

public abstract class BaseManager<T> implements IManager<T> {

    @Override
    public void delete(Integer id) {
	getDao().delete(id);
    }

    @Override
    public T find(Integer id) {
	return getDao().find(id);
    }

    @Override
    public List<T> findByPage(Map<String, Object> paramMap, PageBean page) {
	return getDao().findByPage(paramMap, page);
    }

    @Override
    public List<T> findByPage(PageBean page) {
	return getDao().findByPage(page);
    }
    
    @Override
    public List<T> findByPage(String param, Object value, PageBean page) {
	return getDao().findByPage(param, value, page);
    }

    @Override
    public List<T> findByParam(String param, Object value) {
	return getDao().findByParam(param, value);
    }
    
    @Override
    public List<T> findByParam(Map<String, Object> paramMap) {
	return getDao().findByParam(paramMap);
    }
    

    @Override
    public List<T> findBySql(String tableName, Map<String, Object> paramMap) {
	return getDao().findBySql(tableName, paramMap);
    }

    @Override
    public List<T> findBySql(String tableName, String param, Object value) {
	return getDao().findBySql(tableName, param, value);
    }

    @Override
    public List<T> findBySql(String tableName, String param, Object value,
	    PageBean page) {
	return getDao().findBySql(tableName, param, value, page);
    }

    protected abstract FindAndSearchDao<T> getDao();

    @Override
    public Integer save(T model) {
	return getDao().save(model);
    }
     
    @Override
    public List<T> search(Map<String, Object> paramMap) {
	return getDao().search(paramMap);
    }

    @Override
    public List<T> search(String param, Object value) {
	return getDao().search(param, value);
    }

    @Override
    public void update(T model) {
	getDao().update(model);
    }

}
