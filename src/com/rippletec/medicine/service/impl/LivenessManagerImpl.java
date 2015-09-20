package com.rippletec.medicine.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.LivenessDao;
import com.rippletec.medicine.model.Liveness;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.LivenessManager;
import com.rippletec.medicine.utils.DateUtil;


@Service(LivenessManager.NAME)
public class LivenessManagerImpl extends BaseManager<Liveness> implements LivenessManager{
    
    @Resource(name=LivenessDao.NAME)
    private LivenessDao livenessDao;

    @Override
    protected FindAndSearchDao<Liveness> getDao() {
	return this.livenessDao;
    }

    @Override
    public void save(User user) {
	if (user != null) {
	    Liveness liveness = new Liveness(user, 0, DateUtil.getYearMonthDate(new Date()));
	    save(liveness);
	}
    }

    @Override
    public void updateLogin(User user, Date time) {
	System.out.println(user.getId());
	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put(Liveness.USER_ID, user.getId());
	paramMap.put("time", DateUtil.getYearMonthDate(time));
	Liveness userLiveness = null;
	List<Liveness> livenesses  = findBySql(Liveness.TABLE_NAME, paramMap);
	if (livenesses == null || livenesses.size() <=0 ) {
	    userLiveness = new Liveness(user, 1, DateUtil.getYearMonthDate(time));
	    save(userLiveness);
	}
	else {
	    userLiveness = livenesses.get(0);
	    userLiveness.setCount(userLiveness.getCount() + 1);
	    update(userLiveness);
	}
	return;
    }
    

}
