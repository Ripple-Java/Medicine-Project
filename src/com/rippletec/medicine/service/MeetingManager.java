package com.rippletec.medicine.service;

import java.util.Collection;
import java.util.List;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.Subject;
import com.rippletec.medicine.vo.web.MeetingVo;

public interface MeetingManager extends IManager<Meeting> {
    public static final String NAME = "MeetingManager";
    List<Meeting> findRecentMeeting(PageBean pageBean , String param, Object value);

    Result active(int id) throws DaoException;

    boolean addMeeting(Enterprise enterprise, MeetingVo meeting, Subject subject);

    boolean deleteMeeting(int id, Integer enterpriseId);

    Result updateMeeting(int meetingId, int enterpriseId, MeetingVo meeting, Subject subject);

    List< Meeting> findBySubject(Integer id, Integer enterpriseId);

    Result block(int id) throws DaoException;

    Result unblock(int id) throws DaoException;

    Meeting getMeeting(int id, int enterpriseId);

}
