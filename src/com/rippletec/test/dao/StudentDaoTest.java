package com.rippletec.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.dao.StudentDao;
import com.rippletec.medicine.model.Student;
import com.rippletec.medicine.model.User;


/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class StudentDaoTest implements IBaseDaoTest {
    
    @Resource(name=StudentDao.NAME)
    private StudentDao studentDao;

    @Override
    @Test
    public void testDelete() throws Exception {
	studentDao.delete(1);
    }

    @Override
    @Test
    public void testFind() throws Exception {
    }

    @Override
    @Test
    public void testFindByPage() throws Exception {
    }

    @Override
    @Test
    public void testSave() throws Exception {
	User user  = new User("password", "account", "name", User.TYPE_STU, "cellphone", "certificateImg", new Date(), new Date());
	Student student = new Student(user, "name", "school", "major");
	student.setUser(user);
	studentDao.save(student);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
    }

}
