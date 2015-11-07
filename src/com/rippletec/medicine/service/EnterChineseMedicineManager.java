package com.rippletec.medicine.service;

import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.vo.web.EnterChineseVO;

public interface EnterChineseMedicineManager extends IManager<EnterChineseMedicine> {
    public static final String NAME = "EnterChineseMedicineManager";

    CheckData addMedicine(Enterprise enterprise, ChineseMedicine chineseMedicine, EnterChineseVO entChineseVO);

    boolean active(int id);

}
