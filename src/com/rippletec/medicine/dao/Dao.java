/**
 * 
 */
package com.rippletec.medicine.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.ServiceException;

/**
 * @author Liuyi
 *
 */
public interface Dao <T>{
    
    /**
     * @throws DaoException 
     * @throws ServiceException 
     * @throws DaoException 
     * Dao层基础delete()方法
     * @param id 需要删除条目的id
     * @throws  
     */
    boolean delete(Integer id) throws DaoException;
    
    /**
     * Dao层基础find方法
     * @param id 条目id
     * @return 
     * @throws DaoException 
     */
    T find(Integer id) throws DaoException;
    
    /**
     * 参数搜索方法，使用hql
     * @param param 搜索字段（该字段不能为外键，或级联id）
     * @param value 搜索值
     * @return
     * @throws DaoException 
     */
    List<T> findByParam(String param, Object value) throws DaoException;
    
    /**
     * 参数搜索方法，使用hql
     * @param paramMap	多个key-value参数搜索（该字段不能为外键，或级联id）
     * @return
     * @throws DaoException 
     */
    List<T> findByParam(Map<String, Object> paramMap) throws DaoException;
    
    /**
     * 参数搜索方法，使用sql实现
     * @param tableName 搜索表名称
     * @param paramMap	多个key-value参数搜索（可以是表中任意字段）
     * @return
     * @throws DaoException 
     */
    List<T> findBySql(String tableName, Map<String, Object> paramMap) throws DaoException;
    
    /**
     * 参数搜索方法，使用sql
     * @param tableName 搜索表名称
     * @param param 搜索字段（可以是表中任意字段）
     * @param value 搜索值
     * @return
     * @throws DaoException 
     */
    List<T> findBySql(String tableName, String param, Object value) throws DaoException;

    /**
     * 返回所有条目
     * @return
     * @throws DaoException 
     */
    List<T> findAll() throws DaoException;
    
    /**
     * Dao层基础save()方法
     * @param model 需要持久化的实体
     * @return id
     * @throws DaoException 
     */
    Integer save(T model) throws DaoException;
    
    /**
     * Dao层基础update方法
     * @param model 更新实体
     */
    void update(T model);
    
    /**
     * Dao层基础getCount方法
     * @param tableName 数据库表名称
     * @return 表的条目总数
     * @throws DaoException 
     * @throws NumberFormatException 
     */
    int getCount(String tableName) throws NumberFormatException, DaoException;
	
  
    /**
     * Dao层基础getCount方法
     * @param tableName 表名
     * @param param	指定某个字段的条目统计数量
     * @param value	指定统计
     * @return
     * @throws DaoException 
     * @throws NumberFormatException 
     */
    int getCount(String tableName, String param, Object value) throws NumberFormatException, DaoException;
    
    
    /**
     * Dao层基础getCount方法
     * @param tableName 表名
     * @param param	指定多个字段的统计数量（多个字段直接是&&的关系）
     * @param value	多个统计数量参数的值，必须和参数位置一一对应
     * @return
     * @throws DaoException 
     * @throws NumberFormatException 
     */
    int getCount(String tableName, String[] param, Object[] value) throws NumberFormatException, DaoException;
    
    /**
     * Dao层基础getCount方法
     * @param tableName 	表名
     * @param whereParam	指定某个字段的条目统计数
     * @param whereValue	指定字段的值
     * @param inParam		指定第二个字段
     * @param inValue		第二个字段满足多个值
     * @return
     * @throws DaoException 
     * @throws NumberFormatException 
     */
    int getCount(String tableName, String whereParam, Object whereValue,
	    String inParam, Object[] inValue) throws NumberFormatException, DaoException;

    
    
    HibernateTemplate getDaoHibernateTemplate();

    
}
