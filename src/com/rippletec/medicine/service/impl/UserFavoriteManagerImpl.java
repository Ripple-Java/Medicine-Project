package com.rippletec.medicine.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.bean.Result;
import com.rippletec.medicine.dao.ChineseMedicineDao;
import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.dao.EnterWestMedicineDao;
import com.rippletec.medicine.dao.EnterpriseDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.MeetingDao;
import com.rippletec.medicine.dao.UserDao;
import com.rippletec.medicine.dao.UserFavoriteDao;
import com.rippletec.medicine.dao.VideoDao;
import com.rippletec.medicine.dao.WestMedicineDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterWestMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Meeting;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.model.UserFavorite;
import com.rippletec.medicine.model.Video;
import com.rippletec.medicine.model.WestMedicine;
import com.rippletec.medicine.service.UserFavoriteManager;
import com.rippletec.medicine.service.UserManager;
import com.rippletec.medicine.utils.ErrorCode;
import com.rippletec.medicine.utils.ParamMap;


@Service(UserFavoriteManager.NAME)
public class UserFavoriteManagerImpl extends BaseManager<UserFavorite> implements UserFavoriteManager{
    
    @Resource(name = UserFavoriteDao.NAME)
    private UserFavoriteDao userFavoriteDao;

    @Resource(name = UserManager.NAME)
    private UserManager userManager;
    
    @Resource(name = ChineseMedicineDao.NAME)
    private ChineseMedicineDao chineseMedicineDao;
    
    @Resource(name = WestMedicineDao.NAME)
    private WestMedicineDao westMedicineDao;
    
    @Resource(name = EnterChineseMedicineDao.NAME)
    private EnterChineseMedicineDao enterChineseMedicineDao;
    
    @Resource(name = EnterWestMedicineDao.NAME)
    private EnterWestMedicineDao enterWestMedicineDao;
    
    @Resource(name = EnterpriseDao.NAME)
    private EnterpriseDao enterpriseDao;
    
    @Resource(name = VideoDao.NAME)
    private VideoDao videoDao;
    
    @Resource(name = MeetingDao.NAME)
    private MeetingDao meetingDao;
    
    @Override
    protected FindAndSearchDao<UserFavorite> getDao() {
	return this.userFavoriteDao;
    }

	@Override
	public Result addUserFavorite(String account, UserFavorite userFavorite) throws DaoException {
		User user = userManager.findByAccount(account);
		userFavorite.setUser(user);
		String info = "";
		String name = "";
		switch (userFavorite.getType()) {
		case UserFavorite.MEDICINE_CHINESE:
		    ChineseMedicine chineseMedicine = chineseMedicineDao.find(userFavorite.getObjectId());
		    name = chineseMedicine.getName();
		    info = chineseMedicine.getSortKey();
		    break;
		case UserFavorite.MEDICINE_WEST:
		    WestMedicine westMedicine = westMedicineDao.find(userFavorite.getObjectId());
		    info = westMedicine.getOther_name();
		    name = westMedicine.getName();
		    break;
		case UserFavorite.ENTER_CHINESE:
		    EnterChineseMedicine enterChineseMedicine = enterChineseMedicineDao.find(userFavorite.getObjectId());
		    info = enterChineseMedicine.getSortKey();
		    name = enterChineseMedicine.getName();
		    break;
		case UserFavorite.ENTER_WEST:
		    EnterWestMedicine enterWestMedicine = enterWestMedicineDao.find(userFavorite.getObjectId());
		    info = enterWestMedicine.getOther_name();
		    name = enterWestMedicine.getName();
		    break;
		case UserFavorite.ENTERPRISE:
		    Enterprise enterprise = enterpriseDao.find(userFavorite.getObjectId());
		    info = enterprise.getLogo();
		    name = enterprise.getName();
		    break;
		case UserFavorite.VIDEO:
		    Video video = videoDao.find(userFavorite.getObjectId());
		    info = video.getSpeaker()+"##"+video.getSubject().getName();
		    name = video.getName();
		    break;
		case UserFavorite.MEETING:
		    Meeting meeting = meetingDao.find(userFavorite.getObjectId());
		    info = meeting.getEnterpriseName()+"##"+meeting.getCommitDate();
		    name = meeting.getName();
		    break;
		default:
		    return new Result(false, ErrorCode.PARAM_ERROR);
		}
		userFavorite.setName(name);
		userFavorite.setInfo(info);
		userFavoriteDao.save(userFavorite);
		return new Result(true);
	}

	@Override
	public List<UserFavorite> findByAccount(String account) {
	    List<UserFavorite> userFavorites = new LinkedList<UserFavorite>();
	    User user = userManager.findByAccount(account);
		userFavorites = findBySql(UserFavorite.TABLE_NAME, UserFavorite.USER_ID, user.getId());
	    return userFavorites;
	}

	@Override
	public List<UserFavorite> getMedicineFavorites(User user) {
	    LinkedList<UserFavorite> userFavorites = new LinkedList<UserFavorite>();
	    ParamMap paramMap = new ParamMap().put(UserFavorite.USER_ID, user.getId())
		    			      .put(UserFavorite.TYPE, UserFavorite.ENTER_CHINESE);
	    userFavorites.addAll(userFavoriteDao.findBySql(UserFavorite.TABLE_NAME, paramMap));
	    paramMap.put(UserFavorite.TYPE, UserFavorite.ENTER_WEST);
	    userFavorites.addAll(userFavoriteDao.findBySql(UserFavorite.TABLE_NAME, paramMap));
	    paramMap.put(UserFavorite.TYPE, UserFavorite.MEDICINE_CHINESE);
	    userFavorites.addAll(userFavoriteDao.findBySql(UserFavorite.TABLE_NAME, paramMap));
	    paramMap.put(UserFavorite.TYPE, UserFavorite.MEDICINE_WEST);
	    userFavorites.addAll(userFavoriteDao.findBySql(UserFavorite.TABLE_NAME, paramMap));
	    return userFavorites;
	}
    
   
}
    
   
