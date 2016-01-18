package com.rippletec.medicine.dao;

import java.util.List;
import java.util.Map;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.exception.DaoException;

/**
 * @author Liuyi
 * 分页查询接口，提供分页查询功能
 * @param <T>
 */
public interface IFindByPage<T> {
    
    /**
     * 多参数分页查找hql实现（参数不能为外键或级联id）
     * @param paramMap 格式为：<参数名，参数值>
     * @param page offset,pageSize字段不能为空
     * @return
     * @throws DaoException 
     */
    public List<T> findByPage(Map<String, Object> paramMap, PageBean page) throws DaoException;
    
    /**
     * 基本分页查找方法hql实现（参数不能为外键或级联id）
     * @param page offset,pageSize字段不能为空
     * @return
     * @throws DaoException 
     */
    public List<T> findByPage(PageBean page) throws DaoException;
    
    /**
     * 单参数分页查找hql实现（参数不能为外键或级联id）
     * @param param 参数名
     * @param value 参数值
     * @param page offset,pageSize字段不能为空
     * @return
     * @throws DaoException 
     */
    public List<T> findByPage(String param, Object value, PageBean page) throws DaoException;
    
    /**
     * 单参数分页查找 sql实现（参数可以任意字段）
     * @param tableName 表名称
     * @param param	参数名
     * @param value	参数值
     * @param page	分页bean
     * @return
     * @throws DaoException 
     */
    public List<T> findBySql(String tableName,String param, Object value,PageBean page) throws DaoException;
}
