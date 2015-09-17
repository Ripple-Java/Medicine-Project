package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.VideoDao;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.service.VideoManager;


@Service(VideoManager.NAME)
public class VideoManagerImpl extends BaseManager<Video> implements VideoManager{
    
    @Resource(name=VideoDao.NAME)
    private VideoDao videoDao;

    @Override
    protected FindAndSearchDao<Video> getDao() {
	return this.videoDao;
    }
    

}
