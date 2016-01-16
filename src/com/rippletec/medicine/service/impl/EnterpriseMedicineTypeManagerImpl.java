package com.rippletec.medicine.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.ChineseMedicineDao;
import com.rippletec.medicine.dao.EnterpriseMedicineTypeDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.WestMedicineDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.EnterpriseMedicineTypeManager;
import com.rippletec.medicine.utils.ParamMap;

@Service(EnterpriseMedicineTypeManager.NAME)
public class EnterpriseMedicineTypeManagerImpl extends BaseManager<EnterpriseMedicineType> implements
	EnterpriseMedicineTypeManager {
    @Resource(name=EnterpriseMedicineTypeDao.NAME)
    private EnterpriseMedicineTypeDao enterpriseMedicineTypeDao;
    @Resource(name=ChineseMedicineDao.NAME)
    private ChineseMedicineDao chineseMedicineDao;
    @Resource(name=WestMedicineDao.NAME)
    private WestMedicineDao westMedicineDao;

    @Override
    protected FindAndSearchDao<EnterpriseMedicineType> getDao() {
	return this.enterpriseMedicineTypeDao;
    }

    @Override
    public Map<String, Object> getMedicinesByTypeId(int typeId, int size, int currentPage) throws DaoException {
	EnterpriseMedicineType enterpriseMedicineType  = enterpriseMedicineTypeDao.find(typeId);
	Map<String, Object> res = new HashMap<String, Object>();
	int type = enterpriseMedicineType.getGib_type();
	if( type == EnterpriseMedicineType.CHINESE){
	    List<ChineseMedicine> medicines = chineseMedicineDao.findBySql(ChineseMedicine.TABLE_NAME, ChineseMedicine.ENTER_MEDICINE_TYPE_ID, typeId, new PageBean(currentPage, 0, size));
	    res.put("medicines", medicines);
	}
	else{
	    List<WestMedicine> medicines = westMedicineDao.findBySql(WestMedicine.TABLE_NAME, WestMedicine.ENTER_MEDICINE_TYPE_ID, typeId,new PageBean(currentPage, 0, size));
	    res.put("medicines", medicines);
	}
	res.put("type", type);
	return res;
    }

    @Override
    public List<EnterpriseMedicineType> getTypesByEnterpriseId(int id) {
	return enterpriseMedicineTypeDao.findBySql(EnterpriseMedicineType.TABLE_NAME, EnterpriseMedicineType.ENTERPRISE_ID, id);
    }

    @Override
    public List<EnterpriseMedicineType> getTypes(int enterpriseId, int type) {
	ParamMap paramMap = new ParamMap().put(EnterpriseMedicineType.ENTERPRISE_ID, enterpriseId)
					  .put(EnterpriseMedicineType.GIB_TYPE, type);
	return enterpriseMedicineTypeDao.findBySql(EnterpriseMedicineType.TABLE_NAME, paramMap);
    }
    
}
