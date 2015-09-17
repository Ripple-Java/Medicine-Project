package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.MeetingDao;
import com.rippletec.medicine.model.Meeting;


@Repository(MeetingDao.NAME)
public class MeetingDaoImpl extends BaseDaoImpl<Meeting> implements MeetingDao{

    @Override
    public String getClassName() {
	return Meeting.CLASS_NAME;
    }

    @Override
    public Class<Meeting> getPersistClass() {
	return Meeting.class;
    }
    

}
