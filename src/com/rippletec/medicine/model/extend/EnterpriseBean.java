package com.rippletec.medicine.model.extend;

import com.rippletec.medicine.model.Enterprise;

/**
 * 企业Model拓展，用于添加额外的Bean属性
 * @author Liuyi
 *
 */
public class EnterpriseBean extends Enterprise{

    private static final long serialVersionUID = 8058479494608329311L;
    public static final String CLASS_NAME = Enterprise.CLASS_NAME;
    
    
    public String userAccount;
    
    public EnterpriseBean() {
	
    }

    public EnterpriseBean(Enterprise enterprise) {
	this.userAccount = enterprise.getUser().getAccount();
	this.name = enterprise.getName();
	this.id = enterprise.getId();
	this.type = enterprise.getType();
	this.status = enterprise.getStatus();
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    
    

}
