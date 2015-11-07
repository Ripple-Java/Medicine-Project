package com.rippletec.medicine.service;

import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.vo.web.EnterWestVO;

public interface EnterWestMedicineManager extends IManager<EnterWestMedicine> {
    
    public static final String NAME = "EnterWestMedicineManager";

    CheckData addMedicine(Enterprise enterprise, WestMedicine westMedicine,
	    EnterWestVO enterWestVO);

    void active(int id);

}
