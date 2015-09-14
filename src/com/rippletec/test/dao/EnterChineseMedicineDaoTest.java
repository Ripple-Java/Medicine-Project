package com.rippletec.test.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.dao.EnterpriseMedicineTypeDao;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.EnterpriseMedicineType;
import com.rippletec.medicine.model.Medicine;


/**
 * @author Liuyi
 *
 */
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)//defaultRollback true会回滚不会插入到数据库中 flase则会插入到数据库中
@Transactional
public class EnterChineseMedicineDaoTest implements IBaseDaoTest {
    
    @Resource(name=EnterChineseMedicineDao.NAME)
    private EnterChineseMedicineDao enterChineseMedicineDao;
    @Resource(name=EnterpriseMedicineTypeDao.NAME)
    private EnterpriseMedicineTypeDao enterpriseMedicineTypeDao;

    @Override
    @Test
    public void testDelete() throws Exception {
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
	EnterpriseMedicineType enterpriseMedicineType = enterpriseMedicineTypeDao.find(1);
	Medicine medicine = new Medicine(Medicine.CHINESE);
	EnterChineseMedicine enterChineseMedicine = new EnterChineseMedicine(medicine, enterpriseMedicineType, "enterprise_name", "name", "content", "efficacy", "annouce", "preparations", "manual", "store", "category", 44.0, EnterChineseMedicine.ON_PUBLISTH);
	enterChineseMedicineDao.save(enterChineseMedicine);
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
    }

}
