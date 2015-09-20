package com.rippletec.medicine.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.dao.FeedBackMassDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.UserDao;
import com.rippletec.medicine.model.FeedBackMass;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.FeedBackMassManager;

@Service(FeedBackMassManager.NAME)
public class FeedBackMassManagerImpl extends BaseManager<FeedBackMass> implements FeedBackMassManager{
    
    @Resource(name=FeedBackMassDao.NAME)
    private FeedBackMassDao feedBackMassDao;

    @Resource(name=UserDao.NAME)
    private UserDao userDao;
    @Override
    protected FindAndSearchDao<FeedBackMass> getDao() {
	return this.feedBackMassDao;
    }

	@Override
	public void addFeedBackMass(FeedBackMass feedBackMass, String account) {
		// TODO Auto-generated method stub
		User user =userDao.findByPage(User.ACCOUNT, account, new PageBean(0, 100)).get(0);
		feedBackMass.setUser(user);
		feedBackMass.setStatus(0);
		feedBackMass.setTime(new Date());
		feedBackMassDao.save(feedBackMass);
	}

}
