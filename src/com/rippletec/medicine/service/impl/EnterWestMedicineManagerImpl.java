package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.EnterWestMedicineDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.EnterWestMedicineManager;
import com.rippletec.medicine.vo.web.EnterWestVO;

@Repository(EnterWestMedicineManager.NAME)
public class EnterWestMedicineManagerImpl extends BaseManager<EnterWestMedicine> implements EnterWestMedicineManager{
    
    @Resource(name = EnterWestMedicineDao.NAME)
    private EnterWestMedicineDao enterWestMedicineDao;

    @Override
    protected FindAndSearchDao<EnterWestMedicine> getDao() {
	return this.enterWestMedicineDao;
    }

    @Override
    public boolean addMedicine(Enterprise enterprise,
	    WestMedicine westMedicine, EnterWestVO enterWestVO) {
	Medicine medicine = new Medicine(Medicine.ENTER_WEST);
	EnterWestMedicine enterWestMedicine = new EnterWestMedicine(medicine, westMedicine.getMedicineType(), enterprise, enterWestVO, "sortKey");
	enterWestMedicine.setWestMedicine(westMedicine);
	if(enterWestMedicineDao.save(enterWestMedicine) > 0)
	    return true;
	return false;
    }

    
}
