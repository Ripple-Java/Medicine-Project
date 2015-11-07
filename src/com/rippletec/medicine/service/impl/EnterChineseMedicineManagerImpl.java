package com.rippletec.medicine.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.mysql.fabric.xmlrpc.base.Data;
import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.service.EnterChineseMedicineManager;
import com.rippletec.medicine.utils.StringUtil;
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
    public CheckData addMedicine(Enterprise enterprise,
	    ChineseMedicine chineseMedicine, EnterChineseVO entChineseVO) {
	Medicine medicine = new Medicine(Medicine.ENTER_CHINESE);
	EnterChineseMedicine enterChineseMedicine = new EnterChineseMedicine(medicine, chineseMedicine.getMedicineType(), enterprise, entChineseVO, StringUtil.toPinYin(entChineseVO.getName()));
	enterChineseMedicine.setSortkey(StringUtil.toPinYin(entChineseVO.getName()));
	enterChineseMedicine.setChineseMedicine(chineseMedicine);
	int object_id = enterChineseMedicineDao.save(enterChineseMedicine);
	if(object_id <= 0)
	    return null;
	return (new CheckData(enterprise, entChineseVO.getName() ,object_id, CheckData.TYPE_MEDICINE_CHINESE, null, new Date(), CheckData.CHECKING));
    }

    @Override
    public boolean active(int id) {
	EnterChineseMedicine medicine = enterChineseMedicineDao.find(id);
	if(medicine == null)
	    return false;
	medicine.setStatus(EnterChineseMedicine.ON_PUBLISTH);
	enterChineseMedicineDao.update(medicine);
	return true;
    }
    
    

  

}
