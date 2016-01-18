package com.rippletec.medicine.service;

import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.medicine.vo.web.WestMedicineVO;

import java.util.List;

public interface WestMedicineManager extends IManager<WestMedicine> {
    public static final String NAME = "WestMedicineManager";

    List<BackGroundMedicineVO> searchBackGroudVO( String keyword, String param) throws DaoException;

    int saveMedicine(WestMedicine westMedicine) throws DaoException;

    void saveMedicine(WestMedicineVO westMedicineVO, MedicineType medicineType) throws DaoException;

    void updateMedicine(int id, WestMedicineVO westMedicineVO,
	    MedicineType medicineType) throws DaoException;


}
