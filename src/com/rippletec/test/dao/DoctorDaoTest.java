package com.rippletec.test.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.dao.DoctorDao;
import com.rippletec.medicine.dao.UserDao;

@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class DoctorDaoTest implements IBaseDaoTest {

    @Resource(name=DoctorDao.NAME)
    private DoctorDao doctorDao;
    
    @Resource(name=UserDao.NAME)
    private UserDao userDao;
    
    @Override
    @Test
    public void testDelete() throws Exception {
	doctorDao.delete(2);
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
//	Doctor doctor = new Doctor("hospital", "office", "profession", "officePhone");
//	doctor.setUser(userDao.find(1));
//	doctorDao.save(doctor);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
    }

}
