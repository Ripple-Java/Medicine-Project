package com.rippletec.medicine.service;

import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.medicine.vo.web.ChMedicineVO;

import java.util.List;

public interface ChineseMedicineManager extends IManager<ChineseMedicine> {
    public static final String NAME = "ChineseMedicineManager";

    List<BackGroundMedicineVO> searchBackGroundVO(String keyword, String param);


    Result saveMedicine(ChMedicineVO chMedicineVO, MedicineType medicineType);


    Result updateMedicine(int id, ChMedicineVO chMedicineVO, MedicineType medicineType) throws DaoException;



}
