package com.rippletec.medicine.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.MeetingDao;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.MeetingManager;
import com.rippletec.medicine.vo.web.MeetingVo;

@Service(MeetingManager.NAME)
public class MeetingManagerImpl extends BaseManager<Meeting> implements MeetingManager{

    @Resource(name = MeetingDao.NAME)
    private MeetingDao meetingDao;
    
    @Override
    protected FindAndSearchDao<Meeting> getDao() {
	return this.meetingDao;
    }

    @Override
    public boolean addMeeting(Enterprise enterprise, MeetingVo meeting) {
	Meeting meetingModel = new Meeting(enterprise, meeting);
	meetingModel.setStatus(Meeting.ON_CHECKING);
	meetingModel.setCommitDate(new Date());
	return meetingDao.save(meetingModel) > 0 ? true : false;
    }

    @Override
    public List<Meeting> findRecentMeeting(PageBean pageBean ,String param, Object value) {
	return meetingDao.findByTime(pageBean, param ,value);
    }



}
