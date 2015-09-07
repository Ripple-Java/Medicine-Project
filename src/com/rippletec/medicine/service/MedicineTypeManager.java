package com.rippletec.medicine.service;

import java.util.List;
import java.util.Map;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.model.BaseModel;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;

public interface MedicineTypeManager extends IManager<MedicineType> {
    public static final String NAME = "MedicineTypeManager";

    List<MedicineType> getTypeByParentId(int parentId);

    Map<String, Object> getMedicineByTypeId(int typeId, PageBean pageBean);

}
