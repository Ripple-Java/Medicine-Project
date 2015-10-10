package com.rippletec.medicine.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.vo.web.MeetingVo;

public interface MeetingManager extends IManager<Meeting> {
    public static final String NAME = "MeetingManager";

    boolean addMeeting(Enterprise enterprise, MeetingVo meeting);

    List<Meeting> findRecentMeeting(PageBean pageBean , String param, Object value);

}
