package com.rippletec.medicine.vo.web;

import javax.validation.constraints.NotNull;

public class WestMedicineVO {
    
    public static final String CLASS_NAME = "WestMedicineVO";
    
    // 西药药品名称
    @NotNull(message="药品名不能为空")
    public String name;

    // 别名
    public String other_name;

    // 组成成分
    public String content;

    // 临床运用
    public String current_application;

    // 药理学
    public String pharmacolo;

    // 注意事项
    public String warn;

    // 不良反应
    public String adrs;

    // 药物相互作用
    public String interaction;

    // 给药说明
    public String dose_explain;

    // 用法用量
    public String manual;

    // 制剂与规格
    public String preparations;
    
    public WestMedicineVO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCurrent_application() {
        return current_application;
    }

    public void setCurrent_application(String current_application) {
        this.current_application = current_application;
    }

    public String getPharmacolo() {
        return pharmacolo;
    }

    public void setPharmacolo(String pharmacolo) {
        this.pharmacolo = pharmacolo;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }


    public String getInteraction() {
        return interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public String getDose_explain() {
        return dose_explain;
    }

    public void setDose_explain(String dose_explain) {
        this.dose_explain = dose_explain;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public String getPreparations() {
        return preparations;
    }

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }

    public String getAdrs() {
        return adrs;
    }

    public void setAdrs(String adrs) {
        this.adrs = adrs;
    }
    
    
    
    

}
