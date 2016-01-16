package com.rippletec.medicine.vo.web;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class VideoVO {
    
    @NotNull(message="视频名称不能为空")
    public String name;
    
    @NotNull(message="视频地址不能为空")
    public String pageUrl;
    
    public String speaker;
    
    @NotNull(message="科目不能为空")
    public Integer subject_id;
    
    public Date updateTime;
    
    public VideoVO() {
    }

    public VideoVO(String name, String pageUrl, String speaker, Integer subject_id) {
	super();
	this.name = name;
	this.pageUrl = pageUrl;
	this.speaker = speaker;
	this.subject_id = subject_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public Integer getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Integer subject_id) {
        this.subject_id = subject_id;
    }
    
    
    

}
