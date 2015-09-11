package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.DoctorDao;
import com.rippletec.medicine.dao.FindAndSearchDao;
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
    public boolean setInfo(String account, String name, String hospital,
	    String office, String officePhone, String profession) {
	User user = userManager.findByAccount(account);
	if(user == null)
	    return false;
	Doctor doctor = new Doctor(user, name, hospital, office, profession, officePhone);
	if(doctorDao.save(doctor) <= 0)
	    return false;
	user.setType(User.TYPE_DOC);
	userManager.update(user);
	return true;
    }



}
