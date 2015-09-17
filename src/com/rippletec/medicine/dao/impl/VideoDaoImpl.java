package com.rippletec.medicine.dao.impl;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.dao.VideoDao;
import com.rippletec.medicine.model.Video;

@Repository(VideoDao.NAME)
public class VideoDaoImpl extends BaseDaoImpl<Video> implements VideoDao{

    @Override
    public String getClassName() {
	return Video.CLASS_NAME;
    }

    @Override
    public Class<Video> getPersistClass() {
	return Video.class;
    }
    

}
