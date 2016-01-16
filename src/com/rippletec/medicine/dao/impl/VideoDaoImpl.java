package com.rippletec.medicine.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.VideoDao;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.utils.StringUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

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

    @Override
    public List<Video> findByTime(PageBean page, String param, Object value) {
	if(page == null){
	    return findByParam(StringUtil.getSearchHql(Video.CLASS_NAME, param)+" order by passDate desc", new String[]{param}, new Object[]{value});
	}
	return findByPage(StringUtil.getSearchHql(Video.CLASS_NAME, param)+" order by passDate desc", param, value,page.getOffset(), page.getMaxSize());
    }

}
