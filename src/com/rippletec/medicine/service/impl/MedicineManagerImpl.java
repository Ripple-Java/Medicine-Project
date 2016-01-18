package com.rippletec.medicine.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.ChineseMedicineDao;
import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.dao.EnterWestMedicineDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.MedicineDao;
import com.rippletec.medicine.dao.WestMedicineDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.BackGroundMedicineTypeManager;
import com.rippletec.medicine.service.MedicineManager;
import com.rippletec.medicine.utils.JsonUtil;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;

@Service(MedicineManager.NAME)
public class MedicineManagerImpl extends BaseManager<Medicine> implements MedicineManager{
    
    @Resource(name=MedicineDao.NAME)
    private MedicineDao medicineDao;
    
    @Resource(name=ChineseMedicineDao.NAME)
    private ChineseMedicineDao chineseMedicineDao;
    
    @Resource(name = EnterChineseMedicineDao.NAME)
    private EnterChineseMedicineDao enterChineseMedicineDao;
    
    @Resource(name = EnterWestMedicineDao.NAME)
    private EnterWestMedicineDao enterWestMedicineDao;
    
    @Resource(name=WestMedicineDao.NAME)
    private WestMedicineDao westMedicineDao;
    
    @Resource(name=BackGroundMedicineTypeManager.NAME)
    private BackGroundMedicineTypeManager backGroundMedicineTypeManager;
    

    @Override
    protected FindAndSearchDao<Medicine> getDao() {
	return this.medicineDao;
    }

    @Override
    public boolean deleteMedicine(int id, int type) throws DaoException {
	switch (type) {
	case Medicine.CHINESE:
	    return chineseMedicineDao.delete(id);
	case Medicine.WEST:
	    return westMedicineDao.delete(id);
	case Medicine.ENTER_CHINESE:
	    return enterChineseMedicineDao.delete(id);
	case Medicine.ENTER_WEST:
	    return enterWestMedicineDao.delete(id);
	default:
	    return false;
	}
    }

    @Override
    public boolean getMedicine(PageBean page, int type, JsonUtil jsonUtil, String param, Object value) throws DaoException { 
	boolean res = false;
	switch (type) {
	case Medicine.CHINESE:
	    List<BackGroundMedicineVO> ch_backGroundMedicineVO = new LinkedList<BackGroundMedicineVO>();
	    List<ChineseMedicine> chineseMedicines = chineseMedicineDao.findBySql(ChineseMedicine.TABLE_NAME,param, value, page);
	    for (ChineseMedicine chineseMedicine : chineseMedicines) {
		BackGroundMedicineVO backGroundMedicineVO = new BackGroundMedicineVO(chineseMedicine.getMedicineType().getBackGroundMedicineType(), chineseMedicine.getName(), null, chineseMedicine.getId(), null);
		ch_backGroundMedicineVO.add(backGroundMedicineVO);
	    }
	    if(ch_backGroundMedicineVO.size() > 0){
		res = true;
		jsonUtil.setModels(ch_backGroundMedicineVO);
	    }
	    break;
	case Medicine.WEST:
	    List<BackGroundMedicineVO> west_backGroundMedicineVO = new LinkedList<BackGroundMedicineVO>();
	    List<WestMedicine> westMedicines = westMedicineDao.findBySql(WestMedicine.TABLE_NAME,param, value, page);
	    for (WestMedicine westMedicine : westMedicines) {
		BackGroundMedicineVO backGroundMedicineVO = new BackGroundMedicineVO(westMedicine.getMedicineType().getBackGroundMedicineType(), westMedicine.getName(), null, westMedicine.getId(), null);
		west_backGroundMedicineVO.add(backGroundMedicineVO);
	    }
	    if(west_backGroundMedicineVO.size() > 0){
		res = true;
		jsonUtil.setModels(west_backGroundMedicineVO);
	    }
	    break;
	case Medicine.ENTER_CHINESE:
	    List<BackGroundMedicineVO> ench_backGroundMedicineVO = new LinkedList<BackGroundMedicineVO>();
	    if(param != null && param.equals(ChineseMedicine.MEDICINE_TYPE_ID))
		param = EnterChineseMedicine.ENTER_MEDICINE_TYPE_ID;
	    List<EnterChineseMedicine> enterChineseMedicines = enterChineseMedicineDao.findBySql(EnterChineseMedicine.TABLE_NAME,param, value, page);
	    for (EnterChineseMedicine enterChineseMedicine : enterChineseMedicines) {
		BackGroundMedicineVO backGroundMedicineVO = new BackGroundMedicineVO(enterChineseMedicine.getMedicineType().getBackGroundMedicineType(), enterChineseMedicine.getName(), enterChineseMedicine.getEnterprise_name(), enterChineseMedicine.getId(), enterChineseMedicine.getUpdateTime());
		ench_backGroundMedicineVO.add(backGroundMedicineVO);
	    }
	    if (ench_backGroundMedicineVO.size() > 0) {
		res = true;
		jsonUtil.setModels(ench_backGroundMedicineVO);
	    }
	    break;
	case Medicine.ENTER_WEST:
	    List<BackGroundMedicineVO> enwest_backGroundMedicineVO = new LinkedList<BackGroundMedicineVO>();
	    if(param != null && param.equals(ChineseMedicine.MEDICINE_TYPE_ID))
		param = EnterChineseMedicine.ENTER_MEDICINE_TYPE_ID;
	    List<EnterWestMedicine> enterWestMedicines = enterWestMedicineDao.findBySql(EnterWestMedicine.TABLE_NAME,param, value, page);
	    for (EnterWestMedicine enterWestMedicine : enterWestMedicines) {
		BackGroundMedicineVO backGroundMedicineVO = new BackGroundMedicineVO(enterWestMedicine.getMedicineType().getBackGroundMedicineType(), enterWestMedicine.getName(), enterWestMedicine.getEnterprise_name(), enterWestMedicine.getId(), enterWestMedicine.getUpdateTime());
		enwest_backGroundMedicineVO.add(backGroundMedicineVO);
	    }
	    if(enwest_backGroundMedicineVO.size() > 0){
		res = true;
		jsonUtil.setModels(enwest_backGroundMedicineVO);
	    }
	    break;
	default:
	    return res;
	}
	return res;
    }

    @Override
    public boolean getMedicineByCategory(PageBean page, int type, int category,
	    JsonUtil jsonUtil) throws DaoException {
	return getMedicine(page, type, jsonUtil, ChineseMedicine.MEDICINE_TYPE_ID, category);
    }

    @Override
    public boolean getMedicine(PageBean page, int type, JsonUtil jsonUtil) throws DaoException {
	return getMedicine(page, type, jsonUtil, null, null);
    }

    @Override
    public void getChineseOrWest(List<BackGroundMedicineVO> models,
	    List<Medicine> medicines) throws DaoException {
	for (Medicine medicine : medicines) {
	    int bigType = medicine.getParentType();
	    switch (bigType) {
	    case Medicine.CHINESE:
		List<ChineseMedicine> chineseMedicines = chineseMedicineDao.findBySql(ChineseMedicine.TABLE_NAME, ChineseMedicine.MEDICINE_ID, medicine.getId());
		ChineseMedicine medicineTemp = chineseMedicines.get(0);
		models.add(new BackGroundMedicineVO(medicineTemp.getMedicineType().getBackGroundMedicineType(), medicineTemp.getName(), null, medicineTemp.getId(), null));
		break;
	    case Medicine.WEST:
		List<WestMedicine> westMedicines = westMedicineDao.findBySql(WestMedicine.TABLE_NAME, WestMedicine.MEDICINE_ID, medicine.getId());
		WestMedicine westMedicine = westMedicines.get(0);
		models.add(new BackGroundMedicineVO(westMedicine.getMedicineType().getBackGroundMedicineType(), westMedicine.getName(), null, westMedicine.getId(), null));
		break;
	    case Medicine.ENTER_CHINESE:
		List<EnterChineseMedicine> enterChineseMedicines = enterChineseMedicineDao.findBySql(EnterChineseMedicine.TABLE_NAME, EnterChineseMedicine.MEDICINE_ID, medicine.getId());
		EnterChineseMedicine enterChineseMedicine = enterChineseMedicines.get(0);
		models.add(new BackGroundMedicineVO(enterChineseMedicine.getMedicineType().getBackGroundMedicineType(), enterChineseMedicine.getName(), enterChineseMedicine.getEnterprise_name(), enterChineseMedicine.getId(), enterChineseMedicine.getUpdateTime()));
		break;
	    case Medicine.ENTER_WEST:
		List<EnterWestMedicine> enterWestMedicines = enterWestMedicineDao.findBySql(EnterWestMedicine.TABLE_NAME, EnterWestMedicine.MEDICINE_ID, medicine.getId());
		EnterWestMedicine enterWestMedicine = enterWestMedicines.get(0);
		models.add(new BackGroundMedicineVO(enterWestMedicine.getMedicineType().getBackGroundMedicineType(), enterWestMedicine.getName(), enterWestMedicine.getEnterprise_name(), enterWestMedicine.getId(), enterWestMedicine.getUpdateTime()));
		break;
	    default:
		break;
	    }
	}
    }

   
    
}
