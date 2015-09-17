package com.rippletec.test.dao;

import java.util.Date;
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.dao.UserDao;
import com.rippletec.medicine.model.User;


@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class UserDaoTest implements IBaseDaoTest {
    
    @Resource(name=UserDao.NAME)
    private UserDao userDao;

    @Override
    @Test
    public void testDelete() throws Exception {
	userDao.delete(1);
    }

    @Override
    @Test
    public void testFind() throws Exception {
	System.out.println(userDao.find(1).toString());
    }

    @Override
    @Test
    public void testFindByPage() throws Exception {
    }

    @Test
    public void testGetCount() throws Exception {
	System.out.println(userDao.getCount()+"");
    }

    @Override
    @Test
    public void testSave() throws Exception {
	System.out.println((System.currentTimeMillis()+"").substring(3));
	User user = new User("password","account", User.TYPE_STU, "phone", "certificateImg", new Date());
	userDao.save(user);
    }
    
    @Override
    @Test
    public void testUpdate() throws Exception {
//	Doctor doctor = new Doctor("hospital", "office", "profession", "officePhone");
//	User user = userDao.find(3);
//	
//	userDao.update(user);
    }

}
