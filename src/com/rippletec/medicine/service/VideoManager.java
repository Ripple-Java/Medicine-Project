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

    List<Video> findRecentMeeting(PageBean pageBean, String param, Object value) throws DaoException;

    void active(int id) throws DaoException;

    void deleteVideo(int id, Integer enterpriseId) throws DaoException;

    List<Video> findBySubject(Integer id, Integer enterpriseId) throws DaoException;

    void addVideo(Enterprise enterprise, VideoVO video, Subject subject) throws DaoException;

    void updateVideo(int videoId, int enterpriseId, VideoVO video,
	    Subject subject) throws DaoException;

    void block(int id) throws DaoException;

    void unblock(int id) throws DaoException;

    Video getVideo(int id, int enterpriseId) throws DaoException;

}
