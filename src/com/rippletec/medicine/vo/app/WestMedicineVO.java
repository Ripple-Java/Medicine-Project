package com.rippletec.medicine.vo.app;

import javax.persistence.Column;

import com.rippletec.medicine.model.WestMedicine;

public class WestMedicineVO {
    // 西药药品名称
    private String name;
    
    // 别名
    private String other_name;

    // 组成成分
    private String content;

    // 临床运用
    private String current_application;

    // 药理学
    private String pharmacolo;

    // 注意事项
    private String warn;

    // 不良反应
    private String ADRS;

    // 药物相互作用
    private String interaction;

    // 给药说明
    private String dose_explain;

    // 用法用量
    private String  manual;

    // 制剂与规格
    private String preparations;


    // 药品价格
    private Double price;
    
    // 药品发布状态
    private Integer status;
    
    private String sortKey;
    
    private int medicine_id;
    
    private int medicine_type_id;
    
    private int id;
    
    public WestMedicineVO() {
    }

    public WestMedicineVO(WestMedicine westMedicine) {
	super();
	this.name = westMedicine.getName();
	this.other_name = westMedicine.getOther_name();
	this.content = westMedicine.getContent();
	this.current_application = westMedicine.getCurrent_application();
	this.pharmacolo = westMedicine.getPharmacolo();
	this.warn = westMedicine.getWarn();
	ADRS = westMedicine.getADRS();
	this.interaction = westMedicine.getInteraction();
	this.dose_explain = westMedicine.getDose_explain();
	this.manual = westMedicine.getManual();
	this.preparations = westMedicine.getPreparations();
	this.price = westMedicine.getPrice();
	this.status = westMedicine.getStatus();
	this.sortKey = westMedicine.getSortKey();
	this.medicine_id = westMedicine.getMedicine().getId();
	this.medicine_type_id = westMedicine.getMedicineType().getId();
	this.id = westMedicine.getId();
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

    public String getPreparations() {
        return preparations;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStatus() {
        return status;
    }

    public String getSortKey() {
        return sortKey;
    }

    public int getMedicine_id() {
        return medicine_id;
    }

    public int getMedicine_type_id() {
        return medicine_type_id;
    }

    public int getId() {
        return id;
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

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public void setMedicine_id(int medicine_id) {
        this.medicine_id = medicine_id;
    }

    public void setMedicine_type_id(int medicine_type_id) {
        this.medicine_type_id = medicine_type_id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
}
