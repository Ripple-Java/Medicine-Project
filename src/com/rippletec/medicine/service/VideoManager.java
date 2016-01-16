package com.rippletec.medicine.service;

import java.util.Collection;
import java.util.List;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Subject;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.vo.web.VideoVO;

public interface VideoManager extends IManager<Video> {
    
    public static final String NAME = "VideoManager";

    List<Video> findRecentMeeting(PageBean pageBean, String param, Object value);

    Result active(int id) throws DaoException;

    Result deleteVideo(int id, Integer enterpriseId);

    List<Video> findBySubject(Integer id, Integer enterpriseId);

    Result addVideo(Enterprise enterprise, VideoVO video, Subject subject);

    Result updateVideo(int videoId, int enterpriseId, VideoVO video,
	    Subject subject);

    Result block(int id) throws DaoException;

    Result unblock(int id) throws DaoException;

    Video getVideo(int id, int enterpriseId);

}
