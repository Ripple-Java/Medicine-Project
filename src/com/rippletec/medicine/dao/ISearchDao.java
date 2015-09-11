package com.rippletec.medicine.dao;

import java.util.List;
import java.util.Map;


public interface ISearchDao<T> {
    public List<T> search(Map<String, Object> paramMap);
    
    public List<T> search(String param, Object value);
}
