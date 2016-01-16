package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import com.rippletec.medicine.vo.web.BackGroundMedicineVO;
import com.rippletec.medicine.vo.web.WestMedicineVO;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.WestMedicineDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.WestMedicineManager;

import java.util.LinkedList;
import java.util.List;

@Service(WestMedicineManager.NAME)
public class WestMedicineManagerImpl extends BaseManager<WestMedicine> implements WestMedicineManager{

    @Resource(name=WestMedicineDao.NAME)
    private WestMedicineDao westMedicineDao;
    
    @Override
    protected FindAndSearchDao<WestMedicine> getDao() {
	return this.westMedicineDao;
    }

    @Override
    public List<BackGroundMedicineVO> searchBackGroudVO(String keyword, String param) {
        List<BackGroundMedicineVO> res = new LinkedList<>();
        List<WestMedicine> westMedicines = search(param, keyword);
        if(westMedicines == null && westMedicines.size() <1)
            return res;
        for (WestMedicine westMedicine : westMedicines) {
            res.add(new BackGroundMedicineVO(westMedicine.getMedicineType().getBackGroundMedicineType(), westMedicine.getName(), null, westMedicine.getId(), null));
        }
        return res;
    }

    @Override
    public int saveMedicine(WestMedicine westMedicine) {
	westMedicine.setSortKey(westMedicine.getName());
	Medicine medicine = new Medicine(Medicine.WEST, null);
	westMedicine.setMedicine(medicine);
	return westMedicineDao.save(westMedicine);
    }

    @Override
    public Result saveMedicine(WestMedicineVO westMedicineVO,
	    MedicineType medicineType) {
	Medicine medicine = new Medicine(Medicine.WEST, null);
	WestMedicine westMedicine = new WestMedicine(westMedicineVO, medicine, medicineType);
	westMedicineDao.save(westMedicine);
	return new Result(true);
    }

    @Override
    public Result updateMedicine(int id, WestMedicineVO westMedicineVO,
	    MedicineType medicineType) throws DaoException {
	WestMedicine westMedicine = westMedicineDao.find(id);
	westMedicine.setUpdate(westMedicineVO, medicineType);
	westMedicineDao.update(westMedicine);
	return new Result(true);
    }

}
