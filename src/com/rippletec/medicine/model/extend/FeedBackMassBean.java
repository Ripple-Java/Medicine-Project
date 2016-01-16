package com.rippletec.medicine.model.extend;

import com.rippletec.medicine.model.FeedBackMass;
import com.rippletec.medicine.utils.StringUtil;



/**
 * 反馈Model拓展，用于添加额外的Bean属性
 * @author Liuyi
 *
 */
public class FeedBackMassBean extends FeedBackMass{

  
    private static final long serialVersionUID = 2849918973334926008L;
    public static final String CLASS_NAME = FeedBackMass.CLASS_NAME;
    
    public String userAccount;
    
    
    public FeedBackMassBean() {
    }
    
    

    public FeedBackMassBean(FeedBackMass feedBackMass) {
	this.userAccount = feedBackMass.getUser().getAccount();
	this.id = feedBackMass.getId();
	if(StringUtil.hasText(feedBackMass.getContent())){
	    this.content = feedBackMass.getContent();
	}
	this.time = feedBackMass.getTime();
	if(StringUtil.hasText(feedBackMass.getQqNumber())){
	    this.qqNumber = feedBackMass.getQqNumber();
	}
	if(StringUtil.hasText(feedBackMass.getPhone())){
	    this.phone = feedBackMass.getPhone();
	}
    }



    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    
    

}
