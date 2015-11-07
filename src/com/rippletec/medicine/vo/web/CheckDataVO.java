package com.rippletec.medicine.vo.web;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.utils.DateUtil;

public class CheckDataVO {
    public static final String CLASS_NAME = "CheckDataVO";
    
    //id
    @NotNull(message="id不能为空")
    @Min(value=1,message="id不能小于1")
    public int id;
    
    public String name;
    
    //id
    @NotNull(message="类型不能为空")
    @Min(value=1,message="type不能小于1")
    public int type;
    
    public String enterprise;
    
    public String commitDate;
    
    public CheckDataVO() {
    }
    
    

    public CheckDataVO(int id,String name, int type, String enterprise,
	    String commitDate) {
	super();
	this.id = id;
	this.name = name;
	this.type = type;
	this.enterprise = enterprise;
	this.commitDate = commitDate;
    }


    

    public CheckDataVO(CheckData checkData) {
	this.id = checkData.getId();
	this.name = checkData.getObjectName();
	this.commitDate = DateUtil.getDate(checkData.getUploadDate());
	this.type = checkData.getType();
	this.enterprise = checkData.getEnterprise().getName();
    }


    

    public String getCommitDate() {
        return commitDate;
    }



    public void setCommitDate(String commitDate) {
        this.commitDate = commitDate;
    }



    public int getType() {
        return type;
    }



    public void setType(int type) {
        this.type = type;
    }



    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }



    public String getEnterprise() {
        return enterprise;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }


    
    
    
}
