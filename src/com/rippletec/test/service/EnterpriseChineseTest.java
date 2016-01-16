package com.rippletec.test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.model.ChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.service.ChineseMedicineManager;
import com.rippletec.medicine.service.EnterChineseMedicineManager;
import com.rippletec.medicine.service.EnterpriseManager;
import com.rippletec.medicine.vo.web.EnterChineseVO;
import com.rippletec.test.dao.IBaseDaoTest;


/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class EnterpriseChineseTest implements IBaseDaoTest{
    
    @Resource(name = EnterChineseMedicineManager.NAME)
    private EnterChineseMedicineManager enterChineseMedicineManager;
    
    @Resource(name = EnterpriseManager.NAME)
    private EnterpriseManager enterpriseManager;
    
    @Resource(name = ChineseMedicineManager.NAME)
    private ChineseMedicineManager chineseMedicineManager;

    @Override
    public void testDelete() throws Exception {
    }

    @Override
    public void testFind() throws Exception {
    }

    @Override
    public void testFindByPage() throws Exception {
    }

    @Override
    @Test
    public void testSave() throws Exception {
	Enterprise enterprise = enterpriseManager.find(94);
	for (int i = 0; i < 10; i++) {
	    ChineseMedicine chineseMedicine = chineseMedicineManager.find(i+10);
	    EnterChineseVO enterChineseVO = new EnterChineseVO();
	}
	
    }

    @Override
    public void testUpdate() throws Exception {
    }

}
