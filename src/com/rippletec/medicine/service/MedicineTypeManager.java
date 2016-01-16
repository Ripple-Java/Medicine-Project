package com.rippletec.medicine.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;

public interface MedicineTypeManager extends IManager<MedicineType> {
    public static final String NAME = "MedicineTypeManager";

    Map<String, Object> getMedicineByTypeId(int typeId, PageBean pageBean) throws DaoException;

    List<MedicineType> getTypeByParentId(int parentId);

    MedicineType isExist(MedicineType medicineType);
    
    Integer uniqueSave(MedicineType medicineType);
    
    Boolean flushJsonString();

    String getTypeJson();

    List<MedicineType> getAllChild(MedicineType medicineType);

    List<BackGroundMedicineVO> searchBackGroundVO(String keyword);

    List<BackGroundMedicineVO> getBackGroundMedicineVO(MedicineType medicineType);

    List<BackGroundMedicineVO> getEnterBackGroundMedicineVO(
	    MedicineType medicineType, int enterpriseId);

    MedicineType findType(int medicineType_id, int gibType);

}
