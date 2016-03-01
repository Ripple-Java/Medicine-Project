package com.rippletec.medicine.vo.web;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EnterWestVO extends WestMedicineVO{
    
 

    // 药品价格
    @Digits(fraction=2, integer = 5,message="价格必须为小数点后两位")
    private Double price;
    
    // 通用药品id
    @NotNull(message="关联通用药品id不能为空")
    @Min(value=0,message="id不能小于0")
    private int medicineId;
    
    public EnterWestVO() {
    }
    
    


    public Double getPrice() {
        return price;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }
    
    

}
