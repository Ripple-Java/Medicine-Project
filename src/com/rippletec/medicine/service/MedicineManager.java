package com.rippletec.medicine.service;

import java.util.List;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.utils.JsonUtil;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;

public interface MedicineManager extends IManager<Medicine> {
    public static final String NAME = "MedicineManager";
    
    boolean deleteMedicine(int id , int type ) throws DaoException;
    
    boolean getMedicine(PageBean page,int type, JsonUtil jsonUtil) throws DaoException;

    boolean getMedicineByCategory(PageBean page, int type, int category,
	    JsonUtil jsonUtil) throws DaoException;

    boolean getMedicine(PageBean page, int type, JsonUtil jsonUtil,
	    String param, Object value) throws DaoException;

    void getChineseOrWest(List<BackGroundMedicineVO> models, List<Medicine> medicines) throws DaoException;

}
