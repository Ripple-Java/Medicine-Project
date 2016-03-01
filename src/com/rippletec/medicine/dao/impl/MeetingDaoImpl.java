package com.rippletec.medicine.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.MeetingDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.utils.StringUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;


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

    @Override
    public List<Meeting> findByTime(PageBean page, String param, Object value) throws DaoException {
	if(page == null){
	    return findByParam(StringUtil.getSearchHql(Meeting.CLASS_NAME, param)+" order by commitDate desc",new String[]{param} ,new Object[]{value});
	}
	return findByPage(StringUtil.getSearchHql(Meeting.CLASS_NAME, param)+" order by commitDate desc", param ,value, page.getOffset(), page.getMaxSize());
    }
    

}
