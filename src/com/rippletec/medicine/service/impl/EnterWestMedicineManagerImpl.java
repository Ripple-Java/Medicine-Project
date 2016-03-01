package com.rippletec.medicine.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;

import com.rippletec.medicine.vo.web.BackGroundMedicineVO;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.dao.EnterWestMedicineDao;
import com.rippletec.medicine.dao.EnterpriseMedicineTypeDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.WestMedicineDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.BackGroundMedicineType;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.EnterWestMedicineManager;
import com.rippletec.medicine.utils.ParamMap;
import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.web.EnterWestVO;

@Repository(EnterWestMedicineManager.NAME)
public class EnterWestMedicineManagerImpl extends BaseManager<EnterWestMedicine> implements EnterWestMedicineManager{
    
    @Resource(name = EnterWestMedicineDao.NAME)
    private EnterWestMedicineDao enterWestMedicineDao;
    
    @Resource(name = WestMedicineDao.NAME)
    private WestMedicineDao westMedicineDao;
    
    @Resource(name = EnterpriseMedicineTypeDao.NAME)
    private EnterpriseMedicineTypeDao enterpriseMedicineTypeDao;

    @Override
    protected FindAndSearchDao<EnterWestMedicine> getDao() {
	return this.enterWestMedicineDao;
    }

    @Override
    public CheckData addMedicine(Enterprise enterprise,
	    WestMedicine westMedicine, EnterWestVO enterWestVO) throws DaoException {
	Medicine medicine = new Medicine(Medicine.ENTER_WEST, enterprise.getId());
	MedicineType medicineType = westMedicine.getMedicineType();
	EnterWestMedicine enterWestMedicine = new EnterWestMedicine(medicine, medicineType, enterprise, enterWestVO, StringUtil.toPinYin(enterWestVO.getName()), new Date());
	EnterpriseMedicineType enterpriseMedicineType = null;
	List<EnterpriseMedicineType> enterpriseMedicineTypes;
	try {
	    enterpriseMedicineTypes = enterpriseMedicineTypeDao.findBySql(EnterpriseMedicineType.TABLE_NAME ,EnterpriseMedicineType.BACKGROUND_MEDICINETYPE_ID, medicineType.getBackGroundMedicineType());
	    enterpriseMedicineType = enterpriseMedicineTypes.get(0);
	} catch (DaoException e) {
	    enterpriseMedicineType = new EnterpriseMedicineType(medicineType, enterprise);
	}
	enterWestMedicine.setWestMedicine(westMedicine);
	enterWestMedicine.setEnterpriseMedicineType(enterpriseMedicineType);
	int object_id = enterWestMedicineDao.save(enterWestMedicine);
	return new CheckData(enterprise, enterWestVO.getName(),object_id, CheckData.TYPE_MEDICINE_WEST, null, new Date(), CheckData.CHECKING);
    }

    @Override
    public void active(int id) throws DaoException {
	EnterWestMedicine medicine = enterWestMedicineDao.find(id);
	medicine.setStatus(EnterWestMedicine.ON_PUBLISTH);
	enterWestMedicineDao.update(medicine);
    }

	@Override
	public List<BackGroundMedicineVO> searchBackGroundVO(String keyword, String param) throws DaoException {
		List<BackGroundMedicineVO> res = new CopyOnWriteArrayList<>();
		List<EnterWestMedicine> enterWestMedicines = new CopyOnWriteArrayList<>();
		enterWestMedicines = enterWestMedicineDao.search(param,keyword);
		for (EnterWestMedicine enterWestMedicine : enterWestMedicines) {
			res.add(new BackGroundMedicineVO(enterWestMedicine.getMedicineType().getBackGroundMedicineType(), enterWestMedicine.getName(), enterWestMedicine.getEnterprise_name(), enterWestMedicine.getId(), enterWestMedicine.getUpdateTime()));
		}
		return res;
	}

	@Override
	public List<BackGroundMedicineVO> getBackGroundVO(
		Enterprise enterprise, int page, int pageSize) throws DaoException {
	    List<BackGroundMedicineVO> res = new CopyOnWriteArrayList<>();
		List<EnterWestMedicine> enterWestMedicines = new CopyOnWriteArrayList<>();
		PageBean pageBean = null;
		if(page > 0 && pageSize >0)
		    pageBean = new PageBean(page, 0, pageSize);
		enterWestMedicines = enterWestMedicineDao.findBySql(EnterWestMedicine.TABLE_NAME, EnterWestMedicine.ENTERPRISE_ID, enterprise.getId(), pageBean);
		for (EnterWestMedicine enterWestMedicine : enterWestMedicines) {
			res.add(new BackGroundMedicineVO(enterWestMedicine.getMedicineType().getBackGroundMedicineType(), enterWestMedicine.getName(), enterWestMedicine.getEnterprise_name(), enterWestMedicine.getId(), enterWestMedicine.getUpdateTime()));
		}
		return res;
	}

	@Override
	public void deleteMedicine(int id, Integer enterpriseId) throws DaoException {
	    ParamMap paramMap = new ParamMap().put(EnterWestMedicine.ID, id).put(EnterWestMedicine.ENTERPRISE_ID, enterpriseId);
	    List<EnterWestMedicine> target = enterWestMedicineDao.findBySql(EnterWestMedicine.TABLE_NAME, paramMap);
		int enterTypeId = target.get(0).getEnterpriseMedicineType().getId();
		List<EnterWestMedicine> enterWestMedicines;
		try {
		    enterWestMedicines = enterWestMedicineDao.findBySql(EnterWestMedicine.TABLE_NAME, EnterChineseMedicine.ENTER_MEDICINE_TYPE_ID, enterTypeId);
		} catch (DaoException e) {
		    enterpriseMedicineTypeDao.delete(enterTypeId);
		}
		enterWestMedicineDao.delete(id);
	}

	@Override
	public List<BackGroundMedicineVO> searchBackGroundVO(String keyword,
		String field, String param, Object value) throws DaoException {
	   	List<BackGroundMedicineVO> res = new CopyOnWriteArrayList<>();
		List<EnterWestMedicine> enterWestMedicines = new CopyOnWriteArrayList<>();
		enterWestMedicines = enterWestMedicineDao.search(EnterWestMedicine.TABLE_NAME, field,keyword, param, value);
		for (EnterWestMedicine enterWestMedicine : enterWestMedicines) {
			res.add(new BackGroundMedicineVO(enterWestMedicine.getMedicineType().getBackGroundMedicineType(), enterWestMedicine.getName(), enterWestMedicine.getEnterprise_name(), enterWestMedicine.getId(), enterWestMedicine.getUpdateTime()));
		}
		return res;
	}

	@Override
	public void updateMedicine(int id, EnterWestVO enterWestVO,
		Enterprise enterprise) throws DaoException {
	    WestMedicine westMedicine = westMedicineDao.find(enterWestVO.getMedicineId());
	    ParamMap paramMap = new ParamMap().put(EnterWestMedicine.ID, id).put(EnterWestMedicine.ENTERPRISE_ID, enterprise.getId());
	    List<EnterWestMedicine> medicines = enterWestMedicineDao.findBySql(EnterWestMedicine.TABLE_NAME, paramMap);
	    EnterWestMedicine enterWestMedicine = medicines.get(0);
	    int oldTypeId = enterWestMedicine.getEnterpriseMedicineType().getId();
	    BackGroundMedicineType backGroundMedicineType = westMedicine.getMedicineType().getBackGroundMedicineType();
	    List<EnterpriseMedicineType> enterpriseMedicineTypes;
	    try {
		enterpriseMedicineTypes = enterpriseMedicineTypeDao.findBySql(EnterpriseMedicineType.TABLE_NAME, EnterpriseMedicineType.BACKGROUND_MEDICINETYPE_ID, backGroundMedicineType.getId());
		enterWestMedicine.setEnterpriseMedicineType(enterpriseMedicineTypes.get(0));
	    } catch (DaoException e) {
		enterWestMedicine.setEnterpriseMedicineType(new EnterpriseMedicineType(westMedicine.getMedicineType(), enterprise));
	    }
	    enterWestMedicine.setUpdate(enterWestVO, westMedicine);
	    enterWestMedicineDao.update(enterWestMedicine);
	    List<EnterWestMedicine> enterWestMedicines_now;
	    try {
		enterWestMedicines_now = enterWestMedicineDao.findBySql(EnterWestMedicine.TABLE_NAME, EnterChineseMedicine.ENTER_MEDICINE_TYPE_ID, oldTypeId);
	    } catch (DaoException e) {
		enterpriseMedicineTypeDao.delete(oldTypeId);
	    }
	}

	@Override
	public EnterWestMedicine getMedicine(int id, int enterpriseId) throws DaoException {
	    ParamMap paramMap = new ParamMap().put(EnterWestMedicine.ID, id)
			  .put(EnterWestMedicine.ENTERPRISE_ID, enterpriseId);
	    List<EnterWestMedicine> enterWestMedicines = enterWestMedicineDao.findBySql(EnterWestMedicine.TABLE_NAME, paramMap);
	    return enterWestMedicines.get(0);
	}


}
