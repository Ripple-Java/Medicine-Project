package com.rippletec.medicine.utils;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.model.BackGroundMedicineType;
import com.rippletec.medicine.model.MedicineType;
import com.rippletec.medicine.service.BackGroundMedicineTypeManager;
import com.rippletec.medicine.service.ChineseMedicineManager;
import com.rippletec.medicine.service.MedicineTypeManager;
import com.rippletec.medicine.service.WestMedicineManager;

@Repository(InitDBUtil.NAME)
public class InitDBUtil {
    
    public static final String NAME = "InitDBUtil";
    
    @Resource(name = MedicineTypeManager.NAME)
    private  MedicineTypeManager medicineTypeManager; 
    
    @Resource(name = WestMedicineManager.NAME)
    private WestMedicineManager westMedicineManager;
    
    @Resource(name = ChineseMedicineManager.NAME)
    private ChineseMedicineManager chineseMedicineManager;
    
    @Resource(name = BackGroundMedicineTypeManager.NAME)
    private BackGroundMedicineTypeManager backGroundMedicineTypeManager;
    
    
    
    public  boolean setBackGroundVoToDatabase() {
   	List<MedicineType> medicineTypes = medicineTypeManager.findByParam(MedicineType.PARENT_TYPE_ID, MedicineType.DEFAULT_PARENT_ID);
   	for (MedicineType medicineType : medicineTypes) {
   	    System.out.println(medicineType.toString());
   	    List<MedicineType> firstTypes = medicineTypeManager.findByParam(MedicineType.PARENT_TYPE_ID, medicineType.getId());
   	    for (MedicineType medicineType1 : firstTypes) {
   		List<MedicineType> secondTypes = medicineTypeManager.findByParam(MedicineType.PARENT_TYPE_ID, medicineType1.getId());
   		for (MedicineType medicineType2 : secondTypes) {
   		    if(medicineType2.getBackGroundMedicineType() != null)
   			continue;
   		    if(medicineType2.getFlag()){
   			BackGroundMedicineType backGroundMedicineType = new BackGroundMedicineType(medicineType, medicineType1, medicineType2, new MedicineType());
			    backGroundMedicineType.setType(BackGroundMedicineType.TYPE_NORMAL);
			    medicineType2.setBackGroundMedicineType(backGroundMedicineType);
			    medicineTypeManager.update(medicineType2);
			    continue;
   		    }    
   		    List<MedicineType> thridTypes = medicineTypeManager.findByParam(MedicineType.PARENT_TYPE_ID, medicineType2.getId());
   		    for (MedicineType medicineType3 : thridTypes) {
   				if(medicineType3.getBackGroundMedicineType() != null)
   				    continue;
   			    BackGroundMedicineType backGroundMedicineType = new BackGroundMedicineType(medicineType, medicineType1, medicineType2, medicineType3);
   			    backGroundMedicineType.setType(BackGroundMedicineType.TYPE_NORMAL);
   			    medicineType3.setBackGroundMedicineType(backGroundMedicineType);
   			    medicineTypeManager.update(medicineType3);
   		    }
   		}
   	    }
   	}
   	
   	return true;
       }
       

}
