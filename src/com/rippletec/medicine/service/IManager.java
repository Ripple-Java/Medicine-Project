package com.rippletec.medicine.service;

import com.rippletec.medicine.dao.Dao;
import com.rippletec.medicine.dao.FindByPageDao;

public interface IManager<T> extends Dao<T>, FindByPageDao<T>{

}
