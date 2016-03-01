package com.rippletec.medicine.model.extend;

import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;

/**
 * 企业中药Model拓展，用于添加额外的bean属性
 * @author Liuyi
 *
 */
public class EnterChMedicineBean extends EnterChineseMedicine{

    private static final long serialVersionUID = 7556807404358410657L;
    public static final String CLASS_NAME = EnterChineseMedicine.CLASS_NAME;
    
    public EnterChMedicineBean() {
    }
    
    public EnterChMedicineBean(EnterChineseMedicine medicine) {
	super(medicine.getId(), medicine.getMedicine(), medicine.getMedicineType(), medicine.getEnterpriseMedicineType(),
		medicine.getChineseMedicine(), medicine.getEnterprise(), medicine.getEnterprise_name(), medicine.getName(),
		medicine.getContent(), medicine.getEfficacy(), medicine.getAnnouce(), medicine.getPreparations(), medicine.getManual(),
		medicine.getStore(), medicine.getCategory(), medicine.getPrice(), medicine.getStatus(), medicine.getSortKey(), medicine.getUpdateTime());
	this.chinese_medicineId = medicine.getChineseMedicine().getId();
	this.other_name = medicine.getSortKey();
    }



    public int chinese_medicineId;
    
    public String other_name;
    

}
