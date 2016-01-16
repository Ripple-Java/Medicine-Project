package com.rippletec.medicine.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;

import com.rippletec.medicine.vo.web.BackGroundMedicineVO;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.dao.ChineseMedicineDao;
import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.dao.EnterpriseMedicineTypeDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.BackGroundMedicineType;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.service.EnterChineseMedicineManager;
import com.rippletec.medicine.utils.ParamMap;
import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.web.EnterChineseVO;

@Repository(EnterChineseMedicineManager.NAME)
public class EnterChineseMedicineManagerImpl extends BaseManager<EnterChineseMedicine> implements EnterChineseMedicineManager{

    @Resource(name = EnterChineseMedicineDao.NAME)
    private EnterChineseMedicineDao enterChineseMedicineDao;
    
    @Resource(name = ChineseMedicineDao.NAME)
    private ChineseMedicineDao chineseMedicineDao;
    
    @Resource(name = EnterpriseMedicineTypeDao.NAME)
    private EnterpriseMedicineTypeDao enterpriseMedicineTypeDao;
    
    @Override
    protected FindAndSearchDao<EnterChineseMedicine> getDao() {
	return this.enterChineseMedicineDao;
    }

    @Override
    public CheckData addMedicine(Enterprise enterprise,
	    ChineseMedicine chineseMedicine, EnterChineseVO entChineseVO) {
	Medicine medicine = new Medicine(Medicine.ENTER_CHINESE, enterprise.getId());
	MedicineType medicineType = chineseMedicine.getMedicineType();
	EnterpriseMedicineType enterpriseMedicineType = null;
	List<EnterpriseMedicineType> enterpriseMedicineTypes = enterpriseMedicineTypeDao.findBySql(EnterpriseMedicineType.TABLE_NAME,EnterpriseMedicineType.BACKGROUND_MEDICINETYPE_ID, medicineType.getBackGroundMedicineType().getId());
	if(StringUtil.isEmpty(enterpriseMedicineTypes)){
	    enterpriseMedicineType = new EnterpriseMedicineType(medicineType, enterprise);
	}else {
	    enterpriseMedicineType = enterpriseMedicineTypes.get(0);
	}
	
	EnterChineseMedicine enterChineseMedicine = new EnterChineseMedicine(medicine, chineseMedicine.getMedicineType(), enterprise, entChineseVO, StringUtil.toPinYin(entChineseVO.getName()), new Date());
	enterChineseMedicine.setSortKey(StringUtil.toPinYin(entChineseVO.getName()));
	enterChineseMedicine.setChineseMedicine(chineseMedicine);
	enterChineseMedicine.setStatus(EnterChineseMedicine.ON_PUBLISTH);
	enterChineseMedicine.setEnterpriseMedicineType(enterpriseMedicineType);
	int object_id = enterChineseMedicineDao.save(enterChineseMedicine);
	if(object_id <= 0)
	    return null;
	return (new CheckData(enterprise, entChineseVO.getName() ,object_id, CheckData.TYPE_MEDICINE_CHINESE, null, new Date(), CheckData.CHECKING));
    }
    

    @Override
    public Result active(int id) throws DaoException {
	EnterChineseMedicine medicine = enterChineseMedicineDao.find(id);
	medicine.setStatus(EnterChineseMedicine.ON_PUBLISTH);
	enterChineseMedicineDao.update(medicine);
	return new Result(true);
    }

	@Override
	public List<BackGroundMedicineVO> searchBackGroundVO(String keyword, String param) {
		List<BackGroundMedicineVO> res = new CopyOnWriteArrayList<>();
		List<EnterChineseMedicine> enterChineseMedicines = new CopyOnWriteArrayList<>();
		enterChineseMedicines = enterChineseMedicineDao.search(param,keyword);
		if(enterChineseMedicines == null)
			return  res;
		for (EnterChineseMedicine enterChineseMedicine : enterChineseMedicines) {
		    res.add(new BackGroundMedicineVO(enterChineseMedicine.getMedicineType().getBackGroundMedicineType(), enterChineseMedicine.getName(), enterChineseMedicine.getEnterprise_name(), enterChineseMedicine.getId(), enterChineseMedicine.getUpdateTime()));
		}
		return res;
	}

	@Override
	public List<BackGroundMedicineVO> getBackGroundVO(
		Enterprise enterprise, int page, int pageSize) {
	    List<BackGroundMedicineVO> res = new CopyOnWriteArrayList<>();
		List<EnterChineseMedicine> enterChineseMedicines = new CopyOnWriteArrayList<>();
		PageBean pageBean = null;
		if(page > 0 && pageSize >0)
		    pageBean = new PageBean(page, 0, pageSize);
		enterChineseMedicines = enterChineseMedicineDao.findBySql(EnterChineseMedicine.TABLE_NAME, EnterChineseMedicine.ENTERPRISE_ID, enterprise.getId(), pageBean);
		if(enterChineseMedicines == null)
			return  res;
		for (EnterChineseMedicine enterChineseMedicine : enterChineseMedicines) {
		    res.add(new BackGroundMedicineVO(enterChineseMedicine.getMedicineType().getBackGroundMedicineType(), enterChineseMedicine.getName(), enterChineseMedicine.getEnterprise_name(), enterChineseMedicine.getId(), enterChineseMedicine.getUpdateTime()));
		}
		return res;
	}

	@Override
	public boolean deleteMedicine(int id, Integer enterpriseId) {
	    ParamMap paramMap = new ParamMap().put(EnterChineseMedicine.ID, id).put(EnterChineseMedicine.ENTERPRISE_ID, enterpriseId);
	    List<EnterChineseMedicine> target = enterChineseMedicineDao.findBySql(EnterChineseMedicine.TABLE_NAME, paramMap);
	    if(target != null && target.size() > 0){
		int enterTypeId = target.get(0).getEnterpriseMedicineType().getId();
		List<EnterChineseMedicine> enterChineseMedicines = enterChineseMedicineDao.findBySql(EnterChineseMedicine.TABLE_NAME, EnterChineseMedicine.ENTER_MEDICINE_TYPE_ID, enterTypeId);
		if(StringUtil.isEmpty(enterChineseMedicines)){
		    enterpriseMedicineTypeDao.delete(enterTypeId);
		}
		enterChineseMedicineDao.delete(id);
		return true;
	    }
	    return false;
	}

	@Override
	public List<BackGroundMedicineVO> searchBackGroundVO(String keyword,
		String field, String param, Object value) {
	    List<BackGroundMedicineVO> res = new CopyOnWriteArrayList<>();
		List<EnterChineseMedicine> enterChineseMedicines = new CopyOnWriteArrayList<>();
		enterChineseMedicines = enterChineseMedicineDao.search(EnterChineseMedicine.TABLE_NAME, field,keyword, param ,value);
		if(enterChineseMedicines == null)
			return  res;
		for (EnterChineseMedicine enterChineseMedicine : enterChineseMedicines) {
		    res.add(new BackGroundMedicineVO(enterChineseMedicine.getMedicineType().getBackGroundMedicineType(), enterChineseMedicine.getName(), enterChineseMedicine.getEnterprise_name(), enterChineseMedicine.getId(), enterChineseMedicine.getUpdateTime()));
		}
		return res;
	}

	@Override
	public Result updateMedicine(int id, EnterChineseVO entChineseVO,
		Enterprise enterprise) throws DaoException {
	    ParamMap paramMap = new ParamMap().put(EnterChineseMedicine.ID,id).put(EnterChineseMedicine.ENTERPRISE_ID, enterprise.getId());
	    List<EnterChineseMedicine> medicines = enterChineseMedicineDao.findBySql(EnterChineseMedicine.TABLE_NAME, paramMap);
	    if(StringUtil.isEmpty(medicines)){
		return new Result(false, "此药品不存在");
	    }
	    EnterChineseMedicine medicine = medicines.get(0);
	    ChineseMedicine chineseMedicine = chineseMedicineDao.find(entChineseVO.getMedicineId());
	    
	    int oldTypeId = medicine.getEnterpriseMedicineType().getId();
	    BackGroundMedicineType backGroundMedicineType = chineseMedicine.getMedicineType().getBackGroundMedicineType();
	    List<EnterpriseMedicineType> enterpriseMedicineTypes = enterpriseMedicineTypeDao.findBySql(EnterpriseMedicineType.TABLE_NAME, EnterpriseMedicineType.BACKGROUND_MEDICINETYPE_ID, backGroundMedicineType.getId());
	    if(StringUtil.isEmpty(enterpriseMedicineTypes)){
		medicine.setEnterpriseMedicineType(new EnterpriseMedicineType(chineseMedicine.getMedicineType(), enterprise));
	    }else {
		medicine.setEnterpriseMedicineType(enterpriseMedicineTypes.get(0));
	    }
	    medicine.setUpdate(entChineseVO, chineseMedicine);
	    enterChineseMedicineDao.update(medicine);
	    List<EnterChineseMedicine> enterChineseMedicine_now = enterChineseMedicineDao.findBySql(EnterChineseMedicine.TABLE_NAME, EnterChineseMedicine.ENTER_MEDICINE_TYPE_ID, oldTypeId);
	    if(StringUtil.isEmpty(enterChineseMedicine_now)){
		enterpriseMedicineTypeDao.delete(oldTypeId);
	    }
	    return new Result(true);
	}

    @Override
    public EnterChineseMedicine getMedicine(int id, int enterpriseId) {
	ParamMap paramMap = new ParamMap().put(EnterChineseMedicine.ID, id)
					  .put(EnterChineseMedicine.ENTERPRISE_ID, enterpriseId);
	List<EnterChineseMedicine> enterChineseMedicines = enterChineseMedicineDao.findBySql(EnterChineseMedicine.TABLE_NAME, paramMap);
	if (StringUtil.isEmpty(enterChineseMedicines)) {
	    return null;
	}
	return enterChineseMedicines.get(0);
    }


}
