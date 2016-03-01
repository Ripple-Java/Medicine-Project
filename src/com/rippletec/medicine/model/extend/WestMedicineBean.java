package com.rippletec.medicine.model.extend;

import com.rippletec.medicine.model.WestMedicine;

/**
 * 通用西药Model拓展，用于添加额外的Bean属性
 * @author Liuyi
 *
 */
public class WestMedicineBean extends WestMedicine{

    private static final long serialVersionUID = 6002410305514672151L;
    public static final String CLASS_NAME = WestMedicine.CLASS_NAME;
    
    public int medicineType_id;
    
    public WestMedicineBean() {
    }

    public WestMedicineBean(WestMedicine medicine) {
	super(medicine.getId(), medicine.getName(), medicine.getOther_name(), medicine.getContent(), medicine.getCurrent_application(),
		medicine.getPharmacolo(), medicine.getWarn(), medicine.getAdrs(), medicine.getInteraction(), medicine.getDose_explain(),
		medicine.getManual(), medicine.getPreparations(), medicine.getStatus(), medicine.getSortKey());
	this.medicineType_id = medicine.getMedicineType().getId();
    }

    public int getMedicineType_id() {
        return medicineType_id;
    }

    public void setMedicineType_id(int medicineType_id) {
        this.medicineType_id = medicineType_id;
    }
    
    
    
    

}
