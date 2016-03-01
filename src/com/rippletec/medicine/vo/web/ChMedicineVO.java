package com.rippletec.medicine.vo.web;

import javax.validation.constraints.NotNull;

public class ChMedicineVO {
    
    public static final String CLASS_NAME = "ChMedicineVO";
    
 // 中药药品名称
    @NotNull(message="药品名不能为空")
    public String name;

    // 药物成分
    public String content;

    // 功效主治
    public String efficacy;

    // 用药监护
    public String annouce;

    // 制剂规格
    public String preparations;

    // 用法用量
    public String manual;

    // 贮法
    public String store;

    // 管理分类
    public String category;
    
    public ChMedicineVO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEfficacy() {
        return efficacy;
    }

    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    public String getAnnouce() {
        return annouce;
    }

    public void setAnnouce(String annouce) {
        this.annouce = annouce;
    }

    public String getPreparations() {
        return preparations;
    }

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
    
    

}
