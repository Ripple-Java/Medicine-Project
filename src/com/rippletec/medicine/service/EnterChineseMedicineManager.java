package com.rippletec.medicine.service;

import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.medicine.vo.web.EnterChineseVO;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface EnterChineseMedicineManager extends IManager<EnterChineseMedicine> {
    public static final String NAME = "EnterChineseMedicineManager";

    CheckData addMedicine(Enterprise enterprise, ChineseMedicine chineseMedicine, EnterChineseVO entChineseVO);

    Result active(int id) throws DaoException;

    List<BackGroundMedicineVO> searchBackGroundVO(String keyword, String param);

    List<BackGroundMedicineVO> getBackGroundVO(Enterprise enterprise, int page,
	    int pageSize);

    boolean deleteMedicine(int id, Integer enterpriseId);

    List<BackGroundMedicineVO> searchBackGroundVO(String keyword, String field,
	    String param, Object value);

    Result updateMedicine(int id, EnterChineseVO entChineseVO, Enterprise enterprise) throws DaoException;

    EnterChineseMedicine getMedicine(int id, int enterpriseId);

}
