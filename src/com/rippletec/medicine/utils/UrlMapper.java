//package com.rippletec.medicine.utils;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//import org.springframework.stereotype.Repository;
//
//@Repository(UrlMapper.CLASS_NAME)
//public class UrlMapper {
//    
//    public static final String CLASS_NAME = "UrlMapper";
//    
//    //app url路径匹配，根路径（/MedicineProject/App）
//    public static final String app_user_getEnterprise  = "/res/getEnterprise";
//    public static final String app_user_getEnterContent  = "/user/getEnterContent";
//    public static final String app_user_getEnterMedicineType  = "/user/getEnterMedicineType";
//    public static final String app_user_getEnterMedicine  = "/user/getEnterMedicine";
//    public static final String app_user_getMedicineType  = "/user/getMedicineType";
//    public static final String app_user_getMedicine  = "/user/getMedicine";
//    public static final String app_user_getMedicineDom  = "/user/getMedicineDom";
//    public static final String app_user_getAllSearch  = "/user/getAllSearch";
//    public static final String app_user_getUserInfo  = "/user/getUserInfo";
//    public static final String app_user_updateDBversion  = "/user/updateDBversion";
//    public static final String app_user_getRecentMeeting  = "/res/getRecentMeeting";
//    public static final String app_user_getMeetings  = "/user/getMeetings";
//    public static final String app_user_getRecentVideo  = "/res/getRecentVideo";
//    public static final String app_user_getVideoes  = "/user/getVideoes";
//    public static final String app_user_getFavorite  = "/user/getFavorite";
//    public static final String app_user_getEnterTypes  = "/user/getEnterTypes";
//    public static final String app_user_getEnterMedicines  = "/user/getEnterMedicines";
//    public static final String app_user_getMeetingById  = "/user/getMeetingById";
//    public static final String app_user_getVideoById  = "/user/getVideoById";
//    
//    public static final String app_user_deleteFavorite = "/user/deleteFavorite";
//    public static final String app_user_addFeedBack = "/user/addFeedBack"; 
//    public static final String app_res_getEnterprise = "/res/getEnterprise";
//    public static final String app_getVerificationCode = "/getVerificationCode";
//    public static final String app_user_setDocInfo = "/user/etDocInfo";
//    public static final String app_login = "/login";
//    public static final String app_loginOut = "/loginOut";
//    public static final String app_verifyCode = "/verifyCode";
//    public static final String app_register = "/register";
//    public static final String app_user_setStuInfo = "/user/setStuInfo";
//    public static final String app_user_updatePassword = "/user/updatePassword";
//    public static final String app_user_getBackPassword = "/getBackPassword";
//    public static final String app_user_updateUserInfo = "/user/updateUserInfo";
//    public static final String app_checkCode = "/checkCode";
//    public static final String app_checkUpdate = "/checkUpdate";
//    
//    
//    
//    //后台管理url路径匹配，根路径（MedicineProject/Web）
//    public static final String web_admin_getMedicine  = "/admin/getMedicine";
//    public static final String web_admin_medicine_get_detail  = "/admin/medicine/get/detail";
//    public static final String web_admin_video_get  = "/admin/video/get";
//    public static final String web_admin_getCategoryMedicine  = "/admin/getCategoryMedicine";
//    public static final String web_user_getAllMedicineType  = "/user/getAllMedicineType";
//    public static final String web_admin_getMeetingBySubject  =  "/admin/getMeetingBySubject";
//    public static final String web_admin_getEnterprises   = "/admin/getEnterprises";
//    public static final String web_admin_getUsers  = "/admin/getUsers";
//    public static final String web_admin_getFeedBacks  = "/admin/getFeedBacks";
//    public static final String web_admin_getEnterCheck  = "/admin/getEnterCheck";
//    public static final String web_user_getSubject  = "/user/getSubject";
//    public static final String web_enterprise_getMeeting  = "/enterprise/getMeeting";
//    public static final String web_enterprise_getMeeting_detail  = "/enterprise/getMeeting/detail";
//    public static final String web_enterprise_video_get  = "/enterprise/video/get";
//    public static final String web_enterprise_video_get_detail  = "/enterprise/video/get/detail";
//    public static final String web_enterprise_info_get  = "/enterprise/info/get";
//    public static final String web_enterprise_feedback_get  =  "/enterprise/feedback/get";
//    public static final String web_enterprise_getCount  = "/enterprise/getCount";
//    public static final String web_enterprise_getMedicine  = "/enterprise/getMedicine";
//    public static final String web_enterprise_EnterMedicine_get  = "/enterprise/EnterMedicine/get";
//    
//    public static final String web_adminuser_login = "/adminuser/login";
//    public static final String web_admin_loginOut = "/admin/loginOut";
//    public static final String web_admin_upatePassword = "/admin/upatePassword";
//    public static final String web_admin_deleteMedicine = "/admin/deleteMedicine";
//    public static final String web_admin_addChinMedicine = "/admin/addChinMedicine";
//    public static final String web_admin_addWestMedicine = "/admin/addWestMedicine";
//    public static final String web_admin_updateWestMedicine = "/admin/updateWestMedicine";
//    public static final String web_admin_updateChinMedicine = "/admin/updateChinMedicine";
//    public static final String web_admin_getCount = "/admin/getCount";
//    public static final String web_admin_getCheckData = "/admin/getCheckData";
//    public static final String web_admin_passCheckData = "/admin/passCheckData";
//    public static final String web_admin_searchMedicine = "/admin/searchMedicine";
//    public static final String web_admin_deleteMeeting = "/admin/deleteMeeting";
//    public static final String web_admin_getMeetings = "/admin/getMeetings";
//    public static final String web_admin_searchMeeting = "/admin/searchMeeting";
//    public static final String web_admin_deleteEnterprise = "/admin/deleteEnterprise";
//    public static final String web_admin_searchEnterprise = "/admin/searchEnterprise";
//    public static final String web_admin_getUsersByType = "/admin/getUsersByType";
//    public static final String web_admin_searchUser = "/admin/searchUser";
//    public static final String web_admin_deleteFeedBack = "/admin/deleteFeedBack";
//    public static final String web_admin_flushJsonTypes = "/web/admin/flushJsonTypes";
//    public static final String web_admin_meeting_block = "/admin/meeting/block";
//    public static final String web_admin_feedback_get = "/admin/feedback/get";
//    
//    public static final String web_user_medicine_name_get  = "/user/medicine/name/get";
//    
//    
//    //root
//    public static final String root_share_medicine = "/share/medicine/{typeId}/{id}";
//    public static final String root_share_medicine_get = "/share/medicine/get";
//    public static final String root_updateApp = "/updateApp";
//    
//    //upload
//    public static final String upload_image_portrait = "/image/portrait";
//    public static final String upload_image_enterCheckImg = "/image/enterCheckImg";
//    public static final String upload_image_enterprise_logo = "/image/enterprise/logo";
//    
//    
//    
//    //app json返回字段匹配
//    public static final String[] app_user_getEnterprise_map = {"id","name"};
//    public static final String[] app_user_getEnterContent_map = {"id","name","email","logo","phone","enterpriseUrl"};
//    public static final String[] app_user_getEnterMedicineType_map = {"id","name","gib_type"};
//    public static final String[] app_user_getEnterMedicine_map = {"enterprise_name","price","id","name","content","efficacy","annouce","preparations","manual","store","category","other_name","current_application","pharmacolo","warn","adrs","interaction","dose_explain"};
//    public static final String[] app_user_getMedicineType_map = {"id","name"};
//    public static final String[] app_user_getMedicine_map = {"id","name","content","efficacy","annouce","preparations","manual","store","category","other_name","current_application","pharmacolo","warn","adrs","interaction","dose_explain"};
//    public static final String[] app_user_getMedicineDom_map = {"title","content","author","date"};
//    public static final String[] app_user_getAllSearch_map = {"id","name"};
//    public static final String[] app_user_getUserInfo_map = {"id","account","sex","birthday","cellphone","degree","email","certificateImg","name","updateTime"};
//    public static final String[] app_user_updateDBversion_map = {"enterChineseMedicines","medicine","medicineType","enterWestMedicines","chineseMedicines","westMedicines"};
//    public static final String[] app_user_getRecentMeeting_map = {"id","name","commitDate","content","enterpriseName","pageUrl","enterpriseName"};
//    public static final String[] app_user_getMeetings_map = {"id","name","commitDate","content","pageUrl","enterpriseName"};
//    public static final String[] app_user_getRecentVideo_map = {"id","name","speaker","subject","pageUrl","enterpriseName"};
//    public static final String[] app_user_getVideoes_map = {"id","name","speaker","subject","pageUrl","enterpriseName"};
//    public static final String[] app_user_getFavorite_map = {"id","objectId","type","info","name"};
//    public static final String[] app_user_getEnterTypes_map = {"thirdType_id","thirdType","secondType_id","secondType"};
//    public static final String[] app_user_getEnterMedicines_map = {"id","name","other_name"};
//    public static final String[] app_user_getMeetingById_map = {"id","name","commitDate","content","pageUrl","enterpriseName"};
//    public static final String[] app_user_getVideoById_map = {"id","name","speaker","subject","pageUrl","enterpriseName"};
//    
//    
//    //后台管理 json返回数据匹配
//    public static final String[] web_admin_getMedicine_map = {"enterpriseName","type","id","name"};
//    public static final String[] web_admin_medicine_get_detail_map = {"medicineType_id","id","name","content","efficacy","annouce","preparations","manual","store","category","other_name","current_application","pharmacolo","warn","adrs","interaction","dose_explain"};
//    public static final String[] web_admin_video_get_map = {"id","name","subject","pageUrl","status","modifyTime"};
//    public static final String[] web_admin_getCategoryMedicine_map = {"enterpriseName","type","id","name"};
//    public static final String[] web_user_getAllMedicineType_map = {"id","name"};
//    public static final String[] web_admin_getMeetingBySubject_map =  {"id","name","commitDate","pageUrl","subject"};
//    public static final String[] web_admin_getEnterprises_map  = {"name","id","type","status","userAccount"};
//    public static final String[] web_admin_getUsers_map = {"account","id","type","regeditTime","lastLogin","status"};
//    public static final String[] web_admin_getFeedBacks_map = {"id","content","time","qqNumber","phone","userAccount"};
//    public static final String[] web_admin_getEnterCheck_map = {"id","type","name","enterpriseUrl","enterpriseNumber","checkImg"};
//    public static final String[] web_user_getSubject_map = {"id","name","parent_id"};
//    public static final String[] web_enterprise_getMeeting_map = {"id","name","commitDate","pageUrl","subject","status","modifyTime"};
//    public static final String[] web_enterprise_getMeeting_detail_map = {"id","name","commitDate","pageUrl","subject","status","modifyTime","speaker","content"};
//    public static final String[] web_enterprise_video_get_map = {"id","name","subject","pageUrl","status","modifyTime"};
//    public static final String[] web_enterprise_video_get_detail_map = {"id","name","subject","pageUrl","status","modifyTime","speaker"};
//    public static final String[] web_enterprise_info_get_map = {"id","type","name","logo","phone","email","enterpriseUrl","enterpriseNumber","content"};
//    public static final String[] web_enterprise_feedback_get_map =  {"id","content","time","phone","qqNumber","userAccount"};
//    public static final String[] web_enterprise_getCount_map = {"count"};
//    public static final String[] web_enterprise_getMedicine_map = {"type","name","enterpriseName","id","updateTime"};
//    public static final String[] web_enterprise_EnterMedicine_get_map = {"west_medicineId","chinese_medicineId","price","id","name","content","efficacy","annouce","preparations","manual","store","category","other_name","current_application","pharmacolo","warn","adrs","interaction","dose_explain"};
//    
//    //通用接口 josn数据返回匹配
//    public static final String[] root_share_medicine_get_map = {"enterprise_name","price","id","name","content","efficacy","annouce","preparations","manual","store","category","other_name","current_application","pharmacolo","warn","interaction","dose_explain","adrs"};
//    public static final String[] web_user_medicine_name_get_map = {"name","id"};
//
//
//
// 
//
//
//
// 
//
//   
//
//   
//
//   
//
//
//
//   
//
// 
//
//   
//
//   
//
//  
//
//    
//
//   
//
//    
//
//    
//    private Map<String, String[]> urlMap = new HashMap<String, String[]>();
//
//    public UrlMapper() {
//	this.put(web_user_getAllMedicineType , web_user_getAllMedicineType_map)
//	    .put(app_user_getAllSearch , app_user_getAllSearch_map)
//	    .put(app_user_getEnterContent , app_user_getEnterContent_map)
//	    .put(app_user_getEnterMedicine , app_user_getEnterMedicine_map)
//	    .put(app_user_getEnterMedicines , app_user_getEnterMedicines_map)
//	    .put(app_user_getEnterMedicineType , app_user_getEnterMedicineType_map)
//	    .put(app_user_getEnterprise , app_user_getEnterprise_map)
//	    .put(app_user_getEnterTypes , app_user_getEnterTypes_map)
//	    .put(app_user_getFavorite , app_user_getFavorite_map)
//	    .put(app_user_getMedicine , app_user_getMedicine_map)
//	    .put(app_user_getMedicineDom , app_user_getMedicineDom_map)
//	    .put(app_user_getMedicineType , app_user_getMedicineType_map)
//	    .put(app_user_getMeetingById , app_user_getMeetingById_map)
//	    .put(app_user_getMeetings , app_user_getMeetings_map)
//	    .put(app_user_getRecentMeeting , app_user_getRecentMeeting_map)
//	    .put(app_user_getRecentVideo , app_user_getRecentVideo_map)
//	    .put(web_user_getSubject , web_user_getSubject_map)
//	    .put(app_user_getUserInfo , app_user_getUserInfo_map)
//	    .put(app_user_getVideoById , app_user_getVideoById_map)
//	    .put(app_user_getVideoes , app_user_getVideoes_map)
//	    .put(web_user_medicine_name_get , web_user_medicine_name_get_map)
//	    .put(app_user_updateDBversion , app_user_updateDBversion_map);
//	    
//	this.put(web_admin_getCategoryMedicine , web_admin_getCategoryMedicine_map)
//            .put(web_admin_getEnterCheck , web_admin_getEnterCheck_map)
//            .put(web_admin_getEnterprises , web_admin_getEnterprises_map)
//            .put(web_admin_getFeedBacks , web_admin_getFeedBacks_map)
//            .put(web_admin_getMedicine , web_admin_getMedicine_map)
//            .put(web_admin_getMeetingBySubject , web_admin_getMeetingBySubject_map)
//            .put(web_admin_getUsers , web_admin_getUsers_map)
//            .put(web_enterprise_EnterMedicine_get , web_enterprise_EnterMedicine_get_map)
//            .put(web_enterprise_feedback_get , web_enterprise_feedback_get_map)
//            .put(web_enterprise_getCount , web_enterprise_getCount_map)
//            .put(web_enterprise_getMedicine , web_enterprise_getMedicine_map)
//            .put(web_enterprise_getMeeting_detail , web_enterprise_getMeeting_detail_map)
//            .put(web_enterprise_getMeeting , web_enterprise_getMeeting_map)
//            .put(web_enterprise_info_get , web_enterprise_info_get_map)
//            .put(web_enterprise_video_get_detail , web_enterprise_video_get_detail_map)
//            .put(web_enterprise_video_get , web_enterprise_video_get_map);
//	
//	this.put(root_share_medicine_get , root_share_medicine_get_map);
//    }
//    
//    public Set<String> urlKey() {
//	return urlMap.keySet();
//    }
//    
//    public Collection<String[]> urlMapValue() {
//	return urlMap.values();
//    }
//    
//    public Map<String, String[]> getMapper() {
//	return urlMap;
//    }
//
//    private UrlMapper put(String key, String value[]) {
//	urlMap.put(key, value);
//	return this;
//    }
//
//}
