package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.service.EnterChineseMedicineManager;
import com.rippletec.medicine.vo.web.EnterChineseVO;

@Repository(EnterChineseMedicineManager.NAME)
public class EnterChineseMedicineManagerImpl extends BaseManager<EnterChineseMedicine> implements EnterChineseMedicineManager{

    @Resource(name = EnterChineseMedicineDao.NAME)
    private EnterChineseMedicineDao enterChineseMedicineDao;
    
    @Override
    protected FindAndSearchDao<EnterChineseMedicine> getDao() {
	return this.enterChineseMedicineDao;
    }

    @Override
    public boolean addMedicine(Enterprise enterprise,
	    ChineseMedicine chineseMedicine, EnterChineseVO entChineseVO) {
	Medicine medicine = new Medicine(Medicine.ENTER_CHINESE);
	EnterChineseMedicine enterChineseMedicine = new EnterChineseMedicine(medicine, chineseMedicine.getMedicineType(), enterprise.getName(), entChineseVO.getName(),
		entChineseVO.getContent(), entChineseVO.getEfficacy(), entChineseVO.getAnnouce(), entChineseVO.getPreparations(), entChineseVO.getManual(), entChineseVO.getStore(),
		entChineseVO.getCategory(), entChineseVO.getPrice(), EnterChineseMedicine.ON_CHECKING);
	enterChineseMedicine.setSortkey("sortkey");
	enterChineseMedicine.setChineseMedicine(chineseMedicine);
	if(enterChineseMedicineDao.save(enterChineseMedicine) > 0)
	    return true;
	return false;
    }

  

}
