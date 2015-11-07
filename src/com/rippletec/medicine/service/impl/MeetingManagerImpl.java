package com.rippletec.medicine.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.MeetingDao;
import com.rippletec.medicine.model.CheckData;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.CheckDataManager;
import com.rippletec.medicine.service.MeetingManager;
import com.rippletec.medicine.utils.ExcelUtil;
import com.rippletec.medicine.utils.FileUtil;
import com.rippletec.medicine.utils.PPTUtil;
import com.rippletec.medicine.vo.web.MeetingVo;

@Service(MeetingManager.NAME)
public class MeetingManagerImpl extends BaseManager<Meeting> implements MeetingManager{

    @Resource(name = MeetingDao.NAME)
    private MeetingDao meetingDao;
    
    @Resource(name = CheckDataManager.NAME)
    private CheckDataManager checkDataManager;
    
    @Override
    protected FindAndSearchDao<Meeting> getDao() {
	return this.meetingDao;
    }

    @Override
    public boolean addMeeting(Enterprise enterprise, MeetingVo meeting) {
	Meeting meetingModel = new Meeting(enterprise, meeting);
	meetingModel.setStatus(Meeting.ON_CHECKING);
	meetingModel.setCommitDate(new Date());
	int dataId = meetingDao.save(meetingModel);
	if(dataId <0 )
	    return false;
	CheckData checkData = new CheckData(enterprise, meeting.getName(),dataId, CheckData.TYPE_MEETING, null, new Date(), CheckData.CHECKING);
	return checkDataManager.save(checkData) > 0 ? true : false;
    }

    @Override
    public List<Meeting> findRecentMeeting(PageBean pageBean ,String param, Object value) {
	return meetingDao.findByTime(pageBean, param ,value);
    }

    @Override
    public void active(int id) {
	Meeting meeting = meetingDao.find(id);
	meeting.setStatus(Meeting.ON_PUBLISTH);
	String pptPath = meeting.getPPT();
	meeting.setPPT("App"+PPTUtil.separator+"ppt"+PPTUtil.separator+meeting.getId());
	meetingDao.update(meeting);
	File pptFile = new File(pptPath);
	if(!pptFile.exists())
	    return;
	String fileName = pptFile.getName();
	try {
	    PPTUtil.saveToImg(fileName.substring(0, fileName.indexOf(FileUtil.getSuffixByFilename(fileName))), pptFile, 2, "png");
	} catch (IOException e) {
	    logger.debug("ppt转图片失败");
	}
	return ;
    }



}
