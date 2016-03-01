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
    List<Meeting> findRecentMeeting(PageBean pageBean , String param, Object value) throws DaoException;

    Result active(int id) throws DaoException;

    void addMeeting(Enterprise enterprise, MeetingVo meeting, Subject subject) throws DaoException;

    void deleteMeeting(int id, Integer enterpriseId) throws DaoException;

    void updateMeeting(int meetingId, int enterpriseId, MeetingVo meeting, Subject subject) throws DaoException;

    List< Meeting> findBySubject(Integer id, Integer enterpriseId) throws DaoException;

    Result block(int id) throws DaoException;

    void unblock(int id) throws DaoException;

    Meeting getMeeting(int id, int enterpriseId) throws DaoException;

}
