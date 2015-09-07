package com.rippletec.medicine.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.FindByPageDao;
import com.rippletec.medicine.dao.MedicineDocumentDao;
import com.rippletec.medicine.model.MedicineDocument;
import com.rippletec.medicine.service.MedicineDocumentManager;

@Service(MedicineDocumentManager.NAME)
public class MedicineDocumentManagerImpl extends BaseManager<MedicineDocument> implements MedicineDocumentManager{

    @Resource(name=MedicineDocumentDao.NAME)
    private MedicineDocumentDao medicineDocumentDao; 
    
    @Override
    protected FindByPageDao<MedicineDocument> getDao() {
	return this.medicineDocumentDao;
    }

    @Override
    public List<MedicineDocument> getDocument(int medicineId, int type) {
	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put(MedicineDocument.MEDICINE_ID, medicineId);
	paramMap.put(MedicineDocument.TYPE, type);
	return medicineDocumentDao.findBySql(MedicineDocument.TABLE_NAME,paramMap);
    }
    
    

}
