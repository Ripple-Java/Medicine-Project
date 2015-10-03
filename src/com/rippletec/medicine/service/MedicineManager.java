package com.rippletec.medicine.service;

import java.util.List;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.model.BaseModel;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.utils.JsonUtil;
import com.rippletec.medicine.vo.BackGroundMedicineVO;

public interface MedicineManager extends IManager<Medicine> {
    public static final String NAME = "MedicineManager";
    
    boolean deleteMedicine(int id , int type );
    
    boolean getMedicine(PageBean page,int type, JsonUtil jsonUtil);

    boolean getMedicineByCategory(PageBean page, int type, int category,
	    JsonUtil jsonUtil);

    boolean getMedicine(PageBean page, int type, JsonUtil jsonUtil,
	    String param, Object value);

    void getChineseOrWest(List<BackGroundMedicineVO<BaseModel>> models, List<Medicine> medicines);
    
}
