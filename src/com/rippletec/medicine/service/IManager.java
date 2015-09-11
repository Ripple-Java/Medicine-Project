package com.rippletec.medicine.service;

import com.rippletec.medicine.dao.Dao;
import com.rippletec.medicine.dao.FindByPageDao;
import com.rippletec.medicine.dao.ISearchDao;

public interface IManager<T> extends Dao<T>, FindByPageDao<T>, ISearchDao<T>{

}
