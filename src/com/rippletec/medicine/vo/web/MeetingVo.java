package com.rippletec.medicine.vo.web;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

public class MeetingVo {
    
    public static final String CLASS_NAME = "MeetingVo";
    
    @NotNull(message="会议名不能为空")
    private String name;
    
    private String speaker;

    private String tag;
    
    @NotNull(message="日期不能为空")
    private Date date;

    private String PPT;

    private String video;
    
    @NotNull(message="内容不能为空")
    private String content;
    
    @NotNull(message="所属科目不能为空")
    private String subject;
    
    public MeetingVo() {
    }
    
    

    @Override
    public String toString() {
	return "MeetingVo [name=" + name + ", speaker=" + speaker + ", tag="
		+ tag + ", date=" + date + ", PPT=" + PPT + ", video=" + video
		+ ", content=" + content + ", subject=" + subject + "]";
    }



    public String getName() {
        return name;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getTag() {
        return tag;
    }

    public Date getDate() {
        return date;
    }

    public String getPPT() {
        return PPT;
    }

    public String getVideo() {
        return video;
    }

    public String getContent() {
        return content;
    }

    public String getSubject() {
        return subject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPPT(String pPT) {
        PPT = pPT;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    
    

}
