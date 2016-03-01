package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.DoctorDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.exception.DaoException;
import com.rippletec.medicine.model.Doctor;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.DoctorManager;
import com.rippletec.medicine.service.UserManager;

@Service(DoctorManager.NAME)
public class DoctorManagerImpl extends BaseManager<Doctor> implements DoctorManager{
    
    
    
    @Resource(name=DoctorDao.NAME)
    private DoctorDao doctorDao;
    
    @Resource(name=UserManager.NAME)
    private UserManager userManager;

    @Override
    protected FindAndSearchDao<Doctor> getDao() {
	return this.doctorDao;
    }

    @Override
    public void setInfo(String account, String name, String hospital,
	    String office, String officePhone, String profession) throws DaoException {
	User user = userManager.findByAccount(account);
	Doctor doctor = new Doctor(user, name, hospital, office, profession, officePhone);
	doctorDao.save(doctor);
	user.setType(User.TYPE_DOC);
	userManager.update(user);
    }



}
