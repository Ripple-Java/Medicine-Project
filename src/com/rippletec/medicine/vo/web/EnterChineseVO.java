package com.rippletec.medicine.vo.web;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class EnterChineseVO {

    // 中药药品名称
    @NotNull(message="药品名不能为空")
    private String name;

    // 药物成分
    private String content;

    // 功效主治
    private String efficacy;

    // 用药监护
    private String annouce;

    // 制剂规格
    private String preparations;

    // 用法用量
    private String manual;

    // 贮法
    private String store;

    // 管理分类
    private String category;

    // 药品价格
    @Digits(fraction=2, integer = 5,message="价格必须为小数点后两位")
    private Double price;
    
    // 通用药品id
    @NotNull(message="关联通用药品id不能为空")
    @Min(value=1,message="id不能小于1")
    private int medicineId;
    
    public EnterChineseVO() {
    }
    
    

    @Override
    public String toString() {
	return "EnterChineseVO [name=" + name + ", content=" + content
		+ ", efficacy=" + efficacy + ", annouce=" + annouce
		+ ", preparations=" + preparations + ", manual=" + manual
		+ ", store=" + store + ", category=" + category + ", price="
		+ price + ", medicineId=" + medicineId + "]";
    }



    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getEfficacy() {
        return efficacy;
    }

    public String getAnnouce() {
        return annouce;
    }

    public String getPreparations() {
        return preparations;
    }

    public String getManual() {
        return manual;
    }

    public String getStore() {
        return store;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    public void setAnnouce(String annouce) {
        this.annouce = annouce;
    }

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }
    
    

}
