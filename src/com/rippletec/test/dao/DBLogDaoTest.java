package com.rippletec.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.dao.DBLogDao;
import com.rippletec.medicine.model.DBLog;



/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class DBLogDaoTest implements IBaseDaoTest {
   
    @Resource(name = DBLogDao.NAME)
    private DBLogDao dbLogDao;

    @Override
    @Test
    public void testDelete() throws Exception {
    }

    @Override
    @Test
    public void testFind() throws Exception {
	System.out.println(dbLogDao.find(1).toString());
    }

    @Override
    @Test
    public void testFindByPage() throws Exception {
    }

    @Override
    @Test
    public void testSave() throws Exception {
	DBLog dbLog = new DBLog(1, DBLog.TYPE_SAVE, "save", new Date());
	dbLogDao.save(dbLog);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
    }

}
