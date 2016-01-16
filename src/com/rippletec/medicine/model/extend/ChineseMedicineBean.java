package com.rippletec.medicine.model.extend;

import com.rippletec.medicine.model.ChineseMedicine;


/**
 * 通用中药model扩展，用于添加额外的bean属性
 * @author Liuyi
 *
 */
public class ChineseMedicineBean extends ChineseMedicine {
    
    private static final long serialVersionUID = -5596782102316255827L;
    public static final String CLASS_NAME = ChineseMedicine.CLASS_NAME;
    public int medicineType_id;
    
    public ChineseMedicineBean() {
    }

    public ChineseMedicineBean(ChineseMedicine medicine) {
	super(medicine.getId(), medicine.getName(),medicine.getContent(), medicine.getEfficacy(), medicine.getAnnouce(), medicine.getPreparations(),
		medicine.getManual(), medicine.getManual(), medicine.getCategory(), medicine.getStatus(), medicine.getSortKey());
	this.medicineType_id = medicine.getMedicineType().getId();
    }

    public int getMedicineType_id() {
        return medicineType_id;
    }

    public void setMedicineType_id(int medicineType_id) {
        this.medicineType_id = medicineType_id;
    }
    
       

}
