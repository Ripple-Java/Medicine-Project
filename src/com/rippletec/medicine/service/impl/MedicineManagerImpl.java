package com.rippletec.medicine.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.ChineseMedicineDao;
import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.dao.EnterWestMedicineDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.MedicineDao;
import com.rippletec.medicine.dao.WestMedicineDao;
import com.rippletec.medicine.model.BaseModel;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;
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
    public boolean deleteMedicine(int id, int type) {
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
    public boolean getMedicine(PageBean page, int type, JsonUtil jsonUtil, String param, Object value) { 
	switch (type) {
	case Medicine.CHINESE:
	    List<BackGroundMedicineVO> ch_backGroundMedicineVO = new LinkedList<BackGroundMedicineVO>();
	    List<ChineseMedicine> chineseMedicines = chineseMedicineDao.findBySql(ChineseMedicine.TABLE_NAME,param, value, page);
	    if(chineseMedicines == null)
		return false;
	    for (ChineseMedicine chineseMedicine : chineseMedicines) {
		BackGroundMedicineVO backGroundMedicineVO = new BackGroundMedicineVO(chineseMedicine.getMedicineType().getBackGroundMedicineType(), chineseMedicine.getName(), null, chineseMedicine.getId());
		ch_backGroundMedicineVO.add(backGroundMedicineVO);
	    }
	    jsonUtil.setModelList(ch_backGroundMedicineVO);
	    break;
	case Medicine.WEST:
	    List<BackGroundMedicineVO> west_backGroundMedicineVO = new LinkedList<BackGroundMedicineVO>();
	    List<WestMedicine> westMedicines = westMedicineDao.findBySql(WestMedicine.TABLE_NAME,param, value, page);
	    if(westMedicines == null)
		return false;
	    for (WestMedicine westMedicine : westMedicines) {
		BackGroundMedicineVO backGroundMedicineVO = new BackGroundMedicineVO(westMedicine.getMedicineType().getBackGroundMedicineType(), westMedicine.getName(), null, westMedicine.getId());
		west_backGroundMedicineVO.add(backGroundMedicineVO);
	    }
	    jsonUtil.setModelList(west_backGroundMedicineVO);
	    break;
	case Medicine.ENTER_CHINESE:
	    List<BackGroundMedicineVO> ench_backGroundMedicineVO = new LinkedList<BackGroundMedicineVO>();
	    if(param != null && param.equals(ChineseMedicine.MEDICINE_TYPE_ID))
		param = EnterChineseMedicine.ENTER_MEDICINE_TYPE_ID;
	    List<EnterChineseMedicine> enterChineseMedicines = enterChineseMedicineDao.findBySql(EnterChineseMedicine.TABLE_NAME,param, value, page);
	    if(enterChineseMedicines == null)
		return false;
	    for (EnterChineseMedicine enterChineseMedicine : enterChineseMedicines) {
		BackGroundMedicineVO backGroundMedicineVO = new BackGroundMedicineVO(enterChineseMedicine.getMedicineType().getBackGroundMedicineType(), enterChineseMedicine.getName(), enterChineseMedicine.getEnterprise_name(), enterChineseMedicine.getId());
		ench_backGroundMedicineVO.add(backGroundMedicineVO);
	    }
	    jsonUtil.setModelList(ench_backGroundMedicineVO);
	    break;
	case Medicine.ENTER_WEST:
	    List<BackGroundMedicineVO> enwest_backGroundMedicineVO = new LinkedList<BackGroundMedicineVO>();
	    if(param != null && param.equals(ChineseMedicine.MEDICINE_TYPE_ID))
		param = EnterChineseMedicine.ENTER_MEDICINE_TYPE_ID;
	    List<EnterWestMedicine> enterWestMedicines = enterWestMedicineDao.findBySql(EnterWestMedicine.TABLE_NAME,param, value, page);
	    if(enterWestMedicines == null)
		return false;
	    for (EnterWestMedicine enterWestMedicine : enterWestMedicines) {
		BackGroundMedicineVO backGroundMedicineVO = new BackGroundMedicineVO(enterWestMedicine.getMedicineType().getBackGroundMedicineType(), enterWestMedicine.getName(), enterWestMedicine.getEnterprise_name(), enterWestMedicine.getId());
		enwest_backGroundMedicineVO.add(backGroundMedicineVO);
	    }
	    jsonUtil.setModelList(enwest_backGroundMedicineVO);
	    break;
	default:
	    return false;
	}
	return true;
    }

    @Override
    public boolean getMedicineByCategory(PageBean page, int type, int category,
	    JsonUtil jsonUtil) {
	return getMedicine(page, type, jsonUtil, ChineseMedicine.MEDICINE_TYPE_ID, category);
    }

    @Override
    public boolean getMedicine(PageBean page, int type, JsonUtil jsonUtil) {
	return getMedicine(page, type, jsonUtil, null, null);
    }

    @Override
    public void getChineseOrWest(List<BackGroundMedicineVO> models,
	    List<Medicine> medicines) {
	for (Medicine medicine : medicines) {
	    int bigType = medicine.getParentType();
	    switch (bigType) {
	    case Medicine.CHINESE:
		List<ChineseMedicine> chineseMedicines = chineseMedicineDao.findBySql(ChineseMedicine.TABLE_NAME, ChineseMedicine.MEDICINE_ID, medicine.getId());
		if(chineseMedicines != null){
		    ChineseMedicine medicineTemp = chineseMedicines.get(0);
		    models.add(new BackGroundMedicineVO(medicineTemp.getMedicineType().getBackGroundMedicineType(), medicineTemp.getName(), null, medicineTemp.getId()));
		}
		break;
	    case Medicine.WEST:
		List<WestMedicine> westMedicines = westMedicineDao.findBySql(WestMedicine.TABLE_NAME, WestMedicine.MEDICINE_ID, medicine.getId());
		if(westMedicines != null){
		    WestMedicine westMedicine = westMedicines.get(0);
		    models.add(new BackGroundMedicineVO(westMedicine.getMedicineType().getBackGroundMedicineType(), westMedicine.getName(), null, westMedicine.getId()));
		}
		break;
	    case Medicine.ENTER_CHINESE:
		List<EnterChineseMedicine> enterChineseMedicines = enterChineseMedicineDao.findBySql(EnterChineseMedicine.TABLE_NAME, EnterChineseMedicine.MEDICINE_ID, medicine.getId());
		if(enterChineseMedicines != null){
		    EnterChineseMedicine enterChineseMedicine = enterChineseMedicines.get(0);
		    models.add(new BackGroundMedicineVO(enterChineseMedicine.getMedicineType().getBackGroundMedicineType(), enterChineseMedicine.getName(), enterChineseMedicine.getEnterprise_name(), enterChineseMedicine.getId()));
		}
		break;
	    case Medicine.ENTER_WEST:
		List<EnterWestMedicine> enterWestMedicines = enterWestMedicineDao.findBySql(EnterWestMedicine.TABLE_NAME, EnterWestMedicine.MEDICINE_ID, medicine.getId());
		if(enterWestMedicines != null){
		    EnterWestMedicine enterWestMedicine = enterWestMedicines.get(0);
		    models.add(new BackGroundMedicineVO(enterWestMedicine.getMedicineType().getBackGroundMedicineType(), enterWestMedicine.getName(), enterWestMedicine.getEnterprise_name(), enterWestMedicine.getId()));
		}
		break;
	    default:
		break;
	    }
	}
    }

   
    
}
