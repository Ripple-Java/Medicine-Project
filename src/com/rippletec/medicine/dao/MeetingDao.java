package com.rippletec.medicine.dao;

import java.util.List;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.Meeting;

public interface MeetingDao  extends FindAndSearchDao<Meeting>{
    
    public static final String NAME = "MeetingDao";
    
    public List<Meeting> findByTime(PageBean page,String param, Object value) throws DaoException ;

}
