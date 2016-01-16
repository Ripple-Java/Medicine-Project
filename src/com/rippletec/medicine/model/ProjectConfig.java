package com.rippletec.medicine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

/**
 * 后台配置信息Model
 * @author Liuyi
 *
 */
@Entity
@Repository
@Table(name=ProjectConfig.TABLE_NAME)
public class ProjectConfig extends BaseModel{
    

    private static final long serialVersionUID = 7130240120721036059L;
    
    public static final String TABLE_NAME = "projectConfig";
    public static final String CLASS_NAME = "ProjectConfig";
    
    public static final Integer TYPE_ANDROID = 1;
    public static final Integer TYPE_IOS = 2;
    
    public static final String APP_VERSION = "app_version";
    public static final String IOS_SERSION = "ios_version";
    public static final String APP_SHOW_VERSION = "app_show_version";
    public static final String IOS_SHOW_SERSION = "ios_show_version";
    public static final String APP_UPDATE_MESSAGE = "app_update_message";
    public static final String IOS_UPDATE_MESSAGE = "ios_updtae_message";
    public static final String SUBJECT_RESPONSE_JSON = "subjcet_response_json";

    
    public static final String KEY = "con_key";
    public static final String VALUE = "con_value";
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length=100, nullable=false)
    private String con_key;
    
    @Column(columnDefinition="TEXT", nullable=false)
    private String con_value;
    
    public ProjectConfig() {
    }

    public ProjectConfig(String con_key, String con_value) {
	super();
	this.con_key = con_key;
	this.con_value = con_value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCon_key() {
        return con_key;
    }

    public void setCon_key(String con_key) {
        this.con_key = con_key;
    }

    public String getCon_value() {
        return con_value;
    }

    public void setCon_value(String con_value) {
        this.con_value = con_value;
    }
    
    

}
