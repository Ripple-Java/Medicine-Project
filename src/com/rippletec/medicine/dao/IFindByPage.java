package com.rippletec.medicine.dao;

import java.util.List;
import java.util.Map;

import com.rippletec.medicine.bean.PageBean;

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
     */
    public List<T> findByPage(Map<String, Object> paramMap, PageBean page);
    
    /**
     * 基本分页查找方法hql实现（参数不能为外键或级联id）
     * @param page offset,pageSize字段不能为空
     * @return
     */
    public List<T> findByPage(PageBean page);
    
    /**
     * 单参数分页查找hql实现（参数不能为外键或级联id）
     * @param param 参数名
     * @param value 参数值
     * @param page offset,pageSize字段不能为空
     * @return
     */
    public List<T> findByPage(String param, Object value, PageBean page);
    
    /**
     * 单参数分页查找 sql实现（参数可以任意字段）
     * @param tableName 表名称
     * @param param	参数名
     * @param value	参数值
     * @param page	分页bean
     * @return
     */
    public List<T> findBySql(String tableName,String param, Object value,PageBean page);
}
