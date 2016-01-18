package com.rippletec.medicine.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.FeedBackMassDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.UserDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.FeedBackMass;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.FeedBackMassManager;
import com.rippletec.medicine.service.UserManager;

@Service(FeedBackMassManager.NAME)
public class FeedBackMassManagerImpl extends BaseManager<FeedBackMass> implements FeedBackMassManager{
    
    @Resource(name=FeedBackMassDao.NAME)
    private FeedBackMassDao feedBackMassDao;

    @Resource(name=UserManager.NAME)
    private UserManager userManager;
    @Override
    protected FindAndSearchDao<FeedBackMass> getDao() {
	return this.feedBackMassDao;
    }

	@Override
	public void addFeedBackMass(FeedBackMass feedBackMass, String account) throws DaoException {
		User user = userManager.findByAccount(account);
		feedBackMass.setUser(user);
		feedBackMass.setStatus(0);
		feedBackMass.setTime(new Date());
		feedBackMass.setStatus(FeedBackMass.NO_READ);
		feedBackMassDao.save(feedBackMass);
	}

}
