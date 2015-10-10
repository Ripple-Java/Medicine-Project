package com.rippletec.medicine.service;

import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.vo.web.EnterChineseVO;

public interface EnterChineseMedicineManager extends IManager<EnterChineseMedicine> {
    public static final String NAME = "EnterChineseMedicineManager";

    boolean addMedicine(Enterprise enterprise, ChineseMedicine chineseMedicine, EnterChineseVO entChineseVO);

}
