package com.rippletec.medicine.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rippletec.medicine.dao.FindAndSearchDao;
import com.rippletec.medicine.dao.StudentDao;
import com.rippletec.medicine.model.Student;
import com.rippletec.medicine.model.User;
import com.rippletec.medicine.service.StudentManager;
import com.rippletec.medicine.service.UserManager;


@Service(StudentManager.NAME)
public class StudentManagerImpl extends BaseManager<Student> implements StudentManager{
    
    @Resource(name=StudentDao.NAME)
    private StudentDao studentDao;
    
    @Resource(name=UserManager.NAME)
    private UserManager userManager;

    @Override
    protected FindAndSearchDao<Student> getDao() {
	return this.studentDao;
    }

    @Override
    public boolean setStuInfo(String account, String name, String school,
	    int degree, String major) {
	User user = userManager.findByAccount(account);
	if(user == null)
	    return false;
	Student student = new Student(user, name, school, major);
	if(studentDao.save(student) <= 0)
	    return false;
	user.setType(User.TYPE_STU);
	userManager.update(user);
	return true;
    }

   

}
