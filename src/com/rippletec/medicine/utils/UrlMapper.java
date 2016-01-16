package com.rippletec.medicine.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository(UrlMapper.CLASS_NAME)
public class UrlMapper {
    
    public static final String CLASS_NAME = "UrlMapper";
    
    public static final String user_getEnterprise = "id,name";

    public static final String user_getEnterContent_map = "id,name,email,logo,phone,enterpriseUrl";
    public static final String user_getEnterMedicineType_map = "id,name,gib_type";
    public static final String user_getEnterMedicine_map = "enterprise_name,price,id,name,content,efficacy,annouce,preparations,manual,store,category,other_name,current_application,pharmacolo,warn,adrs,interaction,dose_explain";
    public static final String user_getMedicineType_map = "id,name";
    public static final String user_getMedicine_map = "id,name,content,efficacy,annouce,preparations,manual,store,category,other_name,current_application,pharmacolo,warn,adrs,interaction,dose_explain";
    public static final String user_getMedicineDom_map = "title,content,author,date";
    public static final String user_getAllSearch_map = "id,name";
    public static final String user_getUserInfo_map = "id,account,sex,birthday,cellphone,degree,email,certificateImg,name,updateTime";
    public static final String user_updateDBversion_map = "enterChineseMedicines,medicine,medicineType,enterWestMedicines,chineseMedicines,westMedicines";
    public static final String user_getRecentMeeting_map = "id,name,commitDate,content,enterpriseName,pageUrl,enterpriseName";
    public static final String user_getMeetings_map = "id,name,commitDate,content,pageUrl,enterpriseName";
    public static final String user_getRecentVideo_map = "id,name,speaker,subject,pageUrl,enterpriseName";
    public static final String user_getVideoes_map = "id,name,speaker,subject,pageUrl,enterpriseName";
    public static final String user_getFavorite_map = "id,objectId,type,info,name";
    public static final String user_getEnterTypes_map = "thirdType_id,thirdType,secondType_id,secondType";
    public static final String user_getEnterMedicines_map = "id,name,other_name";
    public static final String user_getMeetingById_map = "id,name,commitDate,content,pageUrl,enterpriseName";
    public static final String user_getVideoById_map = "id,name,speaker,subject,pageUrl,enterpriseName";
    
    public static final String admin_getMedicine_map = "enterpriseName,type,id,name";
    public static final String admin_medicine_get_detail_map = "medicineType_id,id,name,content,efficacy,annouce,preparations,manual,store,category,other_name,current_application,pharmacolo,warn,adrs,interaction,dose_explain";
    public static final String admin_video_get_map = "id,name,subject,pageUrl,status,modifyTime";
    public static final String admin_getCategoryMedicine_map = "enterpriseName,type,id,name";
    public static final String user_getAllMedicineType_map = "id,name";
    public static final String admin_getMeetingBySubject_map =  "id,name,commitDate,pageUrl,subject";
    public static final String admin_getEnterprises_map  = "name,id,type,status,userAccount";
    public static final String admin_getUsers_map = "account,id,type,regeditTime,lastLogin,status";
    public static final String admin_getFeedBacks_map = "id,content,time,qqNumber,phone,userAccount";
    public static final String admin_getEnterCheck_map = "id,type,name,enterpriseUrl,enterpriseNumber,checkImg";
    public static final String user_getSubject_map = "id,name,parent_id";
    public static final String enterprise_getMeeting_map = "id,name,commitDate,pageUrl,subject,status,modifyTime";
    public static final String enterprise_getMeeting_detail_map = "id,name,commitDate,pageUrl,subject,status,modifyTime,speaker,content";
    public static final String enterprise_video_get_map = "id,name,subject,pageUrl,status,modifyTime";
    public static final String enterprise_video_get_detail_map = "id,name,subject,pageUrl,status,modifyTime,speaker";
    public static final String enterprise_info_get_map = "id,type,name,logo,phone,email,enterpriseUrl,enterpriseNumber,content";
    public static final String enterprise_feedback_get_map =  "id,content,time,phone,qqNumber,userAccount";
    public static final String enterprise_getCount_map = "count";
    public static final String enterprise_getMedicine_map = "type,name,enterpriseName,id,updateTime";
    public static final String enterprise_EnterMedicine_get_map = "west_medicineId,chinese_medicineId,price,id,name,content,efficacy,annouce,preparations,manual,store,category,other_name,current_application,pharmacolo,warn,adrs,interaction,dose_explain";

    private Map<String, String> urlMap = new HashMap<String, String>();

    public UrlMapper() {
		
	    }

    private UrlMapper put(String key, String value) {
	urlMap.put(key, value);
	return this;
    }

}
