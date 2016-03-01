package com.rippletec.medicine.vo.web;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.rippletec.medicine.model.ChineseMedicine;

public class EnterChineseVO extends ChineseMedicine{

    
    private static final long serialVersionUID = 1619755164813010857L;

    // 药品价格
    @Digits(fraction=2, integer = 5,message="价格必须为小数点后两位")
    private Double price;
    
    // 通用药品id
    @NotNull(message="关联通用药品id不能为空")
    @Min(value=1,message="id不能小于1")
    private int medicineId;
    
    public EnterChineseVO() {
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
