package com.rippletec.medicine.model.extend;

import java.util.Map;

import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Meeting;

public class MeetingBean extends Meeting {

    public static final String CLASS_NAME = Meeting.CLASS_NAME;
    private static final long serialVersionUID = 5659427473348468304L;
    
    private String logoUrl;
    
    public MeetingBean() {
    }
    
    public MeetingBean(Meeting meeting, Map<String, String> cache) {
	super(meeting);
	String cacheLogo = cache.get(meeting.getEnterpriseName());
	if(cacheLogo == null){
	    Enterprise enterprise = meeting.getEnterprise();
	    cache.put(enterprise.getName(), enterprise.getLogo());
	    this.logoUrl = enterprise.getLogo();
	}
	else {
	    this.logoUrl = cacheLogo;
	}
    }
    
    public MeetingBean(Meeting meeting) {
 	super(meeting);
 	this.logoUrl = meeting.getEnterprise().getLogo();
     }


    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    
    

}
