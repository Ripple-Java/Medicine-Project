package com.rippletec.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rippletec.medicine.dao.EnterChineseMedicineDao;
import com.rippletec.medicine.dao.EnterpriseDao;
import com.rippletec.medicine.dao.EnterpriseMedicineTypeDao;
import com.rippletec.medicine.dao.MedicineTypeDao;
import com.rippletec.medicine.model.EnterChineseMedicine;
import com.rippletec.medicine.model.Enterprise;
import com.rippletec.medicine.model.Medicine;
import com.rippletec.medicine.model.MedicineType;


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
    @Resource(name=MedicineTypeDao.NAME)
    private MedicineTypeDao medicineTypeDao;
    @Resource(name=EnterpriseDao.NAME)
    private EnterpriseDao enterpriseDao;

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
	for (int i = 0; i < 30; i++) {
	    Medicine medicine = new Medicine(Medicine.ENTER_CHINESE, 94);
	    MedicineType medicineType = medicineTypeDao.find(5);
//	    Enterprise enterprise = enterpriseDao.find(94);
////	    EnterChineseMedicine enterChineseMedicine = new EnterChineseMedicine(medicine, medicineType, "enterprise_name", "enterChineseMedicne-"+i, "content", "efficacy", "annouce", "preparations", "manual", "store", "category", 44.0, EnterChineseMedicine.ON_PUBLISTH, new Date());
//	    enterChineseMedicine.setSortKey("sortKey");
//	    enterChineseMedicine.setEnterprise(enterprise);
//	    enterChineseMedicineDao.save(enterChineseMedicine);
	}
	
    }

    @Override
    @Test
    public void testUpdate() throws Exception {
    }

}
