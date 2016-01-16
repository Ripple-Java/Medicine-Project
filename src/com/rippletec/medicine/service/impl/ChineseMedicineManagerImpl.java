package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.medicine.vo.web.ChMedicineVO;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.dao.ChineseMedicineDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.service.ChineseMedicineManager;

import java.util.LinkedList;
import java.util.List;

@Service(ChineseMedicineManager.NAME)
public class ChineseMedicineManagerImpl extends BaseManager<ChineseMedicine> implements
	ChineseMedicineManager {
    
    @Resource(name=ChineseMedicineDao.NAME)
    private ChineseMedicineDao chineseMedicineDao;

    @Override
    protected FindAndSearchDao<ChineseMedicine> getDao() {
	return this.chineseMedicineDao;
    }

    @Override
    public List<BackGroundMedicineVO> searchBackGroundVO(String keyword, String param) {
        List<BackGroundMedicineVO> res = new LinkedList<>();
        List<ChineseMedicine> chineseMedicines = search(param, keyword);
        if (chineseMedicines == null || chineseMedicines.size() < 1) {
            return  res;
        }
        for (ChineseMedicine chineseMedicine : chineseMedicines) {
            res.add(new BackGroundMedicineVO(chineseMedicine.getMedicineType().getBackGroundMedicineType(), chineseMedicine.getName(), null, chineseMedicine.getId(),null));
        }
        return res;
    }

    @Override
    public Result saveMedicine(ChMedicineVO chMedicineVO,
	    MedicineType medicineType) {
	Medicine medicine = new Medicine(Medicine.CHINESE, null);
	ChineseMedicine chineseMedicine = new ChineseMedicine(chMedicineVO, medicine, medicineType);
	chineseMedicineDao.save(chineseMedicine);
	return new Result(true);
    }

    @Override
    public Result updateMedicine(int id, ChMedicineVO chMedicineVO,
	    MedicineType medicineType) throws DaoException {
	ChineseMedicine chineseMedicine = chineseMedicineDao.find(id);
	if(chineseMedicine == null){
	    return new Result(false, "该药品不能存在");
	}
	chineseMedicine.setUpdate(chMedicineVO, medicineType);
	chineseMedicineDao.update(chineseMedicine);
	return new Result(true);
    }

}
