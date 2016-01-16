package com.rippletec.medicine.model.extend;

import com.rippletec.medicine.model.EnterWestMedicine;

/**
 * 企业西药Model拓展，用于添加的Bean属性
 * @author Liuyi
 *
 */
public class EnterWestMedicineBean extends EnterWestMedicine{

    private static final long serialVersionUID = 154470856542079305L;
    public static final String CLASS_NAME = EnterWestMedicine.CLASS_NAME;
    
    public int west_medicineId;
    
    public EnterWestMedicineBean() {
    }

    public EnterWestMedicineBean(EnterWestMedicine medicine) {
	super(medicine.getId(), medicine.getEnterprise_name(), medicine.getName(), medicine.getOther_name(), medicine.getContent(), 
		medicine.getCurrent_application(), medicine.getPharmacolo(), medicine.getWarn(), medicine.getAdrs(), medicine.getInteraction(), 
		medicine.getDose_explain(), medicine.getManual(), medicine.getPreparations(), medicine.getPrice(), medicine.getStatus(), medicine.getSortKey(), medicine.getUpdateTime());
	this.west_medicineId = medicine.getWestMedicine().getId();
	
    }
    
    
    
    

}
