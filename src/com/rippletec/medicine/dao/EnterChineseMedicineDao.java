package com.rippletec.medicine.dao;

import com.rippletec.medicine.model.EnterChineseMedicine;


/**
 * @author Liuyi
 *
 */
public interface EnterChineseMedicineDao extends FindAndSearchDao<EnterChineseMedicine>{
    public static final String NAME = "EnterChineseMedicineDao";

    EnterChineseMedicine findByMedicineId(Integer id);

}
