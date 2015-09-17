package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.MeetingDao;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.service.MeetingManager;

@Service(MeetingManager.NAME)
public class MeetingManagerImpl extends BaseManager<Meeting> implements MeetingManager{

    @Resource(name = MeetingDao.NAME)
    private MeetingDao meetingDao;
    
    @Override
    protected FindAndSearchDao<Meeting> getDao() {
	return this.meetingDao;
    }

}
