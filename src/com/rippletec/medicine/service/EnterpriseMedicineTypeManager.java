package com.rippletec.medicine.service;

import java.util.List;
import java.util.Map;

import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.EnterpriseMedicineType;

public interface EnterpriseMedicineTypeManager extends IManager<EnterpriseMedicineType> {
    public static final String NAME = "EnterpriseMedicineTypeManager";

    Map<String, Object> getMedicinesByTypeId(int typeId, int size, int currentPage) throws DaoException;

    List<EnterpriseMedicineType> getTypesByEnterpriseId(int id) throws DaoException;

    List<EnterpriseMedicineType> getTypes(int enterpriseId, int type) throws DaoException;

}
