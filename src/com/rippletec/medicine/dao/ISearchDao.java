package com.rippletec.medicine.dao;

import java.util.List;
import java.util.Map;

import com.rippletec.medicine.exception.DaoException;

/**
 * @author Liuyi
 * 搜索接口，提供搜索功能
 * @param <T>
 */
public interface ISearchDao<T> {
    
    /**
     * 多少字段条目搜索（hql实现，参数不能为外键或级联id）
     * @param paramMap 多个key-value参数搜索
     * @return
     * @throws DaoException 
     */
    List<T> search(Map<String, Object> paramMap) throws DaoException;
    
    /**
     * 单字段条目搜索（hql实现，参数不能为外键或级联id）
     * @param param 搜索字段
     * @param value 搜索字段值
     * @return
     * @throws DaoException 
     */
    List<T> search(String param, Object value) throws DaoException;

    /**
     * 单字段+条件条目搜索（sql实现，参数可为表中任意字段）
     * @param tableName		表名
     * @param field		搜索字段
     * @param keyword		搜索值
     * @param param		搜索条件字段
     * @param value		搜索条件字段满足的值
     * @return
     * @throws DaoException 
     */
    List<T> search(String tableName, String field, Object keyword, String param, Object value) throws DaoException;

    /**
     * 多字段+多个条件条目搜索（sql实现，参数可为表中任意字段）
     * @param tableName 表名
     * @param fieldMap	多个key-value搜索参数
     * @param paramMap	多个key-value搜索条件
     * @return
     * @throws DaoException 
     */
    List<T> search(String tableName, Map<String, Object> fieldMap, Map<String, Object> paramMap) throws DaoException;
}
