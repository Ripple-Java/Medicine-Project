package com.rippletec.medicine.service;

import java.util.List;

import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;

public interface EnterpriseManager extends IManager<Enterprise>{
    public static final String NAME = "EnterpriseManager";

    List<EnterpriseMedicineType> getEnterMedicineTypes(int id);

    List<Enterprise> getEnterprise(int size, int type, int page);
}
