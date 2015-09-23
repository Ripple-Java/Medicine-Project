package com.rippletec.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.service.ChineseMedicineManager;
import com.rippletec.test.dao.IBaseDaoTest;
/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class ChineseMedicineManagerTest implements IBaseDaoTest{
    
    @Resource(name=ChineseMedicineManager.NAME)
    private ChineseMedicineManager chineseMedicineManager;

    @Override
    @Test
    public void testDelete() throws Exception {
    }

    @Override
    @Test
    public void testFind() throws Exception {
	List<ChineseMedicine> chineseMedicines = chineseMedicineManager.findByParam("annouce", "【用药监护】");
	for (ChineseMedicine chineseMedicine : chineseMedicines) {
	    chineseMedicineManager.delete(chineseMedicine.getId());
	}
    }

    @Override
    @Test
    public void testFindByPage() throws Exception {
    }

    @Override
    @Test
    public void testSave() throws Exception {
	chineseMedicineManager.save(null);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
    }
    

}
