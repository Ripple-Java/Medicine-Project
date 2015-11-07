package com.rippletec.medicine.vo.app;

import javax.persistence.Column;

import com.rippletec.medicine.model.ChineseMedicine;

public class ChineseMedicineVO {
    
    private int id;
    
    // 中药药品名称
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
    private Double price;

    // 药品发布状态
    private Integer status;
    
    //排序关键字
    private String sortKey;
    
    private int medicine_id;
    
    private int medicine_type_id;
    
    public ChineseMedicineVO() {
    }
    
   

    public ChineseMedicineVO(ChineseMedicine chineseMedicine) {
	super();
	this.id = chineseMedicine.getId();
	this.name = chineseMedicine.getName();
	this.content = chineseMedicine.getContent();
	this.efficacy = chineseMedicine.getEfficacy();
	this.annouce = chineseMedicine.getAnnouce();
	this.preparations = chineseMedicine.getPreparations();
	this.manual = chineseMedicine.getManual();
	this.store = chineseMedicine.getStore();
	this.category = chineseMedicine.getCategory();
	this.price = chineseMedicine.getPrice();
	this.status = chineseMedicine.getStatus();
	this.sortKey = chineseMedicine.getSortKey();
	this.medicine_id = chineseMedicine.getMedicine().getId();;
	this.medicine_type_id = chineseMedicine.getMedicineType().getId();
    }



    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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
    
    
    
    
}
