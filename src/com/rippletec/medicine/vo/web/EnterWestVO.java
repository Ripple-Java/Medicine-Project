package com.rippletec.medicine.vo.web;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EnterWestVO {
    
    // 西药药品名称
    @NotNull(message="药品名不能为空")
    private String name;

    // 别名
    private String other_name;

    // 组成成分
    private String content;

    // 临床运用
    private String current_application;

    // 药理学
    private String pharmacolo;

    // 药效学
    private String pk_pd;

    // 药动学
    private String pharmacokinetics;

    // 注意事项
    private String warn;

    // 不良反应
    private String ADRS;

    // 药物相互作用
    private String interaction;

    // 给药说明
    private String dose_explain;

    // 用法用量
    private String manual;

    // 成人用量
    private String adult_dose;

    // 国外用法用量参考
    private String foreign_dose;

    // 制剂与规格
    private String preparations;

    // 贮法
    private String store;

    // 药品价格
    @Digits(fraction=2, integer = 5,message="价格必须为小数点后两位")
    private Double price;
    
    // 通用药品id
    @NotNull(message="关联通用药品id不能为空")
    @Min(value=0,message="id不能小于0")
    private int medicineId;
    
    public EnterWestVO() {
    }
    
    

    @Override
    public String toString() {
	return "EnterWestVO [name=" + name + ", other_name=" + other_name
		+ ", content=" + content + ", current_application="
		+ current_application + ", pharmacolo=" + pharmacolo
		+ ", pk_pd=" + pk_pd + ", pharmacokinetics=" + pharmacokinetics
		+ ", warn=" + warn + ", ADRS=" + ADRS + ", interaction="
		+ interaction + ", dose_explain=" + dose_explain + ", manual="
		+ manual + ", adult_dose=" + adult_dose + ", foreign_dose="
		+ foreign_dose + ", preparations=" + preparations + ", store="
		+ store + ", price=" + price + ", medicineId=" + medicineId
		+ "]";
    }



    public String getName() {
        return name;
    }

    public String getOther_name() {
        return other_name;
    }

    public String getContent() {
        return content;
    }

    public String getCurrent_application() {
        return current_application;
    }

    public String getPharmacolo() {
        return pharmacolo;
    }

    public String getPk_pd() {
        return pk_pd;
    }

    public String getPharmacokinetics() {
        return pharmacokinetics;
    }

    public String getWarn() {
        return warn;
    }

    public String getADRS() {
        return ADRS;
    }

    public String getInteraction() {
        return interaction;
    }

    public String getDose_explain() {
        return dose_explain;
    }

    public String getManual() {
        return manual;
    }

    public String getAdult_dose() {
        return adult_dose;
    }

    public String getForeign_dose() {
        return foreign_dose;
    }

    public String getPreparations() {
        return preparations;
    }

    public String getStore() {
        return store;
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

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCurrent_application(String current_application) {
        this.current_application = current_application;
    }

    public void setPharmacolo(String pharmacolo) {
        this.pharmacolo = pharmacolo;
    }

    public void setPk_pd(String pk_pd) {
        this.pk_pd = pk_pd;
    }

    public void setPharmacokinetics(String pharmacokinetics) {
        this.pharmacokinetics = pharmacokinetics;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public void setADRS(String aDRS) {
        ADRS = aDRS;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public void setDose_explain(String dose_explain) {
        this.dose_explain = dose_explain;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public void setAdult_dose(String adult_dose) {
        this.adult_dose = adult_dose;
    }

    public void setForeign_dose(String foreign_dose) {
        this.foreign_dose = foreign_dose;
    }

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }
    
    

}
