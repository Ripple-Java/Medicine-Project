package com.rippletec.medicine.service;

import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.medicine.vo.web.EnterWestVO;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface EnterWestMedicineManager extends IManager<EnterWestMedicine> {
    
    public static final String NAME = "EnterWestMedicineManager";

    CheckData addMedicine(Enterprise enterprise, WestMedicine westMedicine,
	    EnterWestVO enterWestVO);

    Result active(int id) throws DaoException;

    List<BackGroundMedicineVO> searchBackGroundVO(String keyword, String param);

    List<BackGroundMedicineVO> getBackGroundVO(Enterprise enterprise, int page,
	    int pageSize);

    boolean deleteMedicine(int id, Integer enterpriseId);

    List<BackGroundMedicineVO> searchBackGroundVO(String keyword, String field,
	    String param, Object value);

    Result updateMedicine(int id, EnterWestVO enterWestVO, Enterprise enterprise) throws DaoException;

    EnterWestMedicine getMedicine(int id, int enterpriseId);
}
