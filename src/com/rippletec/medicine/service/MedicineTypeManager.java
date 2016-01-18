package com.rippletec.medicine.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.exception.UtilException;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;

public interface MedicineTypeManager extends IManager<MedicineType> {
    public static final String NAME = "MedicineTypeManager";

    Map<String, Object> getMedicineByTypeId(int typeId, PageBean pageBean) throws DaoException;

    List<MedicineType> getTypeByParentId(int parentId) throws DaoException;

    MedicineType isExist(MedicineType medicineType) throws DaoException;
    
    Integer uniqueSave(MedicineType medicineType) throws DaoException;
    
    Boolean flushJsonString() throws UtilException, DaoException;

    String getTypeJson();

    List<MedicineType> getAllChild(MedicineType medicineType) throws DaoException;

    List<BackGroundMedicineVO> searchBackGroundVO(String keyword) throws DaoException;

    List<BackGroundMedicineVO> getBackGroundMedicineVO(MedicineType medicineType);

    List<BackGroundMedicineVO> getEnterBackGroundMedicineVO(
	    MedicineType medicineType, int enterpriseId) throws DaoException;

    MedicineType findType(int medicineType_id, int gibType) throws DaoException;

}
