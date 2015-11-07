package com.rippletec.medicine.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.EnterWestMedicineDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.EnterWestMedicineManager;
import com.rippletec.medicine.utils.StringUtil;
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
    public CheckData addMedicine(Enterprise enterprise,
	    WestMedicine westMedicine, EnterWestVO enterWestVO) {
	Medicine medicine = new Medicine(Medicine.ENTER_WEST);
	EnterWestMedicine enterWestMedicine = new EnterWestMedicine(medicine, westMedicine.getMedicineType(), enterprise, enterWestVO, StringUtil.toPinYin(enterWestVO.getName()));
	enterWestMedicine.setWestMedicine(westMedicine);
	int object_id = enterWestMedicineDao.save(enterWestMedicine);
	if(object_id <= 0)
	    return null;
	return new CheckData(enterprise, enterWestVO.getName(),object_id, CheckData.TYPE_MEDICINE_WEST, null, new Date(), CheckData.CHECKING);
    }

    @Override
    public void active(int id) {
	EnterWestMedicine medicine = enterWestMedicineDao.find(id);
	medicine.setStatus(EnterWestMedicine.ON_PUBLISTH);
	enterWestMedicineDao.update(medicine);
    }

    
}
