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

    CheckData addMedicine(Enterprise enterprise, ChineseMedicine chineseMedicine, EnterChineseVO entChineseVO) throws DaoException;

    void active(int id) throws DaoException;

    List<BackGroundMedicineVO> searchBackGroundVO(String keyword, String param) throws DaoException;

    List<BackGroundMedicineVO> getBackGroundVO(Enterprise enterprise, int page,
	    int pageSize) throws DaoException;

    void deleteMedicine(int id, Integer enterpriseId) throws DaoException;

    List<BackGroundMedicineVO> searchBackGroundVO(String keyword, String field,
	    String param, Object value) throws DaoException;

    void updateMedicine(int id, EnterChineseVO entChineseVO, Enterprise enterprise) throws DaoException;

    EnterChineseMedicine getMedicine(int id, int enterpriseId) throws DaoException;

}
