package com.rippletec.medicine.service;

import java.util.List;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.ServiceException;
import com.rippletec.medicine.model.CheckData;
public interface CheckDataManager extends IManager<CheckData> {
    public static final String NAME = "CheckDataManager";

    List<CheckData> findResCheckData(String type, List<Object> values,
	    PageBean pBean) throws DaoException, ServiceException;

}
