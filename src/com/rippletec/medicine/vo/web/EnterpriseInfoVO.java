package com.rippletec.medicine.vo.web;

public class EnterpriseInfoVO {
    
    public String content;
    
    public String enterpriseUrl;
    
    public String phone;
    
    public EnterpriseInfoVO() {
    }
    
    

    public EnterpriseInfoVO(String content, String enterpriseUrl, String phone) {
	super();
	this.content = content;
	this.enterpriseUrl = enterpriseUrl;
	this.phone = phone;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEnterpriseUrl() {
        return enterpriseUrl;
    }

    public void setEnterpriseUrl(String enterpriseUrl) {
        this.enterpriseUrl = enterpriseUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    

}
