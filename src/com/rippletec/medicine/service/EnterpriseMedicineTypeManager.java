package com.rippletec.medicine.service;

import java.util.List;
import java.util.Map;

import com.rippletec.medicine.model.BaseModel;
import com.rippletec.medicine.model.EnterpriseMedicineType;

public interface EnterpriseMedicineTypeManager extends IManager<EnterpriseMedicineType> {
    public static final String NAME = "EnterpriseMedicineTypeManager";

    List<EnterpriseMedicineType> getTypesByEnterpriseId(int id);

    Map<String, Object> getMedicinesByTypeId(int typeId, int size, int currentPage);

}
