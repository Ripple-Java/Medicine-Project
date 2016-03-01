package com.rippletec.medicine.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.VideoDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.Subject;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.service.VideoManager;
import com.rippletec.medicine.utils.ParamMap;
import com.rippletec.medicine.utils.StringUtil;
import com.rippletec.medicine.vo.web.VideoVO;


@Service(VideoManager.NAME)
public class VideoManagerImpl extends BaseManager<Video> implements VideoManager{
    
    @Resource(name=VideoDao.NAME)
    private VideoDao videoDao;

    @Override
    protected FindAndSearchDao<Video> getDao() {
	return this.videoDao;
    }

    @Override
    public List<Video> findRecentMeeting(PageBean pageBean, String param, Object value) throws DaoException {
	return videoDao.findByTime(pageBean,param,value);
    }

    @Override
    public void active(int id) throws DaoException {
	Video video = videoDao.find(id);
	video.setStatus(Video.ON_PUBLISTH);
	video.setPassDate(new Date());
	videoDao.update(video);
    }

    @Override
    public void deleteVideo(int id, Integer enterpriseId) throws DaoException {
	ParamMap paramMap = new ParamMap().put(Video.ID, id)
					  .put(Video.ENTERPRISE_ID, enterpriseId);
	List<Video> videos = videoDao.findBySql(Video.TABLE_NAME, paramMap);
	videoDao.delete(videos.get(0).getId());
    }

    @Override
    public List<Video> findBySubject(Integer id, Integer enterpriseId) throws DaoException {
	ParamMap paramMap = new ParamMap().put(Video.SUBJECT_ID, id)
		  .put(Video.ENTERPRISE_ID, enterpriseId);
	return videoDao.findBySql(Video.TABLE_NAME, paramMap);
    }

    @Override
    public void addVideo(Enterprise enterprise, VideoVO video, Subject subject) throws DaoException {
	Video saveVideo = new Video(enterprise, video, subject, Video.ON_PUBLISTH);
	saveVideo.setPassDate(new Date());
	videoDao.save(saveVideo);
    }

    @Override
    public void updateVideo(int videoId, int enterpriseId, VideoVO video,
	    Subject subject) throws DaoException {
	ParamMap paramMap = new ParamMap().put(Video.ID, videoId)
					  .put(Video.ENTERPRISE_ID, enterpriseId);
	List<Video> videos = videoDao.findBySql(Video.TABLE_NAME, paramMap);
	Video updateVideo = videos.get(0);
	updateVideo.setUpdate(video, subject);
	updateVideo.setModifyTime(new Date());
	videoDao.update(updateVideo);
    }

    @Override
    public void block(int id) throws DaoException {
	Video video = videoDao.find(id);
	video.setStatus(Video.ON_CLOSE);
	video.setModifyTime(new Date());
	videoDao.update(video);
    }

    @Override
    public void unblock(int id) throws DaoException {
	Video video = videoDao.find(id);
	video.setStatus(Video.ON_PUBLISTH);
	video.setModifyTime(new Date());
	videoDao.update(video);
    }

    @Override
    public Video getVideo(int id, int enterpriseId) throws DaoException {
	ParamMap paramMap = new ParamMap().put(Video.ID, id)
					  .put(Video.ENTERPRISE_ID, enterpriseId);
	List<Video> videos = videoDao.findBySql(Video.TABLE_NAME, paramMap);
	return videos.get(0);
    }
    

}
